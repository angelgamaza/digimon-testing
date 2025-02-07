package com.gamaza.examples.digimon.service;

import com.gamaza.examples.digimon.dto.request.type.TypePatchDto;
import com.gamaza.examples.digimon.dto.request.type.TypePostDto;
import com.gamaza.examples.digimon.dto.response.type.TypeDto;
import com.gamaza.examples.digimon.dto.response.type.TypeRelationsDto;
import com.gamaza.examples.digimon.entity.Type;
import com.gamaza.examples.digimon.exception.AlreadyExistsException;
import com.gamaza.examples.digimon.exception.GenericRuntimeException;
import com.gamaza.examples.digimon.exception.NotFoundException;
import com.gamaza.examples.digimon.mapper.TypeMapper;
import com.gamaza.examples.digimon.repository.TypeRepository;
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

import static com.gamaza.examples.digimon.config.testcontainers.TestConstants.TYPE_TAG;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.springframework.test.context.bean.override.mockito.MockReset.AFTER;

/**
 * Test class for {@link TypeService}
 */
@Tag(value = TYPE_TAG)
@DisplayName(value = "[Service]: Type")
class TypeServiceTest extends BaseServiceTest {

    @MockitoBean(enforceOverride = true, reset = AFTER)
    private TypeRepository repository;

    @MockitoBean(enforceOverride = true, reset = AFTER)
    private TypeMapper mapper;

    @Autowired
    private TypeService service;

    @Nested
    @Order(value = 0)
    @DisplayName(value = "[Method]: save(TypePostDto input)")
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
            TypePostDto input = TypePostDto.of("Virus");
            // Mapped input
            Type mappedInput = new Type();
            mappedInput.setName("Virus");
            // Saved input
            Type savedInput = new Type(1L);
            savedInput.setName("Virus");
            // Expected output
            TypeDto expectedOutput = TypeDto.of(1L, "Virus");
            // Mocks
            Mockito.when(mapper.asEntity(input)).thenReturn(mappedInput);
            Mockito.when(repository.save(mappedInput)).thenReturn(savedInput);
            Mockito.when(mapper.asDto(savedInput)).thenReturn(expectedOutput);

            /*
             * When
             */
            TypeDto output = service.save(input);

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
            TypePostDto input = TypePostDto.of("Virus");
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
            Type virus = new Type(1L);
            Type vaccine = new Type(2L);
            List<Type> expectedOutput = Arrays.asList(virus, vaccine);
            // Mocks
            Mockito.when(repository.findAll()).thenReturn(expectedOutput);
            Mockito.when(mapper.asDto(any(Type.class))).thenReturn(new TypeDto());

            /*
             * When
             */
            List<TypeDto> output = service.findAll();

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
            Type entity = new Type(1L);
            entity.setName("Virus");
            // Expected output
            TypeRelationsDto expectedOutput = TypeRelationsDto.of(1L, "Virus");
            // Mocks
            Mockito.when(repository.findById(id)).thenReturn(Optional.of(entity));
            Mockito.when(mapper.asRelationsDto(entity)).thenReturn(expectedOutput);

            /*
             * When
             */
            TypeRelationsDto output = service.findById(id);

            /*
             * Then
             */
            // Assertions
            Assertions.assertNotNull(output);
            Assertions.assertEquals(expectedOutput.getId(), output.getId());
            Assertions.assertEquals(expectedOutput.getName(), output.getName());
            Assertions.assertEquals(expectedOutput.getDigimons().size(), output.getDigimons().size());
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
            String name = "Virus";
            // Entity
            Type entity = new Type(1L);
            entity.setName("Virus");
            // Expected output
            TypeRelationsDto expectedOutput = TypeRelationsDto.of(1L, "Virus");
            // Mocks
            Mockito.when(repository.findByName(name)).thenReturn(Optional.of(entity));
            Mockito.when(mapper.asRelationsDto(entity)).thenReturn(expectedOutput);

            /*
             * When
             */
            TypeRelationsDto output = service.findByName(name);

            /*
             * Then
             */
            // Assertions
            Assertions.assertNotNull(output);
            Assertions.assertEquals(expectedOutput.getName(), output.getName());
            Assertions.assertEquals(expectedOutput.getDigimons().size(), output.getDigimons().size());
            // Verifications
            Mockito.verify(repository, times(1)).findByName(name);
            Mockito.verify(mapper, times(1)).asRelationsDto(entity);
        }

        @Test
        @Order(value = 1)
        @DisplayName(value = "2. Given an Optional.empty() from repository -> Call to findByName() method and throw a NotFoundException")
        void Given_EmptyOptional_When_FindByNameMethodIsCalled_Then_ThrowNotFoundException() {
            // Given
            String name = "Virus";
            Mockito.when(repository.findByName(name)).thenReturn(Optional.empty());
            // When
            // Then
            Assertions.assertThrowsExactly(NotFoundException.class, () -> service.findByName(name));
            Mockito.verify(repository, times(1)).findByName(name);
        }

    }

    @Nested
    @Order(value = 4)
    @DisplayName(value = "[Method]: update(Long id, TypePutDto input)")
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
            TypePatchDto input = TypePatchDto.of(
                    JsonNullable.of("Vaccine")
            );
            // Entity
            Type entity = new Type(1L);
            entity.setName("Virus");
            // Entity output
            Type entityOutput = new Type(1L);
            entityOutput.setName("Vaccine");
            // Expected output
            TypeRelationsDto expectedOutput = TypeRelationsDto.of(1L, "Vaccine");
            // Mocks
            Mockito.when(repository.findById(id)).thenReturn(Optional.of(entity));
            Mockito.when(mapper.asEntity(entity, input)).thenReturn(entityOutput);
            Mockito.when(repository.save(entityOutput)).thenReturn(entityOutput);
            Mockito.when(mapper.asRelationsDto(entityOutput)).thenReturn(expectedOutput);

            /*
             * When
             */
            TypeRelationsDto output = service.update(id, input);

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
            Type entity = new Type(1L);
            entity.setName("Virus");
            // Expected output
            TypeRelationsDto expectedOutput = TypeRelationsDto.of(1L, "Virus");
            // Mocks
            Mockito.when(repository.findById(id)).thenReturn(Optional.of(entity));
            Mockito.when(mapper.asEntity(entity, null)).thenReturn(entity);
            Mockito.when(repository.save(entity)).thenReturn(entity);
            Mockito.when(mapper.asRelationsDto(entity)).thenReturn(expectedOutput);

            /*
             * When
             */
            TypeRelationsDto output = service.update(id, null);

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
    @Order(value = 5)
    @DisplayName(value = "[Method]: delete(Long id)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class DeleteTests {

        @Test
        @Transactional
        @Order(value = 0)
        @DisplayName(value = "1. Given a valid entity in database with the given ID -> Call to delete() method and remove it")
        void Given_ValidEntityWithGivenId_When_DeleteMethodIsCalled_Then_RemoveIt() {
            // Given
            Long id = 1L;
            Mockito
                    .when(repository.findById(id))
                    .thenReturn(Optional.of(new Type(1L)));
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