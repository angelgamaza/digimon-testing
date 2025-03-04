package com.gamaza.examples.springtest.mapper;

import com.gamaza.examples.springtest.dto.request.type.TypePatchDto;
import com.gamaza.examples.springtest.dto.request.type.TypePostDto;
import com.gamaza.examples.springtest.dto.response.type.TypeDto;
import com.gamaza.examples.springtest.dto.response.type.TypeRelationsDto;
import com.gamaza.examples.springtest.entity.Digimon;
import com.gamaza.examples.springtest.entity.Type;
import com.gamaza.examples.springtest.mapper.base.BaseMapperTest;
import org.junit.jupiter.api.*;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static com.gamaza.examples.springtest.config.testcontainers.TestConstants.TYPE_TAG;

/**
 * Test class for {@link TypeMapper}
 */
@Tag(value = TYPE_TAG)
@DisplayName(value = "[Mapper]: Type")
class TypeMapperTest extends BaseMapperTest {

    @Autowired
    private TypeMapper mapper;

    @Nested
    @Order(value = 0)
    @DisplayName(value = "[Method]: asEntity(TypePostDto source)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class AsEntityTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given valid input data -> Call to mapper and return an entity")
        void Given_ValidInputData_When_AsEntityMethodIsCalled_Then_EntityIsReturned() {
            // Given
            TypePostDto input = TypePostDto.of("Virus");
            // When
            Type output = mapper.asEntity(input);
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
            Type input = mapper.asEntity(null);
            // Then
            Assertions.assertNull(input);
        }

    }

    @Nested
    @Order(value = 1)
    @DisplayName(value = "[Method]: asEntity(Type target, TypePutDto source)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class AsEntityUpdateTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given a target entity and valid update data -> Call to mapper and return the updated entity")
        void Given_TargetEntityAndValidUpdateData_When_AsEntityMethodIsCalled_Then_UpdatedEntityIsReturned() {
            // Given
            Type target = new Type(1L);
            target.setName("Virus");
            TypePatchDto source = TypePatchDto.of(
                    JsonNullable.of("Data")
            );
            // When
            Type output = mapper.asEntity(target, source);
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
            Type target = new Type(1L);
            target.setName("Virus");
            TypePatchDto source = new TypePatchDto();
            // When
            Type output = mapper.asEntity(target, source);
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
            Type target = new Type(1L);
            target.setName("Virus");
            // When
            Type output = mapper.asEntity(target, null);
            // Then
            Assertions.assertNotNull(output);
            Assertions.assertEquals(target.getId(), output.getId());
            Assertions.assertEquals(target.getName(), output.getName());
        }

    }

    @Nested
    @Order(value = 2)
    @DisplayName(value = "[Method]: asDto(Type source)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class AsDtoTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given a valid entity -> Call to mapper and return a DTO")
        void Given_ValidEntity_When_AsDtoMethodIsCalled_Then_DtoIsReturned() {
            // Given
            Type input = new Type(1L);
            input.setName("Virus");
            LocalDateTime now = LocalDateTime.now();
            input.setCreatedDate(now);
            input.setModifiedDate(now);
            // When
            TypeDto output = mapper.asDto(input);
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
            TypeDto output = mapper.asDto(null);
            // Then
            Assertions.assertNull(output);
        }

    }

    @Nested
    @Order(value = 3)
    @DisplayName(value = "[Method]: asRelationsDto(Type source)")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class AsRelationsDtoTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given a valid entity -> Call to mapper and return a Relations DTO")
        void Given_ValidEntity_When_AsRelationsDtoMethodIsCalled_Then_RelationsDtoIsReturned() {
            // Given
            Type input = new Type(1L);
            input.setName("Data");
            Digimon relationship = new Digimon(1L);
            relationship.setName("Agumon");
            input.setDigimons(Collections.singleton(relationship));
            // When
            TypeRelationsDto output = mapper.asRelationsDto(input);
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
            Type input = new Type(1L);
            input.setName("Data");
            Digimon relationship = new Digimon(1L);
            relationship.setName("Agumon");
            input.setDigimons(new HashSet<>(Arrays.asList(relationship, null)));
            // When
            TypeRelationsDto output = mapper.asRelationsDto(input);
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
            Type input = new Type(1L);
            input.setName("Data");
            input.setDigimons(null);
            // When
            TypeRelationsDto output = mapper.asRelationsDto(input);
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
            TypeRelationsDto output = mapper.asRelationsDto(null);
            // Then
            Assertions.assertNull(output);
        }

    }

}