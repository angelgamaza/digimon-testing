package com.gamaza.examples.digimon.mapper;

import com.gamaza.examples.digimon.dto.request.level.LevelPatchDto;
import com.gamaza.examples.digimon.dto.request.level.LevelPostDto;
import com.gamaza.examples.digimon.dto.response.level.LevelDto;
import com.gamaza.examples.digimon.dto.response.level.LevelRelationsDto;
import com.gamaza.examples.digimon.entity.Digimon;
import com.gamaza.examples.digimon.entity.Level;
import com.gamaza.examples.digimon.mapper.base.BaseMapperTest;
import org.junit.jupiter.api.*;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static com.gamaza.examples.digimon.config.testcontainers.TestConstants.LEVEL_TAG;

/**
 * Test class for {@link LevelMapper}
 */
@Tag(value = LEVEL_TAG)
@DisplayName(value = "[Mapper]: Level")
class LevelMapperTest extends BaseMapperTest {

    @Autowired
    private LevelMapper mapper;

    @Nested
    @Order(value = 0)
    @DisplayName(value = "[Method]: asEntity(LevelPostDto source)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class AsEntityTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given valid input data -> Call to mapper and return an entity")
        void Given_ValidInputData_When_AsEntityMethodIsCalled_Then_EntityIsReturned() {
            // Given
            LevelPostDto input = LevelPostDto.of("Baby");
            // When
            Level output = mapper.asEntity(input);
            // Then
            Assertions.assertNotNull(output);
            Assertions.assertEquals(input.getName(), output.getName());
        }

        @Test
        @Order(value = 1)
        @DisplayName(value = "2. Given null input data -> Call to mapper and return null")
        void Given_NullInputData_When_AsEntityMethodIsCalled_Then_NullIsReturned() {
            // Given
            // When
            Level input = mapper.asEntity(null);
            // Then
            Assertions.assertNull(input);
        }

    }

    @Nested
    @Order(value = 1)
    @DisplayName(value = "[Method]: asEntity(Level target, LevelPutDto source)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class AsEntityUpdateTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given a target entity and valid update data -> Call to mapper and return the updated entity")
        void Given_TargetEntityAndValidUpdateData_When_AsEntityMethodIsCalled_Then_UpdatedEntityIsReturned() {
            // Given
            Level target = new Level(1L);
            target.setName("Baby");
            LevelPatchDto source = LevelPatchDto.of(
                    JsonNullable.of("In-Training")
            );
            // When
            Level output = mapper.asEntity(target, source);
            // Then
            Assertions.assertNotNull(output);
            Assertions.assertEquals(target.getId(), output.getId());
            Assertions.assertEquals(source.getName().get(), output.getName());
        }

        @Test
        @Order(value = 1)
        @DisplayName(value = "2. Given a target entity and update data with null values -> Call to mapper and return the original entity")
        void Given_TargetEntityAndUpdateDataWithNullValues_When_AsEntityMethodIsCalled_Then_OriginalEntityIsReturned() {
            // Given
            Level target = new Level(1L);
            target.setName("Rookie");
            LevelPatchDto source = new LevelPatchDto();
            // When
            Level output = mapper.asEntity(target, source);
            // Then
            Assertions.assertNotNull(output);
            Assertions.assertEquals(target.getId(), output.getId());
            Assertions.assertEquals(target.getName(), output.getName());
        }

        @Test
        @Order(value = 2)
        @DisplayName(value = "3. Given a target entity and null update data -> Call to mapper and return the original entity")
        void Given_TargetEntityAndNullUpdateData_When_AsEntityMethodIsCalled_Then_OriginalEntityIsReturned() {
            // Given
            Level target = new Level(1L);
            target.setName("Rookie");
            // When
            Level output = mapper.asEntity(target, null);
            // Then
            Assertions.assertNotNull(output);
            Assertions.assertEquals(target.getId(), output.getId());
            Assertions.assertEquals(target.getName(), output.getName());
        }

    }

    @Nested
    @Order(value = 2)
    @DisplayName(value = "[Method]: asDto(Level source)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class AsDtoTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given a valid entity -> Call to mapper and return a DTO")
        void Given_ValidEntity_When_AsDtoMethodIsCalled_Then_DtoIsReturned() {
            // Given
            Level input = new Level(1L);
            input.setName("Ultimate");
            LocalDateTime now = LocalDateTime.now();
            input.setCreatedDate(now);
            input.setModifiedDate(now);
            // When
            LevelDto output = mapper.asDto(input);
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
            LevelDto output = mapper.asDto(null);
            // Then
            Assertions.assertNull(output);
        }

    }

    @Nested
    @Order(value = 3)
    @DisplayName(value = "[Method]: asRelationsDto(Level source)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class AsRelationsDtoTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given a valid entity -> Call to mapper and return a Relations DTO")
        void Given_ValidEntity_When_AsRelationsDtoMethodIsCalled_Then_RelationsDtoIsReturned() {
            // Given
            Level input = new Level(1L);
            input.setName("Rookie");
            Digimon relationship = new Digimon(1L);
            relationship.setName("Agumon");
            input.setDigimons(Collections.singleton(relationship));
            // When
            LevelRelationsDto output = mapper.asRelationsDto(input);
            // Then
            Assertions.assertNotNull(output);
            Assertions.assertEquals(input.getId(), output.getId());
            Assertions.assertEquals(input.getName(), output.getName());
            Assertions.assertEquals(1, output.getDigimons().size());
        }

        @Test
        @Order(value = 1)
        @DisplayName(value = "2. Given a valid entity with null relationships -> Call to mapper and return a Relations DTO counting the null relationships")
        void Given_ValidEntityWithNullRelationships_When_AsRelationsDtoMethodIsCalled_Then_RelationsDtoIsReturnedCountingTheNullRelationships() {
            // Given
            Level input = new Level(1L);
            input.setName("Rookie");
            Digimon relationship = new Digimon(1L);
            relationship.setName("Agumon");
            input.setDigimons(new HashSet<>(Arrays.asList(relationship, null)));
            // When
            LevelRelationsDto output = mapper.asRelationsDto(input);
            // Then
            Assertions.assertNotNull(output);
            Assertions.assertEquals(input.getId(), output.getId());
            Assertions.assertEquals(input.getName(), output.getName());
            Assertions.assertEquals(2, output.getDigimons().size());
        }

        @Test
        @Order(value = 2)
        @DisplayName(value = "3. Given a valid entity without relationships -> Call to mapper and return a Relations DTO")
        void Given_ValidEntityWithoutRelationships_When_AsRelationsDtoMethodIsCalled_Then_RelationsDtoIsReturned() {
            // Given
            Level input = new Level(1L);
            input.setName("Rookie");
            input.setDigimons(null);
            // When
            LevelRelationsDto output = mapper.asRelationsDto(input);
            // Then
            Assertions.assertNotNull(output);
            Assertions.assertEquals(input.getId(), output.getId());
            Assertions.assertEquals(input.getName(), output.getName());
            Assertions.assertNull(output.getDigimons());
        }

        @Test
        @Order(value = 3)
        @DisplayName(value = "4. Given a null entity -> Call to mapper and return null")
        void Given_NullEntity_When_AsRelationsDtoMethodIsCalled_Then_NullIsReturned() {
            // Given
            // When
            LevelRelationsDto output = mapper.asRelationsDto(null);
            // Then
            Assertions.assertNull(output);
        }

    }

}