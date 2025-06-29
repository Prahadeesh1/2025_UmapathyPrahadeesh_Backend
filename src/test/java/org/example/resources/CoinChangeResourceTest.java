package org.example.resources;

import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.example.api.CCRequest;
import org.example.core.CCService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DropwizardExtensionsSupport.class)
public class CoinChangeResourceTest {

    private static final CCService CC_SERVICE = new CCService();
    private static final ResourceExtension RESOURCES = ResourceExtension.builder()
            .addResource(new CoinChangeResource(CC_SERVICE))
            .build();

    @Test
    public void testcalcmincoins() {
        List<Double> denominations = Arrays.asList(0.01, 0.05, 0.10, 0.20);
        CCRequest request = new CCRequest(7.03, denominations);

        Response response = RESOURCES.target("/api/v1/coin-change/calculate")
                .request()
                .post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(200, response.getStatus());
    }

    @Test
    public void testHealthCheck() {
        Response response = RESOURCES.target("/api/v1/coin-change/health")
                .request()
                .get();

        assertEquals(200, response.getStatus());
    }

    @Test
    public void testGetValidDenominations() {
        Response response = RESOURCES.target("/api/v1/coin-change/valid-denominations")
                .request()
                .get();

        assertEquals(200, response.getStatus());
    }
}