package coinchange;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.health.HealthCheck;

public class CoinChangeHealthCheck extends HealthCheck {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoinChangeHealthCheck.class);

    private final CoinChangeConfiguration config;
    private URL url;

    public CoinChangeHealthCheck(CoinChangeConfiguration config) throws Exception {
        this.config = config;
        this.url = new URI("http://localhost:8080/coin-change").toURL(); // Adjust the URL as needed
    }

    @Override
    protected Result check() throws Exception {
        // Check if the CoinChangeRouter endpoint is reachable
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(2000); // Set timeout as needed
            connection.setReadTimeout(2000); // Set timeout as needed

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                return Result.healthy();
            } else {
                LOGGER.warn("CoinChangeRouter is not reachable. Response code: {}", responseCode);
                return Result.unhealthy("CoinChangeRouter is not reachable. Response code: " + responseCode);
            }
        } catch (IOException e) {
            LOGGER.error("CoinChangeRouter is not reachable. Error: {}", e.getMessage(), e);
            return Result.unhealthy("CoinChangeRouter is not reachable. Error: " + e.getMessage());
        }
    }

    // Setter for URL to allow injecting a mock URL for testing purposes
    public void setUrl(URL url) {
        this.url = url;
    }
}