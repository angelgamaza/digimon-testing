package com.gamaza.examples.digimon.repository;

import com.gamaza.examples.digimon.entity.Type;
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

import static com.gamaza.examples.digimon.config.testcontainers.TestConstants.TYPE_TAG;

/**
 * Test class for {@link TypeRepository}
 */
@Tag(value = TYPE_TAG)
@DisplayName(value = "[Repository]: Type")
class TypeRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private TypeRepository repository;

    @Nested
    @Order(value = 0)
    @DisplayName(value = "[Method]: save(Type type)")
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
            Type input = new Type();
            input.setName("Virus");
            /*
             * When
             */
            Type output = repository.save(input);
            /*
             * Then
             */
            // Entity checks
            Assertions.assertNotNull(output);
            Assertions.assertEquals(1L, output.getId());
            Assertions.assertEquals("Virus", output.getName());
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
            Type input = new Type();
            // When
            // Then
            DataIntegrityViolationException exceptionThrown = Assertions.assertThrows(DataIntegrityViolationException.class, () -> repository.save(input));
            Assertions.assertTrue(exceptionThrown.getMessage().contains("ERROR: null value in column \"name\" of relation \"type\" violates not-null constraint"));
        }

        @Test
        @Transactional
        @Order(value = 2)
        @DisplayName(value = "3. Given an existing input -> Call to repository and throw a DataIntegrityViolationException")
        @Sql(scripts = "/sql/type/inserts.sql")
        void Given_ExistingType_When_SaveMethodIsCalled_Then_DataIntegrityViolationExceptionIsThrown() {
            // Given
            Type input = new Type();
            input.setName("Virus");
            // When
            // Then
            DataIntegrityViolationException exceptionThrown = Assertions.assertThrows(DataIntegrityViolationException.class, () -> repository.save(input));
            Assertions.assertTrue(exceptionThrown.getMessage().contains("ERROR: duplicate key value violates unique constraint \"uk_type_name\""));
        }

    }

    @Nested
    @Order(value = 1)
    @DisplayName(value = "[Method]: findAll()")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    @Sql(scripts = "/sql/type/inserts.sql")
    class FindAllTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given several Types in database -> Call to repository and return them")
        void Given_SeveralTypesInDatabase_When_FindAllMethodIsCalled_Then_ReturnThem() {
            // Given
            // When
            List<Type> retrievedTypes = repository.findAll();
            // Then
            Assertions.assertFalse(retrievedTypes.isEmpty());
            Assertions.assertEquals(4, retrievedTypes.size());
        }

    }

    @Nested
    @Order(value = 2)
    @DisplayName(value = "[Method]: findById(Long id)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    @Sql(scripts = "/sql/type/inserts.sql")
    class FindByIdTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given a valid ID -> Call to repository and return the associated Type")
        void Given_ValidId_When_FindByIdMethodIsCalled_Then_ReturnAssociatedType() {
            // Given
            Long id = 1L;
            // When
            Optional<Type> retrievedTypeOptional = repository.findById(id);
            // Then
            Assertions.assertTrue(retrievedTypeOptional.isPresent());
            Type retrievedType = retrievedTypeOptional.get();
            Assertions.assertEquals(id, retrievedType.getId());
            Assertions.assertEquals("Vaccine", retrievedType.getName());
        }

        @Test
        @Order(value = 1)
        @DisplayName(value = "2. Given an invalid ID -> Call to repository and return Optional.empty()")
        void Given_InvalidId_When_FindByIdMethodIsCalled_Then_ReturnEmptyOptional() {
            // Given
            Long id = 100L;
            // When
            Optional<Type> retrievedTypeOptional = repository.findById(id);
            // Then
            Assertions.assertTrue(retrievedTypeOptional.isEmpty());
            NoSuchElementException exceptionThrown = Assertions.assertThrows(NoSuchElementException.class, retrievedTypeOptional::get);
            Assertions.assertTrue(exceptionThrown.getMessage().contentEquals("No value present"));
        }

    }

    @Nested
    @Order(value = 3)
    @DisplayName(value = "[Method]: findByName(String name)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    @Sql(scripts = "/sql/type/inserts.sql")
    class FindByNameTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given a valid name -> Call to repository and return the associated Type")
        void Given_ValidName_When_FindByNameMethodIsCalled_Then_ReturnAssociatedType() {
            // Given
            String name = "Virus";
            // When
            Optional<Type> retrievedTypeOptional = repository.findByName(name);
            // Then
            Assertions.assertTrue(retrievedTypeOptional.isPresent());
            Type retrievedType = retrievedTypeOptional.get();
            Assertions.assertEquals(2L, retrievedType.getId());
            Assertions.assertEquals(name, retrievedType.getName());
        }

        @Test
        @Order(value = 1)
        @DisplayName(value = "2. Given an invalid name -> Call to repository and return Optional.empty()")
        void Given_InvalidId_When_FindByNameMethodIsCalled_Then_ReturnEmptyOptional() {
            // Given
            String name = "Vacuna";
            // When
            Optional<Type> retrievedTypeOptional = repository.findByName(name);
            // Then
            Assertions.assertTrue(retrievedTypeOptional.isEmpty());
            NoSuchElementException exceptionThrown = Assertions.assertThrows(NoSuchElementException.class, retrievedTypeOptional::get);
            Assertions.assertTrue(exceptionThrown.getMessage().contentEquals("No value present"));
        }

    }

    @Nested
    @Order(value = 4)
    @DisplayName(value = "[Method]: deleteById(Long id)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    @Sql(scripts = "/sql/type/inserts.sql")
    class DeleteByIdTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given a valid ID -> Call to repository and delete the associated Type")
        void Given_ValidId_When_DeleteByIdMethodIsCalled_Then_AssociatedTypeIsRemoved() {
            // Given
            Long id = 1L;
            // When
            repository.deleteById(id);
            // Then
            Optional<Type> retrievedTypeOptional = repository.findById(id);
            Assertions.assertTrue(retrievedTypeOptional.isEmpty());
            NoSuchElementException exceptionThrown = Assertions.assertThrows(NoSuchElementException.class, retrievedTypeOptional::get);
            Assertions.assertTrue(exceptionThrown.getMessage().contentEquals("No value present"));
            Assertions.assertEquals(3, repository.findAll().size());
        }

    }

}