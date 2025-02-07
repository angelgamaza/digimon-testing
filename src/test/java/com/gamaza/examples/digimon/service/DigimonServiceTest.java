package com.gamaza.examples.digimon.service;

import com.gamaza.examples.digimon.dto.request.digimon.DigimonPatchDto;
import com.gamaza.examples.digimon.dto.request.digimon.DigimonPostDto;
import com.gamaza.examples.digimon.dto.response.digimon.DigimonDto;
import com.gamaza.examples.digimon.dto.response.digimon.DigimonRelationsDto;
import com.gamaza.examples.digimon.entity.Digimon;
import com.gamaza.examples.digimon.entity.Level;
import com.gamaza.examples.digimon.entity.Type;
import com.gamaza.examples.digimon.exception.AlreadyExistsException;
import com.gamaza.examples.digimon.exception.GenericRuntimeException;
import com.gamaza.examples.digimon.exception.NotFoundException;
import com.gamaza.examples.digimon.mapper.DigimonMapper;
import com.gamaza.examples.digimon.repository.DigimonRepository;
import com.gamaza.examples.digimon.service.base.BaseServiceTest;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.gamaza.examples.digimon.config.testcontainers.TestConstants.DIGIMON_TAG;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.springframework.test.context.bean.override.mockito.MockReset.AFTER;

/**
 * Test class for {@link DigimonService}
 */
@Tag(value = DIGIMON_TAG)
@DisplayName(value = "[Service]: Digimon")
class DigimonServiceTest extends BaseServiceTest {

    @MockitoBean(enforceOverride = true, reset = AFTER)
    private DigimonRepository repository;

    @MockitoBean(enforceOverride = true, reset = AFTER)
    private DigimonMapper mapper;

    @Autowired
    private DigimonService service;

    @Nested
    @Order(value = 0)
    @DisplayName(value = "[Method]: save(DigimonPostDto input)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class SaveTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given a valid input data -> Call to save() method and return it")
        void Given_ValidIninput_When_SaveMethodIsCalled_Then_SaveAndReturnIt() {
            /*
             * Given
             */
            // Input
            DigimonPostDto input = DigimonPostDto.of("Agumon");
            // Mapped input
            Digimon mappedInput = new Digimon();
            mappedInput.setName("Agumon");
            // Saved input
            Digimon savedInput = new Digimon(1L);
            savedInput.setName("Agumon");
            // Expected output
            DigimonDto expectedOutput = DigimonDto.of(1L, "Agumon");
            // Mocks
            Mockito.when(mapper.asEntity(input)).thenReturn(mappedInput);
            Mockito.when(repository.save(mappedInput)).thenReturn(savedInput);
            Mockito.when(mapper.asDto(savedInput)).thenReturn(expectedOutput);

            /*
             * When
             */
            DigimonDto output = service.save(input);

            /*
             * Then
             */
            // Assertions
            Assertions.assertNotNull(output);
            Assertions.assertEquals(expectedOutput.getId(), output.getId());
            Assertions.assertEquals(expectedOutput.getName(), output.getName());
            // Verifications
            Mockito.verify(mapper, times(1)).asEntity(input);
            Mockito.verify(mapper, times(1)).asDto(savedInput);
            Mockito.verify(repository, times(1)).save(mappedInput);
        }

        @Test
        @Order(value = 1)
        @DisplayName(value = "2. Given a NullPointerException in repository -> Call to save() method and throw a GenericRuntimeException")
        void Given_NullPointerException_When_SaveMethodIsCalled_Then_ThrowGenericRuntimeException() {
            // Given
            Mockito
                    .when(repository.save(any()))
                    .thenThrow(NullPointerException.class);
            // When
            // Then
            Assertions.assertThrowsExactly(GenericRuntimeException.class, () -> service.save(any()));
            Mockito.verify(repository, times(1)).save(any());
        }

        @Test
        @Order(value = 2)
        @DisplayName(value = "3. Given a DataIntegrityViolationException in repository -> Call to save() method and throw an AlreadyExistsException")
        void Given_DataIntegrityViolationException_When_SaveMethodIsCalled_Then_ThrowAlreadyExistsException() {
            // Given
            DigimonPostDto input = DigimonPostDto.of("Agumon");
            Mockito
                    .when(repository.save(any()))
                    .thenThrow(DataIntegrityViolationException.class);
            // When
            // Then
            Assertions.assertThrowsExactly(AlreadyExistsException.class, () -> service.save(input));
            Mockito.verify(repository, times(1)).save(any());
        }

    }

    @Nested
    @Order(value = 1)
    @DisplayName(value = "[Method]: findAll()")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class FindAllTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given several entities in database -> Call to findAll() method and return them")
        void Given_SeveralEntities_When_FindAllMethodIsCalled_Then_ReturnThem() {
            /*
             * Given
             */
            // Data
            Digimon agumon = new Digimon(1L);
            Digimon greymon = new Digimon(2L);
            List<Digimon> expectedOutput = Arrays.asList(agumon, greymon);
            // Mocks
            Mockito.when(repository.findAll()).thenReturn(expectedOutput);
            Mockito.when(mapper.asDto(any(Digimon.class))).thenReturn(new DigimonDto());

            /*
             * When
             */
            List<DigimonDto> output = service.findAll();

            /*
             * Then
             */
            // Assertions
            Assertions.assertNotNull(output);
            Assertions.assertEquals(expectedOutput.size(), output.size());
            // Verifications
            Mockito.verify(repository, times(1)).findAll();
            Mockito.verify(mapper, times(2)).asDto(any());
        }

    }

    @Nested
    @Order(value = 2)
    @DisplayName(value = "[Method]: findById(Long id)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class FindByIdTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given an entity in database with the given ID -> Call to findById() method and return it")
        void Given_EntityWithGivenId_When_FindByIdMethodIsCalled_Then_ReturnIt() {
            /*
             * Given
             */
            // ID
            Long id = 1L;
            // Entity
            Digimon entity = new Digimon(1L);
            entity.setName("Agumon");
            // Expected output
            DigimonRelationsDto expectedOutput = DigimonRelationsDto.of(1L, "Agumon");
            // Mocks
            Mockito.when(repository.findById(id)).thenReturn(Optional.of(entity));
            Mockito.when(mapper.asRelationsDto(entity)).thenReturn(expectedOutput);

            /*
             * When
             */
            DigimonRelationsDto output = service.findById(id);

            /*
             * Then
             */
            // Assertions
            Assertions.assertNotNull(output);
            Assertions.assertEquals(expectedOutput.getId(), output.getId());
            Assertions.assertEquals(expectedOutput.getName(), output.getName());
            // Verifications
            Mockito.verify(repository, times(1)).findById(id);
            Mockito.verify(mapper, times(1)).asRelationsDto(entity);
        }

        @Test
        @Order(value = 1)
        @DisplayName(value = "2. Given an ID without associated entity in database -> Call to findById() method and throw a NotFoundException")
        void Given_IdWithoutAssociatedEntity_When_FindByIdMethodIsCalled_Then_ThrowNotFoundException() {
            // Given
            Long id = 1L;
            Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
            // When
            // Then
            Assertions.assertThrowsExactly(NotFoundException.class, () -> service.findById(id));
            Mockito.verify(repository, times(1)).findById(id);
        }

    }

    @Nested
    @Order(value = 3)
    @DisplayName(value = "[Method]: findByName(Long id)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class FindByNameTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given an entity in database with the given name -> Call to findByName() method and return it")
        void Given_EntityWithGivenName_When_FindByNameMethodIsCalled_Then_ReturnIt() {
            /*
             * Given
             */
            // Name
            String name = "Agumon";
            // Entity
            Digimon entity = new Digimon(1L);
            entity.setName("Agumon");
            // Expected output
            DigimonRelationsDto expectedOutput = DigimonRelationsDto.of(1L, "Agumon");
            // Mocks
            Mockito.when(repository.findByName(name)).thenReturn(Optional.of(entity));
            Mockito.when(mapper.asRelationsDto(entity)).thenReturn(expectedOutput);

            /*
             * When
             */
            DigimonRelationsDto output = service.findByName(name);

            /*
             * Then
             */
            // Assertions
            Assertions.assertNotNull(output);
            Assertions.assertEquals(expectedOutput.getName(), output.getName());
            // Verifications
            Mockito.verify(repository, times(1)).findByName(name);
            Mockito.verify(mapper, times(1)).asRelationsDto(entity);
        }

        @Test
        @Order(value = 1)
        @DisplayName(value = "2. Given an Optional.empty() from repository -> Call to findByName() method and throw a NotFoundException")
        void Given_EmptyOptional_When_FindByNameMethodIsCalled_Then_ThrowNotFoundException() {
            // Given
            String name = "Agumon";
            Mockito.when(repository.findByName(name)).thenReturn(Optional.empty());
            // When
            // Then
            Assertions.assertThrowsExactly(NotFoundException.class, () -> service.findByName(name));
            Mockito.verify(repository, times(1)).findByName(name);
        }

    }

    @Nested
    @Order(value = 4)
    @DisplayName(value = "[Method]: findAllByTypeId(Long id)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class FindAllByTypeIdTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given several entities in database with the given Type ID -> Call to findAllByTypeId() method and return them")
        void Given_SeveralEntitiesWithTheGivenTypeId_When_FindAllByTypeIdMethodIsCalled_Then_ReturnThem() {
            /*
             * Given
             */
            // Type ID
            Long id = 1L;
            // Entities
            Digimon agumon = new Digimon(1L);
            agumon.setType(new Type(1L));
            Digimon greymon = new Digimon(2L);
            agumon.setType(new Type(1L));
            // Expected output
            List<Digimon> expectedOutput = Arrays.asList(agumon, greymon);
            // Mocks
            Mockito.when(repository.findAllByTypeId(id)).thenReturn(expectedOutput);
            Mockito.when(mapper.asDto(any(Digimon.class))).thenReturn(new DigimonDto());

            /*
             * When
             */
            List<DigimonDto> output = service.findAllByTypeId(id);

            /*
             * Then
             */
            // Assertions
            Assertions.assertNotNull(output);
            Assertions.assertEquals(expectedOutput.size(), output.size());
            // Verifications
            Mockito.verify(repository, times(1)).findAllByTypeId(id);
            Mockito.verify(mapper, times(2)).asDto(any());
        }

    }

    @Nested
    @Order(value = 5)
    @DisplayName(value = "[Method]: findAllByTypeName(String name)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class FindAllByTypeNameTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given several entities in database with the given Type name -> Call to findAllByTypeName() method and return them")
        void Given_SeveralEntitiesWithTheGivenTypeName_When_FindAllByTypeNameMethodIsCalled_Then_ReturnThem() {
            /*
             * Given
             */
            // Type name
            String name = "Data";
            // Entities
            Digimon agumon = new Digimon(1L);
            agumon.setType(new Type(1L));
            Digimon greymon = new Digimon(2L);
            agumon.setType(new Type(1L));
            // Expected output
            List<Digimon> expectedOutput = Arrays.asList(agumon, greymon);
            // Mocks
            Mockito.when(repository.findAllByTypeName(name)).thenReturn(expectedOutput);
            Mockito.when(mapper.asDto(any(Digimon.class))).thenReturn(new DigimonDto());

            /*
             * When
             */
            List<DigimonDto> output = service.findAllByTypeName(name);

            /*
             * Then
             */
            // Assertions
            Assertions.assertNotNull(output);
            Assertions.assertEquals(expectedOutput.size(), output.size());
            // Verifications
            Mockito.verify(repository, times(1)).findAllByTypeName(name);
            Mockito.verify(mapper, times(2)).asDto(any());
        }

    }

    @Nested
    @Order(value = 6)
    @DisplayName(value = "[Method]: findAllByLevelId(Long id)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class FindAllByLevelIdTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given several entities in database with the given Level ID -> Call to findAllByLevelId() method and return them")
        void Given_SeveralEntitiesWithTheGivenLevelId_When_FindAllByLevelIdMethodIsCalled_Then_ReturnThem() {
            /*
             * Given
             */
            // Level ID
            Long id = 1L;
            // Entities
            Digimon agumon = new Digimon(1L);
            agumon.setLevel(new Level(1L));
            Digimon gabumon = new Digimon(2L);
            gabumon.setLevel(new Level(1L));
            // Expected output
            List<Digimon> expectedOutput = Arrays.asList(agumon, gabumon);
            // Mocks
            Mockito.when(repository.findAllByLevelId(id)).thenReturn(expectedOutput);
            Mockito.when(mapper.asDto(any(Digimon.class))).thenReturn(new DigimonDto());

            /*
             * When
             */
            List<DigimonDto> output = service.findAllByLevelId(id);

            /*
             * Then
             */
            // Assertions
            Assertions.assertNotNull(output);
            Assertions.assertEquals(expectedOutput.size(), output.size());
            // Verifications
            Mockito.verify(repository, times(1)).findAllByLevelId(id);
            Mockito.verify(mapper, times(2)).asDto(any());
        }

    }

    @Nested
    @Order(value = 7)
    @DisplayName(value = "[Method]: findAllByLevelName(String name)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class FindAllByLevelNameTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given several entities in database with the given Level name -> Call to findAllByLevelName() method and return them")
        void Given_SeveralEntitiesWithTheGivenLevelName_When_FindAllByLevelNameMethodIsCalled_Then_ReturnThem() {
            /*
             * Given
             */
            // Level name
            String name = "Rookie";
            // Entities
            Digimon agumon = new Digimon(1L);
            agumon.setLevel(new Level(1L));
            Digimon gabumon = new Digimon(2L);
            gabumon.setLevel(new Level(1L));
            // Expected output
            List<Digimon> expectedOutput = Arrays.asList(agumon, gabumon);
            // Mocks
            Mockito.when(repository.findAllByLevelName(name)).thenReturn(expectedOutput);
            Mockito.when(mapper.asDto(any(Digimon.class))).thenReturn(new DigimonDto());

            /*
             * When
             */
            List<DigimonDto> output = service.findAllByLevelName(name);

            /*
             * Then
             */
            // Assertions
            Assertions.assertNotNull(output);
            Assertions.assertEquals(expectedOutput.size(), output.size());
            // Verifications
            Mockito.verify(repository, times(1)).findAllByLevelName(name);
            Mockito.verify(mapper, times(2)).asDto(any());
        }

    }

    @Nested
    @Order(value = 8)
    @DisplayName(value = "[Method]: update(Long id, DigimonPutDto input)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class UpdateTests {

        @Test
        @Transactional
        @Order(value = 0)
        @DisplayName(value = "1. Given an entity in database with the given ID and valid update data -> Call to update() method and return the updated data")
        void Given_EntityWithTheGivenIdAndValidUpdateData_When_UpdateMethodIsCalled_Then_ReturnUpdatedData() {
            /*
             * Given
             */
            // ID
            Long id = 1L;
            // Input
            DigimonPatchDto input = DigimonPatchDto.of(
                    JsonNullable.of("Greymon")
            );
            // Entity
            Digimon entity = new Digimon(1L);
            entity.setName("Agumon");
            // Entity output
            Digimon entityOutput = new Digimon(1L);
            entityOutput.setName("Greymon");
            // Expected output
            DigimonRelationsDto expectedOutput = DigimonRelationsDto.of(1L, "Greymon");
            // Mocks
            Mockito.when(repository.findById(id)).thenReturn(Optional.of(entity));
            Mockito.when(mapper.asEntity(entity, input)).thenReturn(entityOutput);
            Mockito.when(repository.save(entityOutput)).thenReturn(entityOutput);
            Mockito.when(mapper.asRelationsDto(entityOutput)).thenReturn(expectedOutput);

            /*
             * When
             */
            DigimonRelationsDto output = service.update(id, input);

            /*
             * Then
             */
            // Assertions
            Assertions.assertNotNull(output);
            Assertions.assertEquals(expectedOutput.getId(), output.getId());
            Assertions.assertEquals(expectedOutput.getName(), output.getName());
            // Verifications
            Mockito.verify(repository, times(1)).findById(id);
            Mockito.verify(repository, times(1)).save(entityOutput);
            Mockito.verify(mapper, times(1)).asEntity(entity, input);
            Mockito.verify(mapper, times(1)).asRelationsDto(entityOutput);
        }

        @Test
        @Transactional
        @Order(value = 1)
        @DisplayName(value = "2. Given a valid entity in database with the given ID and invalid update data -> Call to update() method and return the data not updated")
        void Given_EntityWithGivenIdAndInvalidUpdateData_When_UpdateMethodIsCalled_Then_ReturnDataNotUpdated() {
            /*
             * Given
             */
            // ID
            Long id = 1L;
            // Entity
            Digimon entity = new Digimon(1L);
            entity.setName("Agumon");
            // Expected output
            DigimonRelationsDto expectedOutput = new DigimonRelationsDto(1L, "Agumon");
            // Mocks
            Mockito.when(repository.findById(id)).thenReturn(Optional.of(entity));
            Mockito.when(mapper.asEntity(entity, null)).thenReturn(entity);
            Mockito.when(repository.save(entity)).thenReturn(entity);
            Mockito.when(mapper.asRelationsDto(entity)).thenReturn(expectedOutput);

            /*
             * When
             */
            DigimonRelationsDto output = service.update(id, null);

            /*
             * Then
             */
            // Assertions
            Assertions.assertNotNull(output);
            Assertions.assertEquals(expectedOutput.getId(), output.getId());
            Assertions.assertEquals(expectedOutput.getName(), output.getName());
            // Verifications
            Mockito.verify(repository, times(1)).findById(id);
            Mockito.verify(repository, times(1)).save(entity);
            Mockito.verify(mapper, times(1)).asEntity(entity, null);
            Mockito.verify(mapper, times(1)).asRelationsDto(entity);
        }

        @Test
        @Order(value = 2)
        @DisplayName(value = "3. Given an ID without associated entity in database -> Call to update() method and throw a NotFoundException")
        void Given_IdWithoutAssociatedEntity_When_UpdateMethodIsCalled_Then_ThrowNotFoundException() {
            // Given
            Long id = 1L;
            Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
            // When
            // Then
            Assertions.assertThrowsExactly(NotFoundException.class, () -> service.update(id, any()));
            Mockito.verify(repository, times(1)).findById(id);
        }

    }

    @Nested
    @Order(value = 9)
    @DisplayName(value = "[Method]: delete(Long id)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class DeleteTests {

        @Test
        @Transactional
        @Order(value = 0)
        @DisplayName(value = "1. Given a valid entity in database with the given ID -> Call to delete method and remove it")
        void Given_ValidEntityWithGivenId_When_DeleteMethodIsCalled_Then_RemoveIt() {
            // Given
            Long id = 1L;
            Mockito
                    .when(repository.findById(id))
                    .thenReturn(Optional.of(new Digimon(1L)));
            // When
            service.delete(id);
            // Then
            Mockito.verify(repository, times(1)).findById(id);
            Mockito.verify(repository, times(1)).deleteById(id);
        }

        @Test
        @Order(value = 1)
        @DisplayName(value = "2. Given an ID without associated entity in database -> Call to delete() method and throw a NotFoundException")
        void Given_IdWithoutAssociatedEntity_When_DeleteMethodIsCalled_Then_ThrowNotFoundException() {
            // Given
            Long id = 1L;
            Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
            // When
            // Then
            Assertions.assertThrowsExactly(NotFoundException.class, () -> service.delete(id));
            Mockito.verify(repository, times(1)).findById(id);
            Mockito.verify(repository, times(0)).deleteById(id);
        }

    }

}