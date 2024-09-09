package com.adamonis.employeeservice.dto;

import com.adamonis.employeeservice.model.enums.Department;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Data Transfer Object for employee responses.
 * Used to transfer employee data from the service layer to the client.
 */
@Builder
public record EmployeeResponseDto(
        Long id,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        BigDecimal salary,
        LocalDate joinDate,
        Department department
) {
}