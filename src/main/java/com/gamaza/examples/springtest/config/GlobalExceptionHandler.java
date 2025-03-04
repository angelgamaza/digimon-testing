package com.gamaza.examples.springtest.config;

import com.gamaza.examples.springtest.dto.response.ProblemDetailDto;
import com.gamaza.examples.springtest.exception.AlreadyExistsException;
import com.gamaza.examples.springtest.exception.NotFoundException;
import com.gamaza.examples.springtest.util.ExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static org.springframework.http.HttpStatus.*;

/**
 * Global Exception Handler
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Global constants
    private static final String DESCRIPTION = "description";

    @ExceptionHandler(value = {
            NotFoundException.class,
            NoResourceFoundException.class
    })
    ResponseEntity<ProblemDetailDto> handleNotFoundException(Exception exception) {
        // Build the result
        ProblemDetailDto result = ProblemDetailDto.ProblemDetailDtoBuilder
                .newInstance(NOT_FOUND, ExceptionUtils.getCauseOrDefaultMessage(exception))
                .withTitle("Resource not found")
                .withProperty(DESCRIPTION, "The resource can not be found on the server")
                .build();

        // Return the response
        return new ResponseEntity<>(result, NOT_FOUND);
    }

    @ExceptionHandler(value = AlreadyExistsException.class)
    ResponseEntity<ProblemDetailDto> handleAlreadyExistsException(AlreadyExistsException exception) {
        // Build the result
        ProblemDetailDto result = ProblemDetailDto.ProblemDetailDtoBuilder
                .newInstance(CONFLICT, ExceptionUtils.getCauseOrDefaultMessage(exception))
                .withTitle("Resource already exists")
                .withProperty(DESCRIPTION, "The resource already exists on the server")
                .build();

        // Return the response
        return new ResponseEntity<>(result, CONFLICT);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class, MethodArgumentTypeMismatchException.class})
    ResponseEntity<ProblemDetailDto> handleIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(
                ProblemDetailDto.ProblemDetailDtoBuilder
                        .newInstance(BAD_REQUEST, ExceptionUtils.getCauseOrDefaultMessage(exception))
                        .withTitle("Illegal argument received")
                        .withProperty(DESCRIPTION, "The received argument is not valid")
                        .build()
        );
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ProblemDetailDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return ResponseEntity.badRequest().body(
                ProblemDetailDto.ProblemDetailDtoBuilder
                        .newInstance(BAD_REQUEST, ExceptionUtils.getCauseOrDefaultMessage(exception))
                        .withTitle("Invalid argument received for the controller")
                        .withProperty(DESCRIPTION, "The data arguments contain one or more invalid values")
                        .build()
        );
    }

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ProblemDetailDto> handleException(Exception exception) {
        return ResponseEntity.internalServerError().body(
                ProblemDetailDto.ProblemDetailDtoBuilder
                        .newInstance(INTERNAL_SERVER_ERROR, ExceptionUtils.getCauseOrDefaultMessage(exception))
                        .withTitle("Internal server error")
                        .withProperty(DESCRIPTION, "Server response with an Internal Error")
                        .build()
        );
    }

}
