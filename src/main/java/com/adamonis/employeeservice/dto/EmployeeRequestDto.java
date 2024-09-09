package com.adamonis.employeeservice.dto;

import com.adamonis.employeeservice.model.enums.Department;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Data Transfer Object for creating an employee.
 * Used to transfer employee data from client requests to service layer.
 */
@Builder
public record EmployeeRequestDto(
        @NotBlank(message = "First name cannot be empty")
        String firstName,

        @NotBlank(message = "Last name cannot be empty")
        String lastName,

        @NotNull(message = "Date of birth cannot be null")
        @Past(message = "Date of birth must be a past date")
        LocalDate dateOfBirth,

        @NotNull(message = "Salary cannot be null")
        @DecimalMin(value = "0.0", message = "Salary must be positive")
        BigDecimal salary,

        @NotNull(message = "Join date cannot be null")
        LocalDate joinDate,

        @NotNull(message = "Department cannot be null")
        Department department
) {
}