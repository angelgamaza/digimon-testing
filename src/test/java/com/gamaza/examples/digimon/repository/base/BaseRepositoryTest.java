package com.gamaza.examples.digimon.repository.base;

import com.gamaza.examples.digimon.config.testcontainers.TestContainersConfig;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestClassOrder;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static com.gamaza.examples.digimon.config.testcontainers.TestConstants.REPOSITORY_TAG;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

/**
 * Base class for {@link CustomRepository}
 */
@Tag(value = REPOSITORY_TAG)
@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = NONE)
@ContextConfiguration(classes = TestContainersConfig.class)
@TestClassOrder(value = ClassOrderer.OrderAnnotation.class)
public abstract class BaseRepositoryTest {
}
