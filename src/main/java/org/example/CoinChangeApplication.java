package org.example;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.example.core.CCService;
import org.example.resources.CoinChangeResource;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterRegistration;
import java.util.EnumSet;

public class CoinChangeApplication extends Application<CoinChangeConfiguration> {

    public static void main(final String[] args) throws Exception {
        new CoinChangeApplication().run(args);
    }

    @Override
    public String getName() {
        return "coin-change-calculator";
    }

    @Override
    public void initialize(final Bootstrap<CoinChangeConfiguration> bootstrap) {
        // Any bootstrap configuration can go here
    }

    @Override
    public void run(final CoinChangeConfiguration configuration,
                    final Environment environment) {

        // Configure CORS
        configureCors(environment);

        // Create service instances
        final CCService CCService = new CCService();

        // Register resources
        final CoinChangeResource coinChangeResource = new CoinChangeResource(CCService);
        environment.jersey().register(coinChangeResource);
    }

    private void configureCors(Environment environment) {
        // Enable CORS headers
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin,Authorization");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }
}