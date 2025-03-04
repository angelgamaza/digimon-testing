package com.gamaza.examples.springtest.repository;

import com.gamaza.examples.springtest.entity.Digimon;
import com.gamaza.examples.springtest.entity.Level;
import com.gamaza.examples.springtest.entity.Type;
import com.gamaza.examples.springtest.repository.base.BaseRepositoryTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.gamaza.examples.springtest.config.testcontainers.TestConstants.DIGIMON_TAG;

/**
 * Test class for {@link DigimonRepository}
 */
@Tag(value = DIGIMON_TAG)
@DisplayName(value = "[Repository]: Digimon")
class DigimonRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private DigimonRepository repository;

    @Nested
    @Order(value = 0)
    @DisplayName(value = "[Method]: save(Digimon digimon)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class SaveTests {

        @Test
        @Transactional
        @Order(value = 0)
        @DisplayName(value = "1. Given a valid input -> Call to repository and save it")
        @Sql(scripts = "/sql/type/inserts.sql")
        @Sql(scripts = "/sql/level/inserts.sql")
        void Given_ValidInput_When_SaveMethodIsCalled_Then_ItIsSaved() {
            /*
             * Given
             */
            Digimon input = new Digimon();
            input.setName("SkullGreymon");
            input.setLevel(new Level(5L));
            input.setType(new Type(2L));
            /*
             * When
             */
            Digimon output = repository.saveAndRefresh(input);
            /*
             * Then
             */
            // Entity checks
            Assertions.assertNotNull(output);
            Assertions.assertEquals(1L, output.getId());
            Assertions.assertEquals("SkullGreymon", output.getName());
            // Relationship checks
            Assertions.assertEquals("Virus", output.getType().getName());
            Assertions.assertEquals("Mega", output.getLevel().getName());
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
        @DisplayName(value = "2. Given a valid input -> Call to repository and save and refresh it")
        @Sql(scripts = "/sql/type/inserts.sql")
        @Sql(scripts = "/sql/level/inserts.sql")
        void Given_ValidInput_When_SaveMethodIsCalled_Then_ItIsSavedAnRefreshed() {
            /*
             * Given
             */
            Digimon input = new Digimon();
            input.setName("SkullGreymon");
            input.setLevel(new Level(5L));
            input.setType(new Type(2L));
            /*
             * When
             */
            Digimon output = repository.save(input);
            repository.refresh(output);
            /*
             * Then
             */
            // Entity checks
            Assertions.assertNotNull(output);
            Assertions.assertEquals(1L, output.getId());
            Assertions.assertEquals("SkullGreymon", output.getName());
            // Relationship checks
            Assertions.assertEquals("Virus", output.getType().getName());
            Assertions.assertEquals("Mega", output.getLevel().getName());
            // Auditable checks
            LocalDateTime createdDate = output.getCreatedDate();
            LocalDateTime modifiedDate = output.getModifiedDate();
            Assertions.assertNotNull(createdDate);
            Assertions.assertNotNull(modifiedDate);
            Assertions.assertTrue(modifiedDate.isAfter(createdDate) || modifiedDate.isEqual(createdDate));
        }

        @Test
        @Transactional
        @Order(value = 2)
        @DisplayName(value = "3. Given an empty input -> Call to repository and throw a DataIntegrityViolationException")
        void Given_EmptyInput_When_SaveMethodIsCalled_Then_DataIntegrityViolationExceptionIsThrown() {
            // Given
            Digimon input = new Digimon();
            // When
            // Then
            DataIntegrityViolationException exceptionThrown = Assertions.assertThrows(DataIntegrityViolationException.class, () -> repository.save(input));
            Assertions.assertTrue(exceptionThrown.getMessage().contains("ERROR: null value in column \"name\" of relation \"digimon\" violates not-null constraint"));
        }

        @Test
        @Transactional
        @Order(value = 3)
        @DisplayName(value = "4. Given an existing input -> Call to repository and throw a DataIntegrityViolationException")
        @Sql(scripts = "/sql/digimon/inserts.sql")
        void Given_ExistingInput_When_SaveMethodIsCalled_Then_DataIntegrityViolationExceptionIsThrown() {
            // Given
            Digimon input = new Digimon();
            input.setName("MetalGreymon");
            input.setLevel(new Level(5L));
            input.setType(new Type(2L));
            // When
            // Then
            DataIntegrityViolationException exceptionThrown = Assertions.assertThrows(DataIntegrityViolationException.class, () -> repository.save(input));
            Assertions.assertTrue(exceptionThrown.getMessage().contains("ERROR: duplicate key value violates unique constraint \"uk_digimon_name\""));
        }

    }

    @Nested
    @Order(value = 1)
    @DisplayName(value = "[Method]: findAll()")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    @Sql(scripts = "/sql/digimon/inserts.sql")
    class FindAllTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given several Digimons in database -> Call to repository and return them")
        void Given_SeveralDigimonsInDatabase_When_FindAllMethodIsCalled_Then_ReturnThem() {
            // Given
            // When
            List<Digimon> retrievedDigimons = repository.findAll();
            // Then
            Assertions.assertFalse(retrievedDigimons.isEmpty());
            Assertions.assertEquals(4, retrievedDigimons.size());
        }

    }

    @Nested
    @Order(value = 2)
    @DisplayName(value = "[Method]: findById(Long id)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    @Sql(scripts = "/sql/digimon/inserts.sql")
    class FindByIdTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given a valid ID -> Call to repository and return the associated Digimon")
        void Given_ValidId_When_FindByIdMethodIsCalled_Then_ReturnAssociatedDigimon() {
            // Given
            Long id = 2L;
            // When
            Optional<Digimon> retrievedDigimonOptional = repository.findById(id);
            // Then
            Assertions.assertTrue(retrievedDigimonOptional.isPresent());
            Digimon retrievedDigimon = retrievedDigimonOptional.get();
            Assertions.assertEquals(id, retrievedDigimon.getId());
            Assertions.assertEquals("Greymon", retrievedDigimon.getName());
        }

        @Test
        @Order(value = 1)
        @DisplayName(value = "2. Given an invalid ID -> Call to repository and return Optional.empty()")
        void Given_InvalidId_When_FindByIdMethodIsCalled_Then_ReturnEmptyOptional() {
            // Given
            Long id = 100L;
            // When
            Optional<Digimon> retrievedDigimonOptional = repository.findById(id);
            // Then
            Assertions.assertTrue(retrievedDigimonOptional.isEmpty());
            NoSuchElementException exceptionThrown = Assertions.assertThrows(NoSuchElementException.class, retrievedDigimonOptional::get);
            Assertions.assertTrue(exceptionThrown.getMessage().contentEquals("No value present"));
        }

    }

    @Nested
    @Order(value = 3)
    @DisplayName(value = "[Method]: findByName(String name)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    @Sql(scripts = "/sql/digimon/inserts.sql")
    class FindByNameTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given a valid name -> Call to repository and return the associated Digimon")
        void Given_ValidName_When_FindByNameMethodIsCalled_Then_ReturnAssociatedDigimon() {
            // Given
            String name = "MetalGreymon";
            // When
            Optional<Digimon> retrievedDigimonOptional = repository.findByName(name);
            // Then
            Assertions.assertTrue(retrievedDigimonOptional.isPresent());
            Digimon retrievedDigimon = retrievedDigimonOptional.get();
            Assertions.assertEquals(3L, retrievedDigimon.getId());
            Assertions.assertEquals(name, retrievedDigimon.getName());
        }

        @Test
        @Order(value = 1)
        @DisplayName(value = "2. Given an invalid name -> Call to repository and return Optional.empty()")
        void Given_InvalidId_When_FindByNameMethodIsCalled_Then_ReturnEmptyOptional() {
            // Given
            String name = "BlackWarGreymon";
            // When
            Optional<Digimon> retrievedDigimonOptional = repository.findByName(name);
            // Then
            Assertions.assertTrue(retrievedDigimonOptional.isEmpty());
            NoSuchElementException exceptionThrown = Assertions.assertThrows(NoSuchElementException.class, retrievedDigimonOptional::get);
            Assertions.assertTrue(exceptionThrown.getMessage().contentEquals("No value present"));
        }

    }

    @Nested
    @Order(value = 4)
    @DisplayName(value = "[Method]: deleteById(Long id)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    @Sql(scripts = "/sql/digimon/inserts.sql")
    class DeleteByIdTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given a valid ID -> Call to repository and delete the associated Digimon")
        void Given_ValidId_When_DeleteByIdMethodIsCalled_Then_AssociatedDigimonIsRemoved() {
            // Given
            Long id = 1L;
            // When
            repository.deleteById(id);
            // Then
            Optional<Digimon> retrievedDigimonOptional = repository.findById(id);
            Assertions.assertTrue(retrievedDigimonOptional.isEmpty());
            NoSuchElementException exceptionThrown = Assertions.assertThrows(NoSuchElementException.class, retrievedDigimonOptional::get);
            Assertions.assertTrue(exceptionThrown.getMessage().contentEquals("No value present"));
            Assertions.assertEquals(3, repository.findAll().size());
        }

    }

}