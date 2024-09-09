package com.adamonis.employeeservice.exception;

/**
 * Exception thrown when an employee is not found in the system.
 */
public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
