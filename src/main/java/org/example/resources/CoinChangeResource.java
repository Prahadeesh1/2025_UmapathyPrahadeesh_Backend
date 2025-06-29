package org.example.resources;

import org.example.api.*;
import org.example.core.CCService;
import com.codahale.metrics.annotation.Timed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@Path("/api/v1/coin-change")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CoinChangeResource {

    private final CCService CCService;

    public CoinChangeResource(CCService CCService) {
        this.CCService = CCService;
    }

    @POST
    @Path("/calculate")
    @Timed
    public Response calcmincoins(@Valid CCRequest request) {
        try {
            Map<String, Object> result = CCService.calcmincoins(request);

            if (result.containsKey("error")) {
                ErrorResponse errorResponse = new ErrorResponse("CALCULATION_ERROR", (String) result.get("error"));
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(errorResponse)
                        .build();
            }

            // Convert service result to DTOs
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> coins = (List<Map<String, Object>>) result.get("coins");
            Integer totalCoins = (Integer) result.get("totalCoins");

            List<CInfo> cInfos = new ArrayList<>();
            for (Map<String, Object> coin : coins) {
                Double denomination = (Double) coin.get("denomination");
                Integer count = (Integer) coin.get("count");
                cInfos.add(new CInfo(denomination, count));
            }

            CCResponse response = new CCResponse(cInfos, totalCoins);
            return Response.ok(response).build();

        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("INTERNAL_ERROR", "An unexpected error occurred");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(errorResponse)
                    .build();
        }
    }

    @GET
    @Path("/health")
    @Timed
    public Response healthCheck() {
        return Response.ok(Map.of("status", "healthy", "service", "coin-change-calculator")).build();
    }

    @GET
    @Path("/valid-denominations")
    @Timed
    public Response getValidDenominations() {
        List<Double> validDenominations = List.of(0.01, 0.05, 0.10, 0.20, 0.50, 1.00, 2.00, 5.00, 10.00, 50.00, 100.00, 1000.00);
        return Response.ok(Map.of("validDenominations", validDenominations)).build();
    }
}