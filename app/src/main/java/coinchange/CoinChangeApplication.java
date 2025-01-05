package coinchange;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import io.dropwizard.Application;
import io.dropwizard.jetty.HttpConnectorFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class CoinChangeApplication extends Application<CoinChangeConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoinChangeApplication.class);

    public static void main(String[] args) throws Exception {
        new CoinChangeApplication().run(args);
    }

    @Override
    public String getName() {
        return "Coin Change Calculator";
    }

    @Override
    public void initialize(final Bootstrap<CoinChangeConfiguration> bootstrap) {
        // Application initialization
    }

    @Override
    public void run(final CoinChangeConfiguration configuration, final Environment environment) throws Exception {
        setupLogging(configuration.getProperty("loggingConfig"));
        LOGGER.info("Logging config: {}", configuration.getProperty("loggingConfig"));
        environment.jersey().register(new CoinChangeRouter(configuration));
        environment.jersey().register(new CorsFilter());

        final CoinChangeHealthCheck healthCheck;
        try {
            healthCheck = new CoinChangeHealthCheck(configuration);
        } catch (Exception e) {
            LOGGER.error("Error creating CoinChangeHealthCheck", e);
            throw e;
        }
        environment.healthChecks().register("coinChange", healthCheck);
    }

    private void setupLogging(String loggingConfigPath) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(context);
        context.reset();
        try {
            configurator.doConfigure(new File(loggingConfigPath));
        } catch (JoranException je) {
            LOGGER.error("Error configuring logging", je);
        }
    }

    public static CoinChangeConfiguration loadConfiguration() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.registerSubtypes(new NamedType(HttpConnectorFactory.class, "http"));
        return mapper.readValue(new File("resources/config.yml"), CoinChangeConfiguration.class);
    }
}