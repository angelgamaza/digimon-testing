package com.gamaza.examples.digimon.controller;

import com.gamaza.examples.digimon.controller.base.BaseControllerTest;
import com.gamaza.examples.digimon.dto.response.ProblemDetailDto;
import com.gamaza.examples.digimon.dto.response.type.TypeDto;
import com.gamaza.examples.digimon.dto.response.type.TypeRelationsDto;
import com.gamaza.examples.digimon.exception.AlreadyExistsException;
import com.gamaza.examples.digimon.exception.NotFoundException;
import com.gamaza.examples.digimon.service.TypeService;
import com.gamaza.examples.digimon.service.impl.TypeServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.gamaza.examples.digimon.config.testcontainers.TestConstants.*;
import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.apache.naming.ResourceRef.DESCRIPTION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON;
import static org.springframework.test.context.bean.override.mockito.MockReset.AFTER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for {@link TypeController}
 */
@Tag(value = TYPE_TAG)
@DisplayName(value = "[Controller]: Type")
@ContextConfiguration(classes = {
        TypeServiceImpl.class,
        TypeController.class
})
class TypeControllerTest extends BaseControllerTest {

    @MockitoBean(enforceOverride = true, reset = AFTER)
    private TypeService service;

    @Nested
    @Order(value = 0)
    @DisplayName(value = "[Method]: POST /type")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class SaveTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given valid input data -> Call to save() method and return it")
        void Given_ValidInputData_When_PostCallIsExecuted_Then_ReturnIt() throws Exception {
            /*
             * Given
             */
            // Expected output
            TypeDto expectedOutput = new TypeDto(1L, "Virus");
            // Mocks
            Mockito.when(service.save(any())).thenReturn(expectedOutput);

            /*
             * When
             */
            // POST Call
            MockHttpServletRequestBuilder post = post(TYPE_BASE_URL)
                    .contentType(APPLICATION_JSON)
                    .content("{\"name\": \"Virus\"}");

            // MVC Mock Execution
            mockMvc
                    .perform(post)
                    .andExpectAll(
                            // Status
                            status().isCreated(),
                            // Content type
                            content().contentType(APPLICATION_JSON),
                            // Headers
                            header().string("Location", "http://localhost:8081/type/1"),
                            // Result paths
                            jsonPath("$.id").value(expectedOutput.getId()),
                            jsonPath("$.name").value(expectedOutput.getName())
                    );

            /*
             * Then
             */
            Mockito.verify(service, times(1)).save(any());
        }

        @Test
        @Order(value = 1)
        @DisplayName(value = "2. Given valid input data but existent in the system -> Call to save() method and return an AlreadyExistsException")
        void Given_ValidInputDataButExistent_When_PostCallIsExecuted_Then_ReturnAlreadyExistsException() throws Exception {
            /*
             * Given
             */
            // Expected output
            ProblemDetailDto expectedOutput = ProblemDetailDto.ProblemDetailDtoBuilder
                    .newInstance(CONFLICT, "The object [Type] with parameters [name=Virus] already exists!")
                    .withType(URI.create("/type"))
                    .withTitle("Resource already exists")
                    .withProperty(DESCRIPTION, "The resource already exists on the server")
                    .build();

            // Mocks
            Mockito.when(service.save(any())).thenThrow(new AlreadyExistsException(TYPE_OBJECT_NAME, "name=Virus"));

            /*
             * When
             */
            // POST Call
            MockHttpServletRequestBuilder post = post(TYPE_BASE_URL)
                    .contentType(APPLICATION_JSON)
                    .content("{\"name\": \"Virus\"}");

            // MVC Mock Execution
            mockMvc
                    .perform(post)
                    .andExpectAll(
                            // Status
                            status().isConflict(),
                            // Content type
                            content().contentType(APPLICATION_PROBLEM_JSON),
                            // Result paths
                            jsonPath("$.title").value(expectedOutput.getTitle()),
                            jsonPath("$.status").value(expectedOutput.getStatus()),
                            jsonPath("$.detail").value(expectedOutput.getDetail()),
                            jsonPath("$.description").value("The resource already exists on the server")
                    );

            /*
             * Then
             */
            Mockito.verify(service, times(1)).save(any());
        }

        @Test
        @Order(value = 2)
        @DisplayName(value = "3. Given a invalid input data -> Call to save() method and return the error")
        void Given_InvalidInputData_When_PostCallIsExecuted_Then_ReturnError() throws Exception {
            /*
             * Given
             */
            // Expected output
            ProblemDetailDto expectedOutput = ProblemDetailDto.ProblemDetailDtoBuilder
                    .newInstance(BAD_REQUEST, EMPTY)
                    .withType(URI.create(TYPE_BASE_URL))
                    .withTitle("Invalid argument received for the controller")
                    .withProperty(DESCRIPTION, "The data arguments contain one or more invalid values")
                    .build();

            /*
             * When
             */
            // POST Call
            MockHttpServletRequestBuilder post = post(TYPE_BASE_URL)
                    .contentType(APPLICATION_JSON)
                    .content("{}");

            // MVC Mock Execution
            mockMvc
                    .perform(post)
                    .andExpectAll(
                            // Status
                            status().isBadRequest(),
                            // Content type
                            content().contentType(APPLICATION_PROBLEM_JSON),
                            // Result paths
                            jsonPath("$.title").value(expectedOutput.getTitle()),
                            jsonPath("$.status").value(expectedOutput.getStatus()),
                            jsonPath("$.detail").isString(),
                            jsonPath("$.detail").isNotEmpty(),
                            jsonPath("$.description").value("The data arguments contain one or more invalid values")
                    );

            /*
             * Then
             */
        }

    }

    @Nested
    @Order(value = 1)
    @DisplayName(value = "[Method]: GET /type")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class FindAllTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given several entities in the system -> Call to findAll() method and return them")
        void Given_SeveralEntitiesInTheSystem_When_GetCallIsExecuted_Then_ReturnThem() throws Exception {
            /*
             * Given
             */
            // Data
            TypeDto type1 = TypeDto.of(1L, "Virus");
            TypeDto type2 = TypeDto.of(2L, "Vaccine");
            // Expected output
            List<TypeDto> expectedOutput = Arrays.asList(type1, type2);
            // Mocks
            Mockito.when(service.findAll()).thenReturn(expectedOutput);

            /*
             * When
             */
            // GET Call
            MockHttpServletRequestBuilder get = get(TYPE_BASE_URL);
            // MVC Mock Execution
            mockMvc
                    .perform(get)
                    .andExpectAll(
                            // Status
                            status().isOk(),
                            // Content type
                            content().contentType(APPLICATION_JSON),
                            // Result paths
                            jsonPath("$.length()").value(expectedOutput.size()),
                            jsonPath("$[0].id").value(type1.getId()),
                            jsonPath("$[0].name").value(type1.getName()),
                            jsonPath("$[1].id").value(type2.getId()),
                            jsonPath("$[1].name").value(type2.getName())
                    );

            /*
             * Then
             */
            Mockito.verify(service, times(1)).findAll();
        }

    }

    @Nested
    @Order(value = 2)
    @DisplayName(value = "[Method]: GET /type/{id}")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class FindByIdTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given an entity with the given ID in database -> Call to findById() method and return it")
        void Given_EntityWithGivenId_When_GetCallIsExecuted_Then_ReturnIt() throws Exception {
            /*
             * Given
             */
            // ID
            Long id = 1L;
            // Expected output
            TypeRelationsDto expectedOutput = TypeRelationsDto.of(1L, "Virus");
            // Mocks
            Mockito.when(service.findById(id)).thenReturn(expectedOutput);

            /*
             * When
             */
            // Call URL
            String url = String.format("%s/%d", TYPE_BASE_URL, id);
            // GET Call
            MockHttpServletRequestBuilder get = get(url);
            // MVC Mock Execution
            mockMvc
                    .perform(get)
                    .andExpectAll(
                            // Status
                            status().isOk(),
                            // Content type
                            content().contentType(APPLICATION_JSON),
                            // Result paths
                            jsonPath("$.id").value(expectedOutput.getId()),
                            jsonPath("$.name").value(expectedOutput.getName())
                    );

            /*
             * Then
             */
            Mockito.verify(service, times(1)).findById(id);
        }

        @Test
        @Order(value = 1)
        @DisplayName(value = "2. Given an ID without associated entity in database -> Call to findById() method and throw a NotFoundException")
        void Given_IdWithoutAssociatedEntity_When_GetCallIsExecuted_Then_ThrowNotFoundException() throws Exception {
            /*
             * Given
             */
            // ID
            Long id = 1L;
            // Expected output
            ProblemDetailDto expectedOutput = ProblemDetailDto.ProblemDetailDtoBuilder
                    .newInstance(NOT_FOUND, "The object [Type] with parameters [id=1] not found!")
                    .withType(URI.create(TYPE_BASE_URL))
                    .withTitle("Resource not found")
                    .withProperty(DESCRIPTION, "The resource can not be found on the server")
                    .build();

            // Mocks
            Mockito.when(service.findById(id)).thenThrow(new NotFoundException(TYPE_OBJECT_NAME, "id=1"));

            /*
             * When
             */
            // Call URL
            String url = String.format("%s/%d", TYPE_BASE_URL, id);
            // GET Call
            MockHttpServletRequestBuilder get = get(url);
            // MVC Mock Execution
            mockMvc
                    .perform(get)
                    .andExpectAll(
                            // Status
                            status().isNotFound(),
                            // Content type
                            content().contentType(APPLICATION_PROBLEM_JSON),
                            // Result paths
                            jsonPath("$.title").value(expectedOutput.getTitle()),
                            jsonPath("$.status").value(expectedOutput.getStatus()),
                            jsonPath("$.detail").value(expectedOutput.getDetail()),
                            jsonPath("$.description").value("The resource can not be found on the server")
                    );

            /*
             * Then
             */
            Mockito.verify(service, times(1)).findById(id);
        }

    }

    @Nested
    @Order(value = 3)
    @DisplayName(value = "[Method]: GET /type/name?name=")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class FindByNameTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given an entity with the given name in database -> Call to findByName() method and return it")
        void Given_EntityWithGivenName_When_GetCallIsExecuted_Then_ReturnIt() throws Exception {
            /*
             * Given
             */
            // ID
            String name = "Virus";
            // Expected output
            TypeRelationsDto expectedOutput = TypeRelationsDto.of(1L, "Virus");
            // Mocks
            Mockito.when(service.findByName(name)).thenReturn(expectedOutput);

            /*
             * When
             */
            // Call URL
            String url = String.format("%s/name", TYPE_BASE_URL);
            // GET Call
            MockHttpServletRequestBuilder get = get(url)
                    .param("name", name);

            // MVC Mock Execution
            mockMvc
                    .perform(get)
                    .andExpectAll(
                            // Status
                            status().isOk(),
                            // Content type
                            content().contentType(APPLICATION_JSON),
                            // Result paths
                            jsonPath("$.id").value(expectedOutput.getId()),
                            jsonPath("$.name").value(expectedOutput.getName())
                    );

            /*
             * Then
             */
            Mockito.verify(service, times(1)).findByName(name);
        }

        @Test
        @Order(value = 1)
        @DisplayName(value = "2. Given a name without associated entity in database -> Call to findByName() method and throw a NotFoundException")
        void Given_NameWithoutAssociatedEntity_When_GetCallIsExecuted_Then_ThrowNotFoundException() throws Exception {
            /*
             * Given
             */
            // ID
            String name = "Injection";
            // Expected output
            ProblemDetailDto expectedOutput = ProblemDetailDto.ProblemDetailDtoBuilder
                    .newInstance(NOT_FOUND, "The object [Type] with parameters [name=Injection] not found!")
                    .withType(URI.create(TYPE_BASE_URL))
                    .withTitle("Resource not found")
                    .withProperty(DESCRIPTION, "The resource can not be found on the server")
                    .build();

            // Mocks
            Mockito.when(service.findByName(name)).thenThrow(new NotFoundException(TYPE_OBJECT_NAME, "name=Injection"));

            /*
             * When
             */
            // Call URL
            String url = String.format("%s/name", TYPE_BASE_URL);
            // GET Call
            MockHttpServletRequestBuilder get = get(url)
                    .param("name", name);

            // MVC Mock Execution
            mockMvc
                    .perform(get)
                    .andExpectAll(
                            // Status
                            status().isNotFound(),
                            // Content type
                            content().contentType(APPLICATION_PROBLEM_JSON),
                            // Result paths
                            jsonPath("$.title").value(expectedOutput.getTitle()),
                            jsonPath("$.status").value(expectedOutput.getStatus()),
                            jsonPath("$.detail").value(expectedOutput.getDetail()),
                            jsonPath("$.description").value("The resource can not be found on the server")
                    );

            /*
             * Then
             */
            Mockito.verify(service, times(1)).findByName(name);
        }

    }

    @Nested
    @Order(value = 4)
    @DisplayName(value = "[Method]: PATCH /type/{id}")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class PatchTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given valid input data and an entity with the given ID in database -> Call to update() method and return the updated data")
        void Given_ValidInputDataAndEntityWithGivenId_When_PatchCallIsExecuted_Then_ReturnUpdatedData() throws Exception {
            /*
             * Given
             */
            // ID
            Long id = 1L;
            // Expected output
            TypeRelationsDto expectedOutput = new TypeRelationsDto(1L, "Vaccine", Collections.emptySet());
            // Mocks
            Mockito.when(service.update(eq(id), any())).thenReturn(expectedOutput);

            /*
             * When
             */
            // Call URL
            String url = String.format("%s/%d", TYPE_BASE_URL, id);
            // POST Call
            MockHttpServletRequestBuilder patch = patch(url)
                    .contentType(APPLICATION_JSON)
                    .content("{\"name\": \"Vaccine\"}");

            // MVC Mock Execution
            mockMvc
                    .perform(patch)
                    .andExpectAll(
                            // Status
                            status().isOk(),
                            // Content type
                            content().contentType(APPLICATION_JSON),
                            // Result paths
                            jsonPath("$.id").value(expectedOutput.getId()),
                            jsonPath("$.name").value(expectedOutput.getName())
                    );

            /*
             * Then
             */
            Mockito.verify(service, times(1)).update(eq(id), any());
        }

        @Test
        @Order(value = 1)
        @DisplayName(value = "2. Given valid input data but an ID without associated entity in database -> Call to update() method and throw a NotFoundException")
        void Given_ValidInputDataButIdNotAssociatedToEntity_When_PatchCallIsExecuted_Then_ThrowNotFoundException() throws Exception {
            /*
             * Given
             */
            // ID
            Long id = 1L;
            // Expected output
            ProblemDetailDto expectedOutput = ProblemDetailDto.ProblemDetailDtoBuilder
                    .newInstance(NOT_FOUND, "The object [Type] with parameters [id=1] not found!")
                    .withType(URI.create(TYPE_BASE_URL))
                    .withTitle("Resource not found")
                    .withProperty(DESCRIPTION, "The resource can not be found on the server")
                    .build();

            // Mocks
            Mockito.when(service.update(eq(id), any())).thenThrow(new NotFoundException(TYPE_OBJECT_NAME, "id=1"));

            /*
             * When
             */
            // Call URL
            String url = String.format("%s/%d", TYPE_BASE_URL, id);
            // POST Call
            MockHttpServletRequestBuilder patch = patch(url)
                    .contentType(APPLICATION_JSON)
                    .content("{\"name\": \"Vaccine\"}");

            // MVC Mock Execution
            mockMvc
                    .perform(patch)
                    .andExpectAll(
                            // Status
                            status().isNotFound(),
                            // Content type
                            content().contentType(APPLICATION_PROBLEM_JSON),
                            // Result paths
                            jsonPath("$.title").value(expectedOutput.getTitle()),
                            jsonPath("$.status").value(expectedOutput.getStatus()),
                            jsonPath("$.detail").value(expectedOutput.getDetail()),
                            jsonPath("$.description").value("The resource can not be found on the server")
                    );

            /*
             * Then
             */
            Mockito.verify(service, times(1)).update(eq(id), any());
        }

    }

    @Nested
    @Order(value = 5)
    @DisplayName(value = "[Method]: DELETE /type/{id}")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class DeleteTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given an entity with the given ID -> Call to delete() method and remove it")
        void Given_EntityWithGivenId_When_DeleteCallIsExecuted_Then_RemoveIt() throws Exception {
            /*
             * Given
             */
            Long id = 1L;

            /*
             * When
             */
            // Call URL
            String url = String.format("%s/%d", TYPE_BASE_URL, id);
            // GET Call
            MockHttpServletRequestBuilder delete = delete(url);
            // MVC Mock Execution
            mockMvc
                    .perform(delete)
                    .andExpect(status().isNoContent());

            /*
             * Then
             */
            Mockito.verify(service, times(1)).delete(id);
        }

        @Test
        @Order(value = 1)
        @DisplayName(value = "2. Given an ID without associated entity in database -> Call to delete() method and throw a NotFoundException")
        void Given_IdWithoutAssociatedEntity_When_DeleteCallIsExecuted_Then_ThrowNotFoundException() throws Exception {
            /*
             * Given
             */
            // ID
            Long id = 1L;
            // Expected output
            ProblemDetailDto expectedOutput = ProblemDetailDto.ProblemDetailDtoBuilder
                    .newInstance(NOT_FOUND, "The object [Type] with parameters [id=1] not found!")
                    .withType(URI.create(TYPE_BASE_URL))
                    .withTitle("Resource not found")
                    .withProperty(DESCRIPTION, "The resource can not be found on the server")
                    .build();

            // Mocks
            Mockito.doThrow(new NotFoundException(TYPE_OBJECT_NAME, "id=1")).when(service).delete(id);

            /*
             * When
             */
            // Call URL
            String url = String.format("%s/%d", TYPE_BASE_URL, id);
            // GET Call
            MockHttpServletRequestBuilder delete = delete(url);
            // MVC Mock Execution
            mockMvc
                    .perform(delete)
                    .andExpectAll(
                            // Status
                            status().isNotFound(),
                            // Content type
                            content().contentType(APPLICATION_PROBLEM_JSON),
                            // Result paths
                            jsonPath("$.title").value(expectedOutput.getTitle()),
                            jsonPath("$.status").value(expectedOutput.getStatus()),
                            jsonPath("$.detail").value(expectedOutput.getDetail()),
                            jsonPath("$.description").value("The resource can not be found on the server")
                    );

            /*
             * Then
             */
            Mockito.verify(service, times(1)).delete(id);
        }

    }

}
