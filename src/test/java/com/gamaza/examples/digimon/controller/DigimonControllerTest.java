package com.gamaza.examples.digimon.controller;

import com.gamaza.examples.digimon.controller.base.BaseControllerTest;
import com.gamaza.examples.digimon.dto.response.ProblemDetailDto;
import com.gamaza.examples.digimon.dto.response.digimon.DigimonDto;
import com.gamaza.examples.digimon.dto.response.digimon.DigimonRelationsDto;
import com.gamaza.examples.digimon.dto.response.level.LevelDto;
import com.gamaza.examples.digimon.dto.response.type.TypeDto;
import com.gamaza.examples.digimon.exception.AlreadyExistsException;
import com.gamaza.examples.digimon.exception.NotFoundException;
import com.gamaza.examples.digimon.service.DigimonService;
import com.gamaza.examples.digimon.service.impl.DigimonServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.net.URI;
import java.util.Arrays;
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
 * Test class for {@link DigimonController}
 */
@Tag(value = DIGIMON_TAG)
@DisplayName(value = "[Controller]: Digimon")
@ContextConfiguration(classes = {
        DigimonServiceImpl.class,
        DigimonController.class
})
class DigimonControllerTest extends BaseControllerTest {

    @MockitoBean(enforceOverride = true, reset = AFTER)
    private DigimonService service;

    @Nested
    @Order(value = 0)
    @DisplayName(value = "[Method]: POST /digimon")
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
            DigimonDto expectedOutput = new DigimonDto(1L, "Agumon");
            // Mocks
            Mockito.when(service.save(any())).thenReturn(expectedOutput);

            /*
             * When
             */
            // POST Call
            MockHttpServletRequestBuilder post = post(DIGIMON_BASE_URL)
                    .contentType(APPLICATION_JSON)
                    .content("{\"name\": \"Agumon\", \"typeId\": 1, \"levelId\": 3}");

            // MVC Mock Execution
            mockMvc
                    .perform(post)
                    .andExpectAll(
                            // Status
                            status().isCreated(),
                            // Content type
                            content().contentType(APPLICATION_JSON),
                            // Headers
                            header().string("Location", "http://localhost:8081/digimon/1"),
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
                    .newInstance(CONFLICT, "The object [Digimon] with parameters [name=Agumon] already exists!")
                    .withType(URI.create("/digimon"))
                    .withTitle("Resource already exists")
                    .withProperty(DESCRIPTION, "The resource already exists on the server")
                    .build();

            // Mocks
            Mockito.when(service.save(any())).thenThrow(new AlreadyExistsException(DIGIMON_OBJECT_NAME, "name=Agumon"));

            /*
             * When
             */
            // POST Call
            MockHttpServletRequestBuilder post = post(DIGIMON_BASE_URL)
                    .contentType(APPLICATION_JSON)
                    .content("{\"name\": \"Agumon\", \"typeId\": 1, \"levelId\": 3}");

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
                            jsonPath("$.status").value(409),
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
                    .withType(URI.create(DIGIMON_BASE_URL))
                    .withTitle("Invalid argument received for the controller")
                    .withProperty(DESCRIPTION, "The data arguments contain one or more invalid values")
                    .build();

            /*
             * When
             */
            // POST Call
            MockHttpServletRequestBuilder post = post(DIGIMON_BASE_URL)
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
    @DisplayName(value = "[Method]: GET /digimon")
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
            DigimonDto digimon1 = DigimonDto.of(1L, "Baby");
            DigimonDto digimon2 = DigimonDto.of(2L, "Vaccine");
            // Expected output
            List<DigimonDto> expectedOutput = Arrays.asList(digimon1, digimon2);
            // Mocks
            Mockito.when(service.findAll()).thenReturn(expectedOutput);

            /*
             * When
             */
            // GET Call
            MockHttpServletRequestBuilder get = get(DIGIMON_BASE_URL);
            // MVC Mock Execution
            mockMvc
                    .perform(get)
                    .andExpectAll(
                            // Status
                            status().isOk(),
                            // Content digimon
                            content().contentType(APPLICATION_JSON),
                            // Result paths
                            jsonPath("$.length()").value(expectedOutput.size()),
                            jsonPath("$[0].id").value(digimon1.getId()),
                            jsonPath("$[0].name").value(digimon1.getName()),
                            jsonPath("$[1].id").value(digimon2.getId()),
                            jsonPath("$[1].name").value(digimon2.getName())
                    );

            /*
             * Then
             */
            Mockito.verify(service, times(1)).findAll();
        }

    }

    @Nested
    @Order(value = 2)
    @DisplayName(value = "[Method]: GET /digimon/{id}")
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
            DigimonRelationsDto expectedOutput = DigimonRelationsDto.of(
                    1L,
                    "Agumon",
                    TypeDto.of(1L, "Vaccine"),
                    LevelDto.of(3L, "Rookie")
            );
            // Mocks
            Mockito.when(service.findById(id)).thenReturn(expectedOutput);

            /*
             * When
             */
            // Call URL
            String url = String.format("%s/%d", DIGIMON_BASE_URL, id);
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
                            jsonPath("$.name").value(expectedOutput.getName()),
                            jsonPath("$.type.id").value(expectedOutput.getType().getId()),
                            jsonPath("$.type.name").value(expectedOutput.getType().getName()),
                            jsonPath("$.level.id").value(expectedOutput.getLevel().getId()),
                            jsonPath("$.level.name").value(expectedOutput.getLevel().getName())
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
                    .newInstance(NOT_FOUND, "The object [Digimon] with parameters [id=1] not found!")
                    .withType(URI.create(DIGIMON_BASE_URL))
                    .withTitle("Resource not found")
                    .withProperty(DESCRIPTION, "The resource can not be found on the server")
                    .build();

            // Mocks
            Mockito.when(service.findById(id)).thenThrow(new NotFoundException(DIGIMON_OBJECT_NAME, "id=1"));

            /*
             * When
             */
            // Call URL
            String url = String.format("%s/%d", DIGIMON_BASE_URL, id);
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
    @DisplayName(value = "[Method]: GET /digimon/name")
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
            String name = "Agumon";
            // Expected output
            DigimonRelationsDto expectedOutput = DigimonRelationsDto.of(
                    1L,
                    "Agumon",
                    TypeDto.of(1L, "Vaccine"),
                    LevelDto.of(3L, "Rookie")
            );
            // Mocks
            Mockito.when(service.findByName(name)).thenReturn(expectedOutput);

            /*
             * When
             */
            // Call URL
            String url = String.format("%s/name", DIGIMON_BASE_URL);
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
                            jsonPath("$.name").value(expectedOutput.getName()),
                            jsonPath("$.type.id").value(expectedOutput.getType().getId()),
                            jsonPath("$.type.name").value(expectedOutput.getType().getName()),
                            jsonPath("$.level.id").value(expectedOutput.getLevel().getId()),
                            jsonPath("$.level.name").value(expectedOutput.getLevel().getName())
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
            String name = "Agrumon";
            // Expected output
            ProblemDetailDto expectedOutput = ProblemDetailDto.ProblemDetailDtoBuilder
                    .newInstance(NOT_FOUND, "The object [Digimon] with parameters [name=Agrumon] not found!")
                    .withType(URI.create(DIGIMON_BASE_URL))
                    .withTitle("Resource not found")
                    .withProperty(DESCRIPTION, "The resource can not be found on the server")
                    .build();

            // Mocks
            Mockito.when(service.findByName(name)).thenThrow(new NotFoundException(DIGIMON_OBJECT_NAME, "name=Agrumon"));

            /*
             * When
             */
            // Call URL
            String url = String.format("%s/name", DIGIMON_BASE_URL);
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
    @DisplayName(value = "[Method]: GET /digimon/type/{id}")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class FindAllByTypeIdTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given several entities with the given Type ID in the system -> Call to findAllByTypeId() method and return them")
        void Given_SeveralEntitiesWithTheGivenTypeIdInTheSystem_When_GetCallIsExecuted_Then_ReturnThem() throws Exception {
            /*
             * Given
             */
            // ID
            Long id = 1L;
            // Data
            DigimonDto digimon1 = DigimonDto.of(1L, "Agumon");
            DigimonDto digimon2 = DigimonDto.of(2L, "Gabumon");
            // Expected output
            List<DigimonDto> expectedOutput = Arrays.asList(digimon1, digimon2);
            // Mocks
            Mockito.when(service.findAllByTypeId(id)).thenReturn(expectedOutput);

            /*
             * When
             */
            // Call URL
            String url = String.format("%s/type/%d", DIGIMON_BASE_URL, id);
            // GET Call
            MockHttpServletRequestBuilder get = get(url);
            // MVC Mock Execution
            mockMvc
                    .perform(get)
                    .andExpectAll(
                            // Status
                            status().isOk(),
                            // Content digimon
                            content().contentType(APPLICATION_JSON),
                            // Result paths
                            jsonPath("$.length()").value(expectedOutput.size()),
                            jsonPath("$[0].id").value(digimon1.getId()),
                            jsonPath("$[0].name").value(digimon1.getName()),
                            jsonPath("$[1].id").value(digimon2.getId()),
                            jsonPath("$[1].name").value(digimon2.getName())
                    );

            /*
             * Then
             */
            Mockito.verify(service, times(1)).findAllByTypeId(id);
        }

    }

    @Nested
    @Order(value = 5)
    @DisplayName(value = "[Method]: GET /digimon/type/name")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class FindAllByTypeNameTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given several entities with the given Type name in the system -> Call to findAllByTypeName() method and return them")
        void Given_SeveralEntitiesWithTheGivenTypeNameInTheSystem_When_GetCallIsExecuted_Then_ReturnThem() throws Exception {
            /*
             * Given
             */
            // ID
            String name = "Vaccine";
            // Data
            DigimonDto digimon1 = DigimonDto.of(1L, "Agumon");
            DigimonDto digimon2 = DigimonDto.of(2L, "Gabumon");
            // Expected output
            List<DigimonDto> expectedOutput = Arrays.asList(digimon1, digimon2);
            // Mocks
            Mockito.when(service.findAllByTypeName(name)).thenReturn(expectedOutput);

            /*
             * When
             */
            // Call URL
            String url = String.format("%s/type/name", DIGIMON_BASE_URL);
            // GET Call
            MockHttpServletRequestBuilder get = get(url)
                    .param("name", name);

            // MVC Mock Execution
            mockMvc
                    .perform(get)
                    .andExpectAll(
                            // Status
                            status().isOk(),
                            // Content digimon
                            content().contentType(APPLICATION_JSON),
                            // Result paths
                            jsonPath("$.length()").value(expectedOutput.size()),
                            jsonPath("$[0].id").value(digimon1.getId()),
                            jsonPath("$[0].name").value(digimon1.getName()),
                            jsonPath("$[1].id").value(digimon2.getId()),
                            jsonPath("$[1].name").value(digimon2.getName())
                    );

            /*
             * Then
             */
            Mockito.verify(service, times(1)).findAllByTypeName(name);
        }

    }

    @Nested
    @Order(value = 6)
    @DisplayName(value = "[Method]: GET /digimon/level/{id}")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class FindAllByLevelIdTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given several entities with the given Level ID in the system -> Call to findAllByLevelId() method and return them")
        void Given_SeveralEntitiesWithTheGivenLevelIdInTheSystem_When_GetCallIsExecuted_Then_ReturnThem() throws Exception {
            /*
             * Given
             */
            // ID
            Long id = 1L;
            // Data
            DigimonDto digimon1 = DigimonDto.of(1L, "Agumon");
            DigimonDto digimon2 = DigimonDto.of(2L, "Gabumon");
            // Expected output
            List<DigimonDto> expectedOutput = Arrays.asList(digimon1, digimon2);
            // Mocks
            Mockito.when(service.findAllByLevelId(id)).thenReturn(expectedOutput);

            /*
             * When
             */
            // Call URL
            String url = String.format("%s/level/%d", DIGIMON_BASE_URL, id);
            // GET Call
            MockHttpServletRequestBuilder get = get(url);
            // MVC Mock Execution
            mockMvc
                    .perform(get)
                    .andExpectAll(
                            // Status
                            status().isOk(),
                            // Content digimon
                            content().contentType(APPLICATION_JSON),
                            // Result paths
                            jsonPath("$.length()").value(expectedOutput.size()),
                            jsonPath("$[0].id").value(digimon1.getId()),
                            jsonPath("$[0].name").value(digimon1.getName()),
                            jsonPath("$[1].id").value(digimon2.getId()),
                            jsonPath("$[1].name").value(digimon2.getName())
                    );

            /*
             * Then
             */
            Mockito.verify(service, times(1)).findAllByLevelId(id);
        }

    }

    @Nested
    @Order(value = 7)
    @DisplayName(value = "[Method]: GET /digimon/level/name")
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
    class FindAllByLevelNameTests {

        @Test
        @Order(value = 0)
        @DisplayName(value = "1. Given several entities with the given Level name in the system -> Call to findAllByLevelName() method and return them")
        void Given_SeveralEntitiesWithTheGivenLevelNameInTheSystem_When_GetCallIsExecuted_Then_ReturnThem() throws Exception {
            /*
             * Given
             */
            // ID
            String name = "Vaccine";
            // Data
            DigimonDto digimon1 = DigimonDto.of(1L, "Agumon");
            DigimonDto digimon2 = DigimonDto.of(2L, "Gabumon");
            // Expected output
            List<DigimonDto> expectedOutput = Arrays.asList(digimon1, digimon2);
            // Mocks
            Mockito.when(service.findAllByLevelName(name)).thenReturn(expectedOutput);

            /*
             * When
             */
            // Call URL
            String url = String.format("%s/level/name", DIGIMON_BASE_URL);
            // GET Call
            MockHttpServletRequestBuilder get = get(url)
                    .param("name", name);

            // MVC Mock Execution
            mockMvc
                    .perform(get)
                    .andExpectAll(
                            // Status
                            status().isOk(),
                            // Content digimon
                            content().contentType(APPLICATION_JSON),
                            // Result paths
                            jsonPath("$.length()").value(expectedOutput.size()),
                            jsonPath("$[0].id").value(digimon1.getId()),
                            jsonPath("$[0].name").value(digimon1.getName()),
                            jsonPath("$[1].id").value(digimon2.getId()),
                            jsonPath("$[1].name").value(digimon2.getName())
                    );

            /*
             * Then
             */
            Mockito.verify(service, times(1)).findAllByLevelName(name);
        }

    }

    @Nested
    @Order(value = 8)
    @DisplayName(value = "[Method]: PATCH /digimon/{id}")
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
            DigimonRelationsDto expectedOutput = new DigimonRelationsDto(
                    1L,
                    "Agumon",
                    TypeDto.of(1L, "Vaccine"),
                    LevelDto.of(3L, "Rookie")
            );
            // Mocks
            Mockito.when(service.update(eq(id), any())).thenReturn(expectedOutput);

            /*
             * When
             */
            // Call URL
            String url = String.format("%s/%d", DIGIMON_BASE_URL, id);
            // POST Call
            MockHttpServletRequestBuilder patch = patch(url)
                    .contentType(APPLICATION_JSON)
                    .content("{\"name\": \"Agumon\"}");

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
                            jsonPath("$.name").value(expectedOutput.getName()),
                            jsonPath("$.type.id").value(expectedOutput.getType().getId()),
                            jsonPath("$.type.name").value(expectedOutput.getType().getName()),
                            jsonPath("$.level.id").value(expectedOutput.getLevel().getId()),
                            jsonPath("$.level.name").value(expectedOutput.getLevel().getName())
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
                    .newInstance(NOT_FOUND, "The object [Digimon] with parameters [id=1] not found!")
                    .withType(URI.create(DIGIMON_BASE_URL))
                    .withTitle("Resource not found")
                    .withProperty(DESCRIPTION, "The resource can not be found on the server")
                    .build();

            // Mocks
            Mockito.when(service.update(eq(id), any())).thenThrow(new NotFoundException(DIGIMON_OBJECT_NAME, "id=1"));

            /*
             * When
             */
            // Call URL
            String url = String.format("%s/%d", DIGIMON_BASE_URL, id);
            // POST Call
            MockHttpServletRequestBuilder patch = patch(url)
                    .contentType(APPLICATION_JSON)
                    .content("{\"name\": \"Agumon\"}");

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
    @Order(value = 9)
    @DisplayName(value = "[Method]: DELETE /digimon/{id}")
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
            String url = String.format("%s/%d", DIGIMON_BASE_URL, id);
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
                    .newInstance(NOT_FOUND, "The object [Digimon] with parameters [id=1] not found!")
                    .withType(URI.create(DIGIMON_BASE_URL))
                    .withTitle("Resource not found")
                    .withProperty(DESCRIPTION, "The resource can not be found on the server")
                    .build();

            // Mocks
            Mockito.doThrow(new NotFoundException(DIGIMON_OBJECT_NAME, "id=1")).when(service).delete(id);

            /*
             * When
             */
            // Call URL
            String url = String.format("%s/%d", DIGIMON_BASE_URL, id);
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
