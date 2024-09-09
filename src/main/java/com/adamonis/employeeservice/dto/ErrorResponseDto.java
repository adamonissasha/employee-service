package com.adamonis.employeeservice.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * DTO for representing validation errors.
 * Contains a map of error messages where keys are field names or property paths,
 * and values are the associated error messages.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {
    private Map<String, String> errors;
}