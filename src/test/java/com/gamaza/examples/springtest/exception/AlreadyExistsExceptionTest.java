package com.gamaza.examples.springtest.exception;

import com.gamaza.examples.springtest.config.EstandarBaseTest;
import org.junit.jupiter.api.*;

import static com.gamaza.examples.springtest.config.testcontainers.TestConstants.EXCEPTION_TAG;

/**
 * Test class for {@link AlreadyExistsException}
 */
@Tag(value = EXCEPTION_TAG)
@DisplayName(value = "[Exception]: AlreadyExistsException")
class AlreadyExistsExceptionTest extends EstandarBaseTest {

    @Nested
    @Order(value = 0)
    @DisplayName(value = "[Scope]: Message")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class ValuesTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given an AlreadyExistsException -> Check if the exception message is correct (Check 1)")
        void Given_AlreadyExistsException_When_ExceptionIsThrown_Then_ExceptionMessageIsCorrect1() {
            // Given
            String object = "Type";
            String parameters = "name=Virus";
            // When
            AlreadyExistsException exception = Assertions.assertThrows(AlreadyExistsException.class, () -> {
                throw new AlreadyExistsException(object, parameters);
            });
            // Then
            String expectedMessage = "The object [Type] with parameters [name=Virus] already exists!";
            Assertions.assertEquals(expectedMessage, exception.getMessage());
        }

        @Test
        @Order(value = 1)
        @DisplayName(value = "2. Given an AlreadyExistsException -> Check if the exception message is correct (Check 2)")
        void Given_AlreadyExistsException_When_ExceptionIsThrown_Then_ExceptionMessageIsCorrect2() {
            // Given
            String object = "Digimon";
            String parameters = "id=1";
            // When
            AlreadyExistsException exception = Assertions.assertThrows(AlreadyExistsException.class, () -> {
                throw new AlreadyExistsException(object, parameters);
            });
            // Then
            String expectedMessage = "The object [Digimon] with parameters [id=1] already exists!";
            Assertions.assertEquals(expectedMessage, exception.getMessage());
        }

    }

}
