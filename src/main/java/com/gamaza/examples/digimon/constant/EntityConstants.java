package com.gamaza.examples.digimon.constant;

/**
 * Constants for Entity classes
 */
public final class EntityConstants {

    /*
     * Generic Entity constants
     */
    public static final String SCHEMA_PUBLIC_STRING = "public";

    /*
     * Entity names
     */
    public static final String TYPE_ENTITY_NAME = "type";
    public static final String LEVEL_ENTITY_NAME = "level";
    public static final String DIGIMON_ENTITY_NAME = "digimon";

    /*
     * Entity fields
     */
    public static final String FIELD_ID_STRING = "id";
    public static final String FIELD_NAME_STRING = "name";
    public static final String FIELD_CREATED_DATE_STRING = "created_date";
    public static final String FIELD_MODIFIED_DATE_STRING = "modified_date";

    /*
     * Relationship fields
     */
    public static final String TYPE_RELATIONSHIP_STRING = "type";
    public static final String LEVEL_RELATIONSHIP_STRING = "level";
    public static final String DIGIMONS_RELATIONSHIP_STRING = "digimons";

    /*
     * Unique constraints
     */
    public static final String UK_TYPE_NAME_STRING = "uk_type_name";
    public static final String UK_LEVEL_NAME_STRING = "uk_level_name";
    public static final String UK_DIGIMON_NAME_STRING = "uk_digimon_name";

    /*
     * Unique indexes
     */
    public static final String UINDEX_TYPE_NAME_STRING = "ui_type_name";
    public static final String UINDEX_LEVEL_NAME_STRING = "ui_level_name";
    public static final String UINDEX_DIGIMON_NAME_STRING = "ui_digimon_name";

    /*
     * Foreign keys
     */
    public static final String FK_DIGIMON_TYPE = "fk_digimon_type";
    public static final String FK_DIGIMON_LEVEL = "fk_digimon_level";

    /*
     * Object names
     */
    public static final String TYPE_OBJECT_NAME = "Type";
    public static final String LEVEL_OBJECT_NAME = "Level";
    public static final String DIGIMON_OBJECT_NAME = "Digimon";

    /*
     * Entity graphs
     */

    // Graphs
    public static final String TYPE_ENTITY_GRAPH = "graph.type";
    public static final String LEVEL_ENTITY_GRAPH = "graph.level";
    public static final String DIGIMON_ENTITY_GRAPH = "graph.digimon";

    // Subgraphs
    public static final String DIGIMON_TYPE_ENTITY_SUBGRAPH = "subgraph.digimon.type";
    public static final String DIGIMON_LEVEL_ENTITY_SUBGRAPH = "subgraph.digimon.level";

    /**
     * Private constructor
     */
    private EntityConstants() {
        throw new IllegalStateException("Could not instantiate EntityConstants class!");
    }

}
