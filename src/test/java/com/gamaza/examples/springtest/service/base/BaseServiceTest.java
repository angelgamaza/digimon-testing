package com.gamaza.examples.springtest.service.base;

import com.gamaza.examples.springtest.config.EstandarBaseTest;
import org.junit.jupiter.api.Tag;

import static com.gamaza.examples.springtest.config.testcontainers.TestConstants.SERVICE_TAG;

/**
 * Base class for {@link org.springframework.stereotype.Service}
 */
@Tag(value = SERVICE_TAG)
public abstract class BaseServiceTest extends EstandarBaseTest {
}
