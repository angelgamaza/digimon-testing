package com.gamaza.examples.digimon.config;

import com.gamaza.examples.digimon.config.testcontainers.TestContainersConfig;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.TestClassOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * Base test class for all tests
 */
@SpringBootTest
@Import(value = TestContainersConfig.class)
@TestClassOrder(value = ClassOrderer.OrderAnnotation.class)
public abstract class EstandarBaseTest {
}
