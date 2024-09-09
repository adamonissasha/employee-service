package com.adamonis.employeeservice.controller;

import com.adamonis.employeeservice.dto.EmployeeRequestDto;
import com.adamonis.employeeservice.dto.EmployeeResponseDto;
import com.adamonis.employeeservice.dto.NewEmployeeResponseDto;
import com.adamonis.employeeservice.exception.EmployeeNotFoundException;
import com.adamonis.employeeservice.service.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Controller for managing employee operations.
 * Handles requests for creating, retrieving, and searching employees.
 */
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@Validated
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * Creates a new employee.
     *
     * @param employeeRequestDto the request object containing employee details to be created
     * @return a response entity containing the details of the created employee with status 200 OK
     */
    @PostMapping
    public ResponseEntity<NewEmployeeResponseDto> createEmployee(@Valid @RequestBody EmployeeRequestDto employeeRequestDto) {
        return ResponseEntity.ok(employeeService.createEmployee(employeeRequestDto));
    }

    /**
     * Retrieves employee details by their ID.
     *
     * @param id the ID of the employee
     * @return a response entity containing the employee details with status 200 OK if the employee is found,
     * or status 404 Not Found if the employee is not found
     * @throws EmployeeNotFoundException if the employee with the given ID is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    /**
     * Searches for employees based on the provided parameters.
     *
     * @param name       the name or surname of the employee to search for
     * @param fromSalary the minimum salary for filtering
     * @param toSalary   the maximum salary for filtering
     * @return a list of employees that match the search criteria with status 200 OK
     * @throws IllegalArgumentException if fromSalary is greater than toSalary
     */
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> searchEmployees(
            @RequestParam String name,
            @RequestParam @DecimalMin("0.0") BigDecimal fromSalary,
            @RequestParam @DecimalMin("0.0") BigDecimal toSalary
    ) {
        if (fromSalary.compareTo(toSalary) > 0) {
            throw new IllegalArgumentException("From salary cannot be greater than to salary");
        }

        return ResponseEntity.ok(employeeService.searchEmployees(name, fromSalary, toSalary));
    }
}
