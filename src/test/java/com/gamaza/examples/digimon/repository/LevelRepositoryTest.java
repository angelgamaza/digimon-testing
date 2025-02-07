package com.gamaza.examples.digimon.repository;

import com.gamaza.examples.digimon.entity.Level;
import com.gamaza.examples.digimon.repository.base.BaseRepositoryTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.gamaza.examples.digimon.config.testcontainers.TestConstants.LEVEL_TAG;

/**
 * Test class for {@link LevelRepository}
 */
@Tag(value = LEVEL_TAG)
@DisplayName(value = "[Repository]: Level")
class LevelRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private LevelRepository repository;

    @Nested
    @Order(value = 0)
    @DisplayName(value = "[Method]: save(Level level)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class SaveTests {

        @Test
        @Transactional
        @Order(value = 0)
        @DisplayName(value = "1. Given a valid input -> Call to repository and save it")
        void Given_ValidInput_When_SaveMethodIsCalled_Then_ItIsSaved() {
            /*
             * Given
             */
            Level input = new Level();
            input.setName("Baby");
            /*
             * When
             */
            Level output = repository.save(input);
            /*
             * Then
             */
            // Entity checks
            Assertions.assertNotNull(output);
            Assertions.assertEquals(1L, output.getId());
            Assertions.assertEquals("Baby", output.getName());
            // Relationship checks
            Assertions.assertEquals(0, output.getDigimons().size());
            // Auditable checks
            LocalDateTime createdDate = output.getCreatedDate();
            LocalDateTime modifiedDate = output.getModifiedDate();
            Assertions.assertNotNull(createdDate);
            Assertions.assertNotNull(modifiedDate);
            Assertions.assertTrue(modifiedDate.isAfter(createdDate) || modifiedDate.isEqual(createdDate));
        }

        @Test
        @Transactional
        @Order(value = 1)
        @DisplayName(value = "2. Given an empty input -> Call to repository and throw a DataIntegrityViolationException")
        void Given_EmptyInput_When_SaveMethodIsCalled_Then_DataIntegrityViolationExceptionIsThrown() {
            // Given
            Level input = new Level();
            // When
            // Then
            DataIntegrityViolationException exceptionThrown = Assertions.assertThrows(DataIntegrityViolationException.class, () -> repository.save(input));
            Assertions.assertTrue(exceptionThrown.getMessage().contains("ERROR: null value in column \"name\" of relation \"level\" violates not-null constraint"));
        }

        @Test
        @Transactional
        @Order(value = 2)
        @DisplayName(value = "3. Given an existing input -> Call to repository and throw a DataIntegrityViolationException")
        @Sql(scripts = "/sql/level/inserts.sql")
        void Given_ExistingInput_When_SaveMethodIsCalled_Then_DataIntegrityViolationExceptionIsThrown() {
            // Given
            Level input = new Level();
            input.setName("Baby");
            // When
            // Then
            DataIntegrityViolationException exceptionThrown = Assertions.assertThrows(DataIntegrityViolationException.class, () -> repository.save(input));
            Assertions.assertTrue(exceptionThrown.getMessage().contains("ERROR: duplicate key value violates unique constraint \"uk_level_name\""));
        }

    }

    @Nested
    @Order(value = 1)
    @DisplayName(value = "[Method]: findAll()")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    @Sql(scripts = "/sql/level/inserts.sql")
    class FindAllTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given several Levels in database -> Call to repository and return them")
        void Given_SeveralLevelsInDatabase_When_FindAllMethodIsCalled_Then_ReturnThem() {
            // Given
            // When
            List<Level> retrievedLevels = repository.findAll();
            // Then
            Assertions.assertFalse(retrievedLevels.isEmpty());
            Assertions.assertEquals(6, retrievedLevels.size());
        }

    }

    @Nested
    @Order(value = 2)
    @DisplayName(value = "[Method]: findById(Long id)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    @Sql(scripts = "/sql/level/inserts.sql")
    class FindByIdTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given a valid ID -> Call to repository and return the associated Level")
        void Given_ValidId_When_FindByIdMethodIsCalled_Then_ReturnAssociatedLevel() {
            // Given
            Long id = 1L;
            // When
            Optional<Level> retrievedLevelOptional = repository.findById(id);
            // Then
            Assertions.assertTrue(retrievedLevelOptional.isPresent());
            Level retrievedLevel = retrievedLevelOptional.get();
            Assertions.assertEquals(id, retrievedLevel.getId());
            Assertions.assertEquals("Baby", retrievedLevel.getName());
        }

        @Test
        @Order(value = 1)
        @DisplayName(value = "2. Given an invalid ID -> Call to repository and return Optional.empty()")
        void Given_InvalidId_When_FindByIdMethodIsCalled_Then_ReturnEmptyOptional() {
            // Given
            Long id = 100L;
            // When
            Optional<Level> retrievedLevelOptional = repository.findById(id);
            // Then
            Assertions.assertTrue(retrievedLevelOptional.isEmpty());
            NoSuchElementException exceptionThrown = Assertions.assertThrows(NoSuchElementException.class, retrievedLevelOptional::get);
            Assertions.assertTrue(exceptionThrown.getMessage().contentEquals("No value present"));
        }

    }

    @Nested
    @Order(value = 3)
    @DisplayName(value = "[Method]: findByName(String name)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    @Sql(scripts = "/sql/level/inserts.sql")
    class FindByNameTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given a valid name -> Call to repository and return the associated Level")
        void Given_ValidName_When_FindByNameMethodIsCalled_Then_ReturnAssociatedLevel() {
            // Given
            String name = "Champion";
            // When
            Optional<Level> retrievedLevelOptional = repository.findByName(name);
            // Then
            Assertions.assertTrue(retrievedLevelOptional.isPresent());
            Level retrievedLevel = retrievedLevelOptional.get();
            Assertions.assertEquals(4L, retrievedLevel.getId());
            Assertions.assertEquals(name, retrievedLevel.getName());
        }

        @Test
        @Order(value = 1)
        @DisplayName(value = "2. Given an invalid name -> Call to repository and return Optional.empty()")
        void Given_InvalidId_When_FindByNameMethodIsCalled_Then_ReturnEmptyOptional() {
            // Given
            String name = "Campeón";
            // When
            Optional<Level> retrievedLevelOptional = repository.findByName(name);
            // Then
            Assertions.assertTrue(retrievedLevelOptional.isEmpty());
            NoSuchElementException exceptionThrown = Assertions.assertThrows(NoSuchElementException.class, retrievedLevelOptional::get);
            Assertions.assertTrue(exceptionThrown.getMessage().contentEquals("No value present"));
        }

    }

    @Nested
    @Order(value = 4)
    @DisplayName(value = "[Method]: deleteById(Long id)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    @Sql(scripts = "/sql/level/inserts.sql")
    class DeleteByIdTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given a valid ID -> Call to repository and delete the associated Level")
        void Given_ValidId_When_DeleteByIdMethodIsCalled_Then_AssociatedLevelIsRemoved() {
            // Given
            Long id = 1L;
            // When
            repository.deleteById(id);
            // Then
            Optional<Level> retrievedLevelOptional = repository.findById(id);
            Assertions.assertTrue(retrievedLevelOptional.isEmpty());
            NoSuchElementException exceptionThrown = Assertions.assertThrows(NoSuchElementException.class, retrievedLevelOptional::get);
            Assertions.assertTrue(exceptionThrown.getMessage().contentEquals("No value present"));
            Assertions.assertEquals(5, repository.findAll().size());
        }

    }

}