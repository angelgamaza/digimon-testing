package com.gamaza.examples.digimon.config.testcontainers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import static com.gamaza.examples.digimon.config.testcontainers.TestConstants.POSTGRESQL_IMAGE;
import static com.gamaza.examples.digimon.config.testcontainers.TestConstants.POSTGRESQL_INIT_SCRIPT;

/**
 * Test containers configuration
 */
@TestConfiguration(proxyBeanMethods = false)
public class TestContainersConfig {

    // Class logger
    private static final Logger LOGGER = LoggerFactory.getLogger(TestContainersConfig.class);

    /**
     * Container declaracion
     */
    @Bean
    @ServiceConnection(name = "postgres")
    public PostgreSQLContainer<?> buildPostgresqlContainer() {
        return new PostgreSQLContainer<>(POSTGRESQL_IMAGE)
                .withInitScript(POSTGRESQL_INIT_SCRIPT)
                .withLogConsumer(new Slf4jLogConsumer(LOGGER));
    }

}
