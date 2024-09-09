package com.adamonis.employeeservice.service;

import com.adamonis.employeeservice.dto.EmployeeRequestDto;
import com.adamonis.employeeservice.dto.EmployeeResponseDto;
import com.adamonis.employeeservice.dto.NewEmployeeResponseDto;
import com.adamonis.employeeservice.exception.EmployeeNotFoundException;
import com.adamonis.employeeservice.mapper.EmployeeMapper;
import com.adamonis.employeeservice.model.enums.Department;
import com.adamonis.employeeservice.model.Employee;
import com.adamonis.employeeservice.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeService employeeService;


    @Test
    void testCreateEmployee() {
        EmployeeRequestDto requestDto = createEmployeeRequestDto();
        Employee employee = createFirstEmployee();
        NewEmployeeResponseDto responseDto = new NewEmployeeResponseDto(1L);

        when(employeeMapper.mapEmployeeRequestDtoToEmployee(requestDto)).thenReturn(employee);
        when(employeeRepository.create(employee)).thenReturn(1L);

        NewEmployeeResponseDto result = employeeService.createEmployee(requestDto);

        assertEquals(responseDto, result);
        verify(employeeMapper).mapEmployeeRequestDtoToEmployee(requestDto);
        verify(employeeRepository).create(employee);
    }

    @Test
    void testGetEmployeeByIdFound() {
        Employee employee = createFirstEmployee();
        EmployeeResponseDto responseDto = createEmployeeResponseDto();

        when(employeeRepository.findFirstByPredicate(any())).thenReturn(Optional.of(employee));
        when(employeeMapper.mapEmployeeToEmployeeResponseDto(employee)).thenReturn(responseDto);

        EmployeeResponseDto result = employeeService.getEmployeeById(employee.getId());

        assertEquals(responseDto, result);
        verify(employeeRepository).findFirstByPredicate(any());
        verify(employeeMapper).mapEmployeeToEmployeeResponseDto(employee);
    }

    @Test
    void testGetEmployeeByIdNotFound() {
        Long employeeId = 1L;

        when(employeeRepository.findFirstByPredicate(any())).thenReturn(Optional.empty());

        EmployeeNotFoundException thrownException = assertThrows(EmployeeNotFoundException.class, () ->
                employeeService.getEmployeeById(employeeId)
        );

        assertEquals("Employee with ID " + employeeId + " not found", thrownException.getMessage());
    }

    @Test
    void testSearchEmployees() {
        Employee firstEmployee = createFirstEmployee();
        Employee secondEmployee = createSecondEmployee();
        EmployeeResponseDto responseDto = createEmployeeResponseDto();

        when(employeeRepository.findByPredicate(any())).thenReturn(List.of(firstEmployee));
        when(employeeMapper.mapEmployeeToEmployeeResponseDto(firstEmployee)).thenReturn(responseDto);

        List<EmployeeResponseDto> result = employeeService.searchEmployees("Moh", BigDecimal.valueOf(500), BigDecimal.valueOf(1500));

        assertEquals(List.of(responseDto), result);
        verify(employeeRepository).findByPredicate(any());
        verify(employeeMapper).mapEmployeeToEmployeeResponseDto(firstEmployee);
    }

    @Test
    void testSearchEmployeesWithNoResults() {
        when(employeeRepository.findByPredicate(any())).thenReturn(List.of());

        List<EmployeeResponseDto> result = employeeService.searchEmployees("Al", BigDecimal.valueOf(80000), BigDecimal.valueOf(100000));

        assertEquals(List.of(), result);
        verify(employeeRepository).findByPredicate(any());
    }

    private EmployeeRequestDto createEmployeeRequestDto() {
        return EmployeeRequestDto.builder()
                .firstName("Mohamed")
                .lastName("Ahmed")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .salary(BigDecimal.valueOf(1000))
                .joinDate(LocalDate.of(2023, 5, 18))
                .department(Department.IT)
                .build();
    }

    private Employee createFirstEmployee() {
        return Employee.builder()
                .id(1L)
                .firstName("Mohamed")
                .lastName("Ahmed")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .salary(BigDecimal.valueOf(1000))
                .joinDate(LocalDate.of(2023, 5, 18))
                .department(Department.IT)
                .build();
    }

    private Employee createSecondEmployee() {
        return Employee.builder()
                .id(2L)
                .firstName("Salman")
                .lastName("Ebrahim")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .salary(BigDecimal.valueOf(2000))
                .joinDate(LocalDate.of(2016, 3, 18))
                .department(Department.BUSINESS)
                .build();
    }

    private EmployeeResponseDto createEmployeeResponseDto() {
        return EmployeeResponseDto.builder()
                .id(1L)
                .firstName("Mohamed")
                .lastName("Ahmed")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .salary(BigDecimal.valueOf(1000))
                .joinDate(LocalDate.of(2023, 5, 18))
                .department(Department.IT)
                .build();
    }
}