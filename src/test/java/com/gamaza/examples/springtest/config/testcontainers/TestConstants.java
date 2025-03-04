package com.gamaza.examples.springtest.config.testcontainers;

/**
 * Constants for Tests
 */
public final class TestConstants {

    /**
     * Test container image configuration
     */
    public static final String POSTGRESQL_IMAGE = "postgres:17.2-alpine";
    public static final String POSTGRESQL_INIT_SCRIPT = "sql/schema.sql";

    /**
     * Test tags
     */
    // Classes
    public static final String REPOSITORY_TAG = "repository";
    public static final String SERVICE_TAG = "rervice";
    public static final String CONTROLLER_TAG = "controller";
    public static final String MAPPER_TAG = "mapper";
    public static final String CONSTANT_TAG = "constant";
    public static final String EXCEPTION_TAG = "exception";
    public static final String UTIL_TAG = "utils";
    // API Entities
    public static final String TYPE_TAG = "type";
    public static final String LEVEL_TAG = "level";
    public static final String DIGIMON_TAG = "digimon";

    /*
     * Object names
     */
    public static final String TYPE_OBJECT_NAME = "Type";
    public static final String LEVEL_OBJECT_NAME = "Level";
    public static final String DIGIMON_OBJECT_NAME = "Digimon";

    /**
     * Controllers base URL
     */
    public static final String TYPE_BASE_URL = "/type";
    public static final String LEVEL_BASE_URL = "/level";
    public static final String DIGIMON_BASE_URL = "/digimon";

    /**
     * Private constructor
     */
    private TestConstants() {
        throw new IllegalStateException("Could not instantiate TestConstants class!");
    }

}
