package com.gamaza.examples.springtest.util;

import io.micrometer.common.util.StringUtils;

/**
 * Utils for exceptions
 */
public final class ExceptionUtils {

    /**
     * Get the cause or the default message from the given {@link Throwable}
     *
     * @param throwable The {@link Throwable} instance
     * @return The first non-blank message or the class name if none found
     */
    public static String getCauseOrDefaultMessage(Throwable throwable) {
        if (throwable == null) {
            return null;
        }
        String message = ExceptionUtils.getFirstNonBlank(
                (throwable.getCause() != null) ? throwable.getCause().getLocalizedMessage() : null,
                throwable.getLocalizedMessage(),
                throwable.getMessage()
        );
        return message != null ? message : throwable.getClass().getSimpleName();
    }

    /**
     * Get the first non-blank string from the provided messages
     *
     * @param messages Varargs of messages
     * @return The first non-blank message or null if none found
     */
    private static String getFirstNonBlank(String... messages) {
        for (String message : messages) {
            if (!StringUtils.isBlank(message)) {
                return message;
            }
        }
        return null;
    }

    /**
     * Private constructor to prevent instantiation
     */
    private ExceptionUtils() {
        throw new IllegalStateException("Could not instantiate ExceptionUtils class!");
    }

}