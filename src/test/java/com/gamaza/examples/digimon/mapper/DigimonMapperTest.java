package com.gamaza.examples.digimon.mapper;

import com.gamaza.examples.digimon.dto.request.digimon.DigimonPatchDto;
import com.gamaza.examples.digimon.dto.request.digimon.DigimonPostDto;
import com.gamaza.examples.digimon.dto.response.digimon.DigimonDto;
import com.gamaza.examples.digimon.dto.response.digimon.DigimonRelationsDto;
import com.gamaza.examples.digimon.entity.Digimon;
import com.gamaza.examples.digimon.entity.Level;
import com.gamaza.examples.digimon.entity.Type;
import com.gamaza.examples.digimon.mapper.base.BaseMapperTest;
import org.junit.jupiter.api.*;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static com.gamaza.examples.digimon.config.testcontainers.TestConstants.DIGIMON_TAG;

/**
 * Test class for {@link DigimonMapper}
 */
@Tag(value = DIGIMON_TAG)
@DisplayName(value = "[Mapper]: Digimon")
class DigimonMapperTest extends BaseMapperTest {

    @Autowired
    private DigimonMapper mapper;

    @Nested
    @Order(value = 0)
    @DisplayName(value = "[Method]: asEntity(DigimonPostDto source)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class AsEntityTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given valid input data -> Call to mapper and return an entity")
        void Given_ValidInputData_When_AsEntityMethodIsCalled_Then_EntityIsReturned() {
            // Given
            DigimonPostDto input = DigimonPostDto.of("Agumon", 1L, 3L);
            // When
            Digimon output = mapper.asEntity(input);
            // Then
            Assertions.assertNotNull(output);
            Assertions.assertEquals(input.getName(), output.getName());
            Assertions.assertEquals(input.getTypeId(), output.getType().getId());
            Assertions.assertEquals(input.getLevelId(), output.getLevel().getId());
        }

        @Test
        @Order(value = 1)
        @DisplayName(value = "2. Given null input data -> Call to mapper and return null")
        void Given_NullInputData_When_AsEntityMethodIsCalled_Then_NullIsReturned() {
            // Given
            // When
            Digimon input = mapper.asEntity(null);
            // Then
            Assertions.assertNull(input);
        }

    }

    @Nested
    @Order(value = 1)
    @DisplayName(value = "[Method]: asEntity(Digimon target, DigimonPutDto source)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class AsEntityUpdateTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given a target entity and valid update data -> Call to mapper and return the updated entity (Check 1)")
        void Given_TargetEntityAndValidUpdateData_When_AsEntityMethodIsCalled_Then_UpdatedEntityIsReturned1() {
            // Given
            Digimon target = new Digimon(1L);
            target.setName("Agumon");
            target.setType(new Type(1L));
            target.setLevel(new Level(3L));
            DigimonPatchDto source = DigimonPatchDto.of(
                    JsonNullable.of("Greymon"),
                    JsonNullable.undefined(),
                    JsonNullable.of(4L)
            );
            // When
            Digimon output = mapper.asEntity(target, source);
            // Then
            Assertions.assertNotNull(output);
            Assertions.assertEquals(target.getId(), output.getId());
            Assertions.assertEquals(source.getName().get(), output.getName());
            Assertions.assertEquals(target.getType().getId(), output.getType().getId());
            Assertions.assertEquals(source.getLevelId().get(), output.getLevel().getId());
        }

        @Test
        @Order(value = 1)
        @DisplayName(value = "2. Given a target entity and valid update data -> Call to mapper and return the updated entity (Check 2)")
        void Given_TargetEntityAndValidUpdateData_When_AsEntityMethodIsCalled_Then_UpdatedEntityIsReturned2() {
            // Given
            Digimon target = new Digimon(1L);
            target.setName("Agumon");
            target.setType(new Type(1L));
            target.setLevel(new Level(3L));
            DigimonPatchDto source = DigimonPatchDto.of(
                    JsonNullable.of("Greymon"),
                    JsonNullable.of(1L),
                    JsonNullable.of(4L)
            );
            // When
            Digimon output = mapper.asEntity(target, source);
            // Then
            Assertions.assertNotNull(output);
            Assertions.assertEquals(target.getId(), output.getId());
            Assertions.assertEquals(source.getName().get(), output.getName());
            Assertions.assertEquals(source.getTypeId().get(), output.getType().getId());
            Assertions.assertEquals(source.getLevelId().get(), output.getLevel().getId());
        }

        @Test
        @Order(value = 2)
        @DisplayName(value = "3. Given a target entity and valid update data -> Call to mapper and return the updated entity (Check 3)")
        void Given_TargetEntityAndValidUpdateData_When_AsEntityMethodIsCalled_Then_UpdatedEntityIsReturned3() {
            // Given
            Digimon target = new Digimon(1L);
            target.setName("Agumon");
            target.setType(new Type(1L));
            target.setLevel(new Level(3L));
            DigimonPatchDto source = DigimonPatchDto.of(
                    JsonNullable.of("Greymon"),
                    null,
                    null
            );
            // When
            Digimon output = mapper.asEntity(target, source);
            // Then
            Assertions.assertNotNull(output);
            Assertions.assertEquals(target.getId(), output.getId());
            Assertions.assertEquals(source.getName().get(), output.getName());
            Assertions.assertEquals(target.getType().getId(), output.getType().getId());
            Assertions.assertEquals(target.getLevel().getId(), output.getLevel().getId());
        }

        @Test
        @Order(value = 3)
        @DisplayName(value = "4. Given a target entity and update data with null values -> Call to mapper and return the original entity")
        void Given_TargetEntityAndUpdateDataWithNullValues_When_AsEntityMethodIsCalled_Then_OriginalEntityIsReturned() {
            // Given
            Digimon target = new Digimon(1L);
            target.setName("Rookie");
            DigimonPatchDto source = new DigimonPatchDto();
            // When
            Digimon output = mapper.asEntity(target, source);
            // Then
            Assertions.assertNotNull(output);
            Assertions.assertEquals(target.getId(), output.getId());
            Assertions.assertEquals(target.getName(), output.getName());
        }

        @Test
        @Order(value = 4)
        @DisplayName(value = "5. Given a target entity and null update data -> Call to mapper and return the original entity")
        void Given_TargetEntityAndNullUpdateData_When_AsEntityMethodIsCalled_Then_OriginalEntityIsReturned() {
            // Given
            Digimon target = new Digimon(1L);
            target.setName("Rookie");
            // When
            Digimon output = mapper.asEntity(target, null);
            // Then
            Assertions.assertNotNull(output);
            Assertions.assertEquals(target.getId(), output.getId());
            Assertions.assertEquals(target.getName(), output.getName());
        }

    }

    @Nested
    @Order(value = 2)
    @DisplayName(value = "[Method]: asDto(Digimon source)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class AsDtoTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given a valid entity -> Call to mapper and return a DTO")
        void Given_ValidEntity_When_AsDtoMethodIsCalled_Then_DtoIsReturned() {
            // Given
            Digimon input = new Digimon(1L);
            input.setName("Agumon");
            LocalDateTime now = LocalDateTime.now();
            input.setCreatedDate(now);
            input.setModifiedDate(now);
            // When
            DigimonDto output = mapper.asDto(input);
            // Then
            Assertions.assertNotNull(output);
            Assertions.assertEquals(input.getId(), output.getId());
            Assertions.assertEquals(input.getName(), output.getName());
            Assertions.assertEquals(input.getCreatedDate(), output.getCreatedDate());
            Assertions.assertEquals(input.getModifiedDate(), output.getModifiedDate());
        }

        @Test
        @Order(value = 1)
        @DisplayName(value = "2. Given a null entity -> Call to mapper and return null")
        void Given_NullEntity_When_AsDtoMethodIsCalled_Then_NullIsReturned() {
            // Given
            // When
            DigimonDto output = mapper.asDto(null);
            // Then
            Assertions.assertNull(output);
        }

    }

    @Nested
    @Order(value = 3)
    @DisplayName(value = "[Method]: asRelationsDto(Digimon source)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class AsRelationsDtoTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given a valid entity -> Call to mapper and return a Relations DTO")
        void Given_ValidEntity_When_AsRelationsDtoMethodIsCalled_Then_RelationsDtoIsReturned() {
            // Given
            Digimon input = new Digimon(1L);
            input.setName("Agumon");
            input.setType(new Type(1L));
            input.setLevel(new Level(1L));
            // When
            DigimonRelationsDto output = mapper.asRelationsDto(input);
            // Then
            Assertions.assertNotNull(output);
            Assertions.assertEquals(input.getId(), output.getId());
            Assertions.assertEquals(input.getName(), output.getName());
            Assertions.assertEquals(input.getType().getId(), output.getType().getId());
            Assertions.assertEquals(input.getLevel().getId(), output.getLevel().getId());
        }

        @Test
        @Order(value = 1)
        @DisplayName(value = "2. Given a valid entity with null relationships -> Call to mapper and return a Relations DTO")
        void Given_ValidEntityWithNullRelationships_When_AsRelationsDtoMethodIsCalled_Then_RelationsDtoIsReturned() {
            // Given
            Digimon input = new Digimon(1L);
            input.setName("Rookie");
            // When
            DigimonRelationsDto output = mapper.asRelationsDto(input);
            // Then
            Assertions.assertNotNull(output);
            Assertions.assertEquals(input.getId(), output.getId());
            Assertions.assertEquals(input.getName(), output.getName());
            Assertions.assertNull(output.getType());
            Assertions.assertNull(output.getLevel());
        }

        @Test
        @Order(value = 2)
        @DisplayName(value = "4. Given a null entity -> Call to mapper and return null")
        void Given_NullEntity_When_AsRelationsDtoMethodIsCalled_Then_NullIsReturned() {
            // Given
            // When
            DigimonRelationsDto output = mapper.asRelationsDto(null);
            // Then
            Assertions.assertNull(output);
        }

    }

}