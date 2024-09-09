package com.adamonis.employeeservice.dto;

import lombok.Builder;

/**
 * Data Transfer Object for the response after creating a new employee.
 * Contains the ID of the newly created employee.
 */
@Builder
public record NewEmployeeResponseDto(
        Long id
) {
}