package com.adamonis.employeeservice.exception;

import com.adamonis.employeeservice.dto.ErrorResponseDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Global exception handler for REST controllers.
 * Handles validation errors, file access issues, and other exceptions globally across all REST controllers.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation exceptions for invalid requests.
     *
     * @param ex the {@link MethodArgumentNotValidException} that was thrown
     * @return a {@link ResponseEntity} containing details of the validation errors with status 400 Bad Request
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult().getAllErrors().stream()
                .collect(Collectors.toMap(
                        error -> ((org.springframework.validation.FieldError) error).getField(),
                        DefaultMessageSourceResolvable::getDefaultMessage
                ));
        return new ResponseEntity<>(new ErrorResponseDto(errors), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles constraint violation exceptions for validation errors.
     *
     * @param ex the {@link ConstraintViolationException} that was thrown
     * @return a {@link ResponseEntity} containing details of the constraint violations with status 400 Bad Request
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDto> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        Map<String, String> errors = ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage
                ));
        return new ResponseEntity<>(new ErrorResponseDto(errors), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all other exceptions that are not specifically handled.
     *
     * @param ex the {@link Exception} that was thrown
     * @return a {@link ResponseEntity} containing details of the unexpected error with status 500 Internal Server Error
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "An unexpected error occurred: " + ex.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(error), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles exceptions thrown when an employee is not found.
     *
     * @param ex the {@link EmployeeNotFoundException} that was thrown
     * @return a {@link ResponseEntity} containing the error message with status 404 Not Found
     */
    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDto> handleEmployeeNotFoundException(EmployeeNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(error), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles exceptions related to file access issues.
     *
     * @param ex the {@link FileAccessException} that was thrown
     * @return a {@link ResponseEntity} containing the error message with status 500 Internal Server Error
     */
    @ExceptionHandler(FileAccessException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDto> handleFileAccessException(FileAccessException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(error), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles {@link IllegalArgumentException} thrown by controllers.
     *
     * @param ex the {@link IllegalArgumentException} that was thrown
     * @return a {@link ResponseEntity} containing the error details with status 400 Bad Request
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
