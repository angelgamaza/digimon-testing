package com.gamaza.examples.springtest.controller.base;

import com.gamaza.examples.springtest.config.JacksonMapperConfig;
import com.gamaza.examples.springtest.config.GlobalExceptionHandler;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestClassOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static com.gamaza.examples.springtest.config.testcontainers.TestConstants.CONTROLLER_TAG;

/**
 * Base class for {@link org.springframework.stereotype.Controller}
 */
@WebMvcTest
@Import(value = {GlobalExceptionHandler.class, JacksonMapperConfig.class})
@Tag(value = CONTROLLER_TAG)
@TestClassOrder(value = ClassOrderer.OrderAnnotation.class)
public abstract class BaseControllerTest {

    @Autowired
    protected MockMvc mockMvc;

}
