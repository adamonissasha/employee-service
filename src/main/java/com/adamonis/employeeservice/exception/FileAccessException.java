package com.adamonis.employeeservice.exception;


/**
 * Exception thrown when there is an issue with file access.
 */
public class FileAccessException extends RuntimeException {
    public FileAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}