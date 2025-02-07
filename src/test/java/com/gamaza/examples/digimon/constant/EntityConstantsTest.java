package com.gamaza.examples.digimon.constant;

import com.gamaza.examples.digimon.config.EstandarBaseTest;
import org.junit.jupiter.api.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static com.gamaza.examples.digimon.config.testcontainers.TestConstants.CONSTANT_TAG;
import static com.gamaza.examples.digimon.constant.EntityConstants.*;

/**
 * Test class for {@link EntityConstants}
 */
@Tag(value = CONSTANT_TAG)
@DisplayName(value = "[Constant]: Entity")
class EntityConstantsTest extends EstandarBaseTest {

    @Nested
    @Order(value = 0)
    @DisplayName(value = "[Scope]: Values")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class ValuesTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given the Entity constant values -> Check if them are correct")
        void Given_EntityConstantValues_When_TheyAreChecked_Then_TheyAreCorrect() {
            // Generic entity
            Assertions.assertEquals("public", SCHEMA_PUBLIC_STRING);
            // Entity names
            Assertions.assertEquals("type", TYPE_ENTITY_NAME);
            Assertions.assertEquals("level", LEVEL_ENTITY_NAME);
            Assertions.assertEquals("digimon", DIGIMON_ENTITY_NAME);
            // Entity fields
            Assertions.assertEquals("id", FIELD_ID_STRING);
            Assertions.assertEquals("name", FIELD_NAME_STRING);
            Assertions.assertEquals("created_date", FIELD_CREATED_DATE_STRING);
            Assertions.assertEquals("modified_date", FIELD_MODIFIED_DATE_STRING);
            // Relationship fields
            Assertions.assertEquals("type", TYPE_RELATIONSHIP_STRING);
            Assertions.assertEquals("level", LEVEL_RELATIONSHIP_STRING);
            Assertions.assertEquals("digimons", DIGIMONS_RELATIONSHIP_STRING);
        }

        @Test
        @Order(value = 1)
        @DisplayName(value = "2. Given the database restrictions constant values -> Check if the them are correct")
        void Given_DatabaseRestrictionsConstantValues_When_TheyAreChecked_Then_TheyAreCorrect() {
            // Unique constraints
            Assertions.assertEquals("uk_type_name", UK_TYPE_NAME_STRING);
            Assertions.assertEquals("uk_level_name", UK_LEVEL_NAME_STRING);
            Assertions.assertEquals("uk_digimon_name", UK_DIGIMON_NAME_STRING);
            // Unique indexes
            Assertions.assertEquals("ui_type_name", UINDEX_TYPE_NAME_STRING);
            Assertions.assertEquals("ui_level_name", UINDEX_LEVEL_NAME_STRING);
            Assertions.assertEquals("ui_digimon_name", UINDEX_DIGIMON_NAME_STRING);
            // Foreign keys
            Assertions.assertEquals("fk_digimon_type", FK_DIGIMON_TYPE);
            Assertions.assertEquals("fk_digimon_level", FK_DIGIMON_LEVEL);
            // Entity graphs
            Assertions.assertEquals("graph.type", TYPE_ENTITY_GRAPH);
            Assertions.assertEquals("graph.level", LEVEL_ENTITY_GRAPH);
            Assertions.assertEquals("graph.digimon", DIGIMON_ENTITY_GRAPH);
            // Subgraphs
            Assertions.assertEquals("subgraph.digimon.type", DIGIMON_TYPE_ENTITY_SUBGRAPH);
            Assertions.assertEquals("subgraph.digimon.level", DIGIMON_LEVEL_ENTITY_SUBGRAPH);
            // Object names
            Assertions.assertEquals("Type", TYPE_OBJECT_NAME);
            Assertions.assertEquals("Level", LEVEL_OBJECT_NAME);
            Assertions.assertEquals("Digimon", DIGIMON_OBJECT_NAME);
        }

    }

    @Nested
    @Order(value = 1)
    @DisplayName(value = "[Scope]: Constructor")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class ConstructorTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given the constant class -> Check if the constructor is private")
        void Given_EntityConstantsClass_When_ConstructorIsCalled_Then_ItIsPrivate() throws NoSuchMethodException {
            // Get the private constructor of the Constants class
            Constructor<EntityConstants> constructor = EntityConstants.class.getDeclaredConstructor();
            // Make the constructor accessible
            constructor.setAccessible(true);
            // Verify that an IllegalStateException is thrown when we attempt to instantiate the class
            InvocationTargetException exception = Assertions.assertThrows(InvocationTargetException.class, constructor::newInstance);
            // Extract the actual exception (IllegalStateException) from the InvocationTargetException
            Assertions.assertInstanceOf(IllegalStateException.class, exception.getCause());
            // Verify the exception message
            IllegalStateException cause = (IllegalStateException) exception.getCause();
            Assertions.assertEquals("Could not instantiate EntityConstants class!", cause.getMessage());
        }

    }

}
