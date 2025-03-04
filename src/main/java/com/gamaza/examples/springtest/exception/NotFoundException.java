package com.gamaza.examples.springtest.exception;

/**
 * Exception for Not Found cases
 */
public class NotFoundException extends RuntimeException {

    // Class constants
    private static final String NOT_FOUND_MESSAGE = "The object [%s] with parameters [%s] not found!";

    /**
     * Constructor
     *
     * @param object     The object type
     * @param parameters The object parameters
     */
    public NotFoundException(String object, String parameters) {
        super(
                String.format(NOT_FOUND_MESSAGE, object, parameters)
        );
    }

}
