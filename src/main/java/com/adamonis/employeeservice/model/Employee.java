package com.adamonis.employeeservice.model;

import com.adamonis.employeeservice.model.enums.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents an employee in the system.
 * This class contains all the necessary details about an employee.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private BigDecimal salary;
    private LocalDate joinDate;
    private Department department;
}
