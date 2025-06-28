package org.example.api;

import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.List;


// Success Response DTO
public class CCResponse {
    @JsonProperty
    private List<CInfo> coins;

    @JsonProperty
    private Integer totalCoins;

    public CCResponse() {}

    public CCResponse(List<CInfo> coins, Integer totalCoins) {
        this.coins = coins;
        this.totalCoins = totalCoins;
    }

    public List<CInfo> getCoins() {
        return coins;
    }

    public void setCoins(List<CInfo> coins) {
        this.coins = coins;
    }

    public Integer getTotalCoins() {
        return totalCoins;
    }

    public void setTotalCoins(Integer totalCoins) {
        this.totalCoins = totalCoins;
    }
}

