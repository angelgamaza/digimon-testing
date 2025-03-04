package com.gamaza.examples.springtest.util;

import com.gamaza.examples.springtest.config.EstandarBaseTest;
import org.junit.jupiter.api.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static com.gamaza.examples.springtest.config.testcontainers.TestConstants.UTIL_TAG;

/**
 * Test class for {@link ExceptionUtils}
 */
@Tag(value = UTIL_TAG)
@DisplayName(value = "[Util]: Exception")
class ExceptionUtilsTest extends EstandarBaseTest {

    @Nested
    @Order(value = 0)
    @DisplayName(value = "[Scope]: Constructor")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class ConstructorTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given utils class -> Check if the constructor is private")
        void Given_ExceptionUtilsClass_When_ConstructorIsCalled_Then_ItIsPrivate() throws NoSuchMethodException {
            // Get the private constructor of the class
            Constructor<ExceptionUtils> constructor = ExceptionUtils.class.getDeclaredConstructor();
            // Make the constructor accessible
            constructor.setAccessible(true);
            // Verify that an IllegalStateException is thrown when we attempt to instantiate the class
            InvocationTargetException exception = Assertions.assertThrows(InvocationTargetException.class, constructor::newInstance);
            // Extract the actual exception (IllegalStateException) from the InvocationTargetException
            Assertions.assertInstanceOf(IllegalStateException.class, exception.getCause());
            // Verify the exception message
            IllegalStateException cause = (IllegalStateException) exception.getCause();
            Assertions.assertEquals("Could not instantiate ExceptionUtils class!", cause.getMessage());
        }

    }

    @Nested
    @Order(value = 1)
    @DisplayName(value = "[Scope]: Static Functions")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class StaticFunctionsTest {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given a null Throwable -> Call to GetCauseOrDefaultMessage method and return null")
        void GivenNullThrowable_When_GetCauseOrDefaultMessageIsCalled_Then_ReturnNull() {
            // Given
            // When
            // Then
            Assertions.assertNull(
                    ExceptionUtils.getCauseOrDefaultMessage(null)
            );
        }

        @Test
        @Order(value = 1)
        @DisplayName(value = "2. Given a not null Throwable with valid cause -> Call to GetCauseOrDefaultMessage method and return it")
        void GivenNotNullThrowableWithValidCause_When_GetCauseOrDefaultMessageIsCalled_Then_ReturnIt() {
            // Given
            Exception cause = new Exception("Cause message");
            Exception exception = new Exception("Main message", cause);
            // When
            String result = ExceptionUtils.getCauseOrDefaultMessage(exception);
            // Then
            Assertions.assertEquals("Cause message", result);
        }

        @Test
        @Order(value = 2)
        @DisplayName(value = "3. Given a not null Throwable with no cause -> Call to GetCauseOrDefaultMessage method and return the localized message")
        void GivenNotNullThrowableWithNoCause_When_GetCauseOrDefaultMessageIsCalled_Then_ReturnTheLocalizedMessage() {
            // Given
            Exception exception = new Exception("Main message");
            // When
            String result = ExceptionUtils.getCauseOrDefaultMessage(exception);
            // Then
            Assertions.assertEquals("Main message", result);
        }

        @Test
        @Order(value = 3)
        @DisplayName(value = "4. Given a not null Throwable with no messages -> Call to GetCauseOrDefaultMessage method and return the class name")
        void GivenNotNullThrowableWithNoMessages_When_GetCauseOrDefaultMessageIsCalled_Then_ReturnTheClassName() {
            // Given
            Exception exception = new Exception("");
            // When
            String result = ExceptionUtils.getCauseOrDefaultMessage(exception);
            // Then
            Assertions.assertEquals("Exception", result);
        }

        @Test
        @Order(value = 4)
        @DisplayName(value = "5. Given a not null Throwable with only the message -> Call to GetCauseOrDefaultMessage method and return it")
        void GivenNotNullThrowableWithOnlyTheMessage_When_GetCauseOrDefaultMessageIsCalled_Then_ReturnIt() {
            // Given
            Exception exception = new Exception("") {
                @Override
                public String getLocalizedMessage() {
                    return "";
                }

                @Override
                public String getMessage() {
                    return "Fallback message";
                }
            };
            // When
            String result = ExceptionUtils.getCauseOrDefaultMessage(exception);
            // Then
            Assertions.assertEquals("Fallback message", result);
        }

    }

}
