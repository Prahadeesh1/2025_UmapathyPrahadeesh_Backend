package org.example.core;

import org.example.api.CCRequest;
import java.util.*;

public class CCService {

    private static final int[] VALID_DENOMINATIONS = {1, 5, 10, 20, 50, 100, 200, 500, 1000, 5000, 10000, 100000};

    public Map<String, Object> calcmincoins(CCRequest request) {
        return calcmincoins(request.getAmount(),
                request.getDenominations().stream().mapToDouble(Double::doubleValue).toArray());
    }

    private Map<String, Object> calcmincoins(double amount, double[] denominations) {
        Map<String, Object> result = new HashMap<>();

        // Check if amount is between range
        if (amount < 0 || amount > 10000.00) {
            result.put("error", "The amount has to be between 0 and 10000 please");
            return result;
        }

        if (amount == 0) {
            result.put("coins", new ArrayList<>());
            result.put("totalCoins", 0);
            return result;
        }

        // Convert to cents
        int targetcents = (int) Math.round(amount * 100);

        // Validate and convert given coin denominations to cents
        List<Integer> validCoins = new ArrayList<>();
        for (double coin : denominations) {
            int coincents = (int) Math.round(coin * 100);
            if (isvaliddenomination(coincents)) {
                validCoins.add(coincents);
            } else {
                result.put("error", "Wrong coin denomination. Please try again: " + coin);
                return result;
            }
        }

        // Check if denomination input empty
        if (validCoins.isEmpty()) {
            result.put("error", "No valid coin denomination given");
            return result;
        }

        // Sort the coins in ascending order
        Collections.sort(validCoins);

        // Convert to array for DP algorithm
        int[] coins = validCoins.stream().mapToInt(i -> i).toArray();

        // Apply DP algorithm
        Map<String, Object> dpResult = coinChangeDP(targetcents, coins);

        if (dpResult.containsKey("error")) {
            return dpResult;
        }

        // Convert results back to dollar denominations
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> coinCounts = (List<Map<String, Object>>) dpResult.get("coins");
        List<Map<String, Object>> dollarCoinCounts = new ArrayList<>();

        for (Map<String, Object> coinCount : coinCounts) {
            Map<String, Object> dollarCoin = new HashMap<>();
            int cents = (Integer) coinCount.get("denomination");
            double dollars = cents / 100.0;
            dollarCoin.put("denomination", dollars);
            dollarCoin.put("count", coinCount.get("count"));
            dollarCoinCounts.add(dollarCoin);
        }

        result.put("coins", dollarCoinCounts);
        result.put("totalCoins", dpResult.get("totalCoins"));

        return result;
    }

    private Map<String, Object> coinChangeDP(int amount, int[] coins) {
        Map<String, Object> result = new HashMap<>();

        int[] dp = new int[amount + 1];
        int[] p = new int[amount + 1];

        // Initialize DP array
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        p[0] = -1;

        // Fill the DP table
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (coin <= i && dp[i - coin] != Integer.MAX_VALUE) {
                    if (dp[i - coin] + 1 < dp[i]) {
                        dp[i] = dp[i - coin] + 1;
                        p[i] = coin;
                    }
                }
            }
        }

        // Check if solution exists
        if (dp[amount] == Integer.MAX_VALUE) {
            result.put("error", "Cannot make the target amount with given denominations");
            return result;
        }

        // Reconstruct the solution
        List<Integer> usedCoins = new ArrayList<>();
        int currentAmount = amount;

        while (currentAmount > 0) {
            int coinUsed = p[currentAmount];
            usedCoins.add(coinUsed);
            currentAmount -= coinUsed;
        }

        // Count occurrences of each coin denomination
        Map<Integer, Integer> coinCount = new HashMap<>();
        for (int coin : usedCoins) {
            coinCount.put(coin, coinCount.getOrDefault(coin, 0) + 1);
        }

        // Convert to sorted list format
        List<Map<String, Object>> coinResults = new ArrayList<>();
        List<Integer> sortedDenominations = new ArrayList<>(coinCount.keySet());
        Collections.sort(sortedDenominations);

        for (int denomination : sortedDenominations) {
            Map<String, Object> coinInfo = new HashMap<>();
            coinInfo.put("denomination", denomination);
            coinInfo.put("count", coinCount.get(denomination));
            coinResults.add(coinInfo);
        }

        result.put("coins", coinResults);
        result.put("totalCoins", dp[amount]);

        return result;
    }

    private boolean isvaliddenomination(int coinCents) {
        for (int validCoin : VALID_DENOMINATIONS) {
            if (validCoin == coinCents) {
                return true;
            }
        }
        return false;
    }
}