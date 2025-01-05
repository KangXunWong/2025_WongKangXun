package coinchange;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.codahale.metrics.health.HealthCheck.Result;
import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class CoinChangeHealthCheckTest {

    private static CoinChangeConfiguration config;
    private static WireMockServer wireMockServer;

    @BeforeAll
    public static void setUp() throws IOException {
        config = CoinChangeApplication.loadConfiguration();
        wireMockServer = new WireMockServer(options().port(8080));
        wireMockServer.start();
    }

    @AfterAll
    public static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void testHealthCheckHealthy() throws Exception {
        wireMockServer.stubFor(get(urlEqualTo("/coin-change"))
                .willReturn(aResponse().withStatus(200)));

        CoinChangeHealthCheck healthCheck = new CoinChangeHealthCheck(config);
        Result result = healthCheck.check();
        assertTrue(result.isHealthy(), "Expected the health check to be healthy");
    }

    @Test
    public void testHealthCheckUnhealthy() throws Exception {
        wireMockServer.stubFor(get(urlEqualTo("/coin-change"))
                .willReturn(aResponse().withStatus(500)));

        CoinChangeHealthCheck healthCheck = new CoinChangeHealthCheck(config);
        Result result = healthCheck.check();
        assertTrue(!result.isHealthy(), "Expected the health check to be unhealthy");
        assertEquals("CoinChangeRouter is not reachable. Response code: 500", result.getMessage());
    }
}