package com.gamaza.examples.springtest.exception;

import com.gamaza.examples.springtest.config.EstandarBaseTest;
import org.junit.jupiter.api.*;

import static com.gamaza.examples.springtest.config.testcontainers.TestConstants.EXCEPTION_TAG;

/**
 * Test class for {@link GenericRuntimeException}
 */
@Tag(value = EXCEPTION_TAG)
@DisplayName(value = "[Exception]: GenericRuntimeException")
class GenericRuntimeExceptionTest extends EstandarBaseTest {

    @Nested
    @Order(value = 0)
    @DisplayName(value = "[Scope]: Message")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class ValuesTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given a GenericRuntimeException -> Check if the exception message is correct (Check 1)")
        void Given_GenericRuntimeException_When_ExceptionIsThrown_Then_ExceptionMessageIsCorrect1() {
            // Given
            String message = "Exception message";
            // When
            GenericRuntimeException exception = Assertions.assertThrows(GenericRuntimeException.class, () -> {
                throw new GenericRuntimeException(message);
            });
            // Then
            String expectedMessage = "Unhandled exception with message: Exception message";
            Assertions.assertEquals(expectedMessage, exception.getMessage());
        }

        @Test
        @Order(value = 1)
        @DisplayName(value = "2. Given a GenericRuntimeException -> Check if the exception message is correct (Check 2)")
        void Given_GenericRuntimeException_When_ExceptionIsThrown_Then_ExceptionMessageIsCorrect2() {
            // Given
            String message = "Runtime exception at line [2]";
            // When
            GenericRuntimeException exception = Assertions.assertThrows(GenericRuntimeException.class, () -> {
                throw new GenericRuntimeException(message);
            });
            // Then
            String expectedMessage = "Unhandled exception with message: Runtime exception at line [2]";
            Assertions.assertEquals(expectedMessage, exception.getMessage());
        }

    }

}
