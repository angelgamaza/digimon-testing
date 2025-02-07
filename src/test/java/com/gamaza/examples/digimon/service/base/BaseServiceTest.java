package com.gamaza.examples.digimon.service.base;

import com.gamaza.examples.digimon.config.EstandarBaseTest;
import org.junit.jupiter.api.Tag;

import static com.gamaza.examples.digimon.config.testcontainers.TestConstants.SERVICE_TAG;

/**
 * Base class for {@link org.springframework.stereotype.Service}
 */
@Tag(value = SERVICE_TAG)
public abstract class BaseServiceTest extends EstandarBaseTest {
}
