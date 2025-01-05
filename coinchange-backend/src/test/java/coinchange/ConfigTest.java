package coinchange;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import io.dropwizard.jackson.Jackson;
import io.dropwizard.jetty.HttpConnectorFactory;
import io.dropwizard.server.DefaultServerFactory;

public class ConfigTest {

        @Test
        public void testGetProperty() throws IOException {

                // Load the configuration from the config.yml file
                ObjectMapper mapper = Jackson.newObjectMapper(new YAMLFactory());
                CoinChangeConfiguration config = mapper.readValue(new File("resources/config.yml"),
                                CoinChangeConfiguration.class);

                // Test the properties
                assertEquals("./resources/logback.xml", config.getProperty("loggingConfig"),
                                "Expected loggingConfig to be './resources/logback.xml'");
                DefaultServerFactory serverFactory = (DefaultServerFactory) config.getServerFactory();
                HttpConnectorFactory applicationConnector = (HttpConnectorFactory) serverFactory
                                .getApplicationConnectors()
                                .get(0);
                assertEquals(8080, applicationConnector.getPort(), "Expected application connector port to be 8080");
        }
}