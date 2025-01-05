package coinchange;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import io.dropwizard.jetty.HttpConnectorFactory;
import io.dropwizard.setup.Environment;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;

@ExtendWith(DropwizardExtensionsSupport.class)
public class CoinChangeApplicationTest {

    public static final DropwizardAppExtension<CoinChangeConfiguration> RULE = new DropwizardAppExtension<>(
            CoinChangeApplication.class, "resources/config.yml");

    @Test
    public void testGetName() {
        CoinChangeApplication app = new CoinChangeApplication();
        assertEquals("Coin Change Calculator", app.getName());
    }

    @Test
    public void testRun() throws Exception {
        CoinChangeConfiguration config = RULE.getConfiguration();
        Environment environment = RULE.getEnvironment();

        // Ensure the application has started and the environment is set up
        assertNotNull(environment.jersey());

        // Perform additional assertions if needed
        assertEquals("./resources/logback.xml", config.getProperty("loggingConfig"),
                "Expected loggingConfig to be './resources/logback.xml'");
    }

    @Test
    public void testLoadConfiguration_NoInvalidTypeIdException() {
        assertDoesNotThrow(() -> {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.registerSubtypes(new NamedType(HttpConnectorFactory.class, "http"));
            mapper.readValue(new File("resources/config.yml"), CoinChangeConfiguration.class);
        });
    }

}