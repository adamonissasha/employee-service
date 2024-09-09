package com.adamonis.employeeservice.service;

import com.adamonis.employeeservice.dto.EmployeeRequestDto;
import com.adamonis.employeeservice.dto.EmployeeResponseDto;
import com.adamonis.employeeservice.dto.NewEmployeeResponseDto;
import com.adamonis.employeeservice.exception.EmployeeNotFoundException;
import com.adamonis.employeeservice.mapper.EmployeeMapper;
import com.adamonis.employeeservice.model.Employee;
import com.adamonis.employeeservice.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public NewEmployeeResponseDto createEmployee(EmployeeRequestDto employeeRequestDto) {
        Employee employee = employeeMapper.mapEmployeeRequestDtoToEmployee(employeeRequestDto);

        long createdEmployeeId = employeeRepository.create(employee);

        return NewEmployeeResponseDto.builder()
                .id(createdEmployeeId)
                .build();
    }

    public EmployeeResponseDto getEmployeeById(Long id) {
        return employeeRepository.findFirstByPredicate((e -> e.getId().equals(id)))
                .map(employeeMapper::mapEmployeeToEmployeeResponseDto)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found"));
    }

    @Cacheable(value = "employees", key = "#name + #fromSalary + #toSalary", cacheManager = "cacheManager")
    public List<EmployeeResponseDto> searchEmployees(String name, BigDecimal fromSalary, BigDecimal toSalary) {
        return employeeRepository.findByPredicate((e ->
                        searchPredicate(name, fromSalary, toSalary, e))).stream()
                .map(employeeMapper::mapEmployeeToEmployeeResponseDto)
                .collect(Collectors.toList());
    }

    private static boolean searchPredicate(String name, BigDecimal fromSalary, BigDecimal toSalary, Employee employee) {
        return (employee.getFirstName().contains(name) || employee.getLastName().contains(name)) &&
                (employee.getSalary().compareTo(fromSalary) >= 0 && employee.getSalary().compareTo(toSalary) <= 0);
    }
}