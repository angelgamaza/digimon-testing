package com.gamaza.examples.springtest.exception;

/**
 * Exception for Already Exists cases
 */
public class AlreadyExistsException extends RuntimeException {

    // Class constants
    private static final String ALREADY_EXISTS_MESSAGE = "The object [%s] with parameters [%s] already exists!";

    /**
     * Constructor
     *
     * @param object     The object type
     * @param parameters The object parameters
     */
    public AlreadyExistsException(String object, String parameters) {
        super(
                String.format(ALREADY_EXISTS_MESSAGE, object, parameters)
        );
    }

}
