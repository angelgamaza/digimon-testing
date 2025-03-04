package com.gamaza.examples.springtest.exception;

/**
 * Exception for Generic cases
 */
public class GenericRuntimeException extends RuntimeException {

    // Class constants
    private static final String MESSAGE = "Unhandled exception with message: %s";

    /**
     * Constructor
     *
     * @param message The exception message
     */
    public GenericRuntimeException(String message) {
        super(
                String.format(MESSAGE, message)
        );
    }

}
