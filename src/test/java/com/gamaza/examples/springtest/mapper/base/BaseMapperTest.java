package com.gamaza.examples.springtest.mapper.base;

import com.gamaza.examples.springtest.config.EstandarBaseTest;
import org.junit.jupiter.api.Tag;

import static com.gamaza.examples.springtest.config.testcontainers.TestConstants.MAPPER_TAG;

/**
 * Base class for {@link org.mapstruct.Mapper}
 */
@Tag(value = MAPPER_TAG)
public abstract class BaseMapperTest extends EstandarBaseTest {
}
