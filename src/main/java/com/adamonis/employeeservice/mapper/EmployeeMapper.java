package com.adamonis.employeeservice.mapper;

import com.adamonis.employeeservice.dto.EmployeeRequestDto;
import com.adamonis.employeeservice.dto.EmployeeResponseDto;
import com.adamonis.employeeservice.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for converting between Employee and EmployeeResponseDto.
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    /**
     * Converts an Employee entity to an EmployeeResponseDto.
     *
     * @param employee the Employee entity to be converted
     * @return the corresponding EmployeeResponseDto
     */
    EmployeeResponseDto mapEmployeeToEmployeeResponseDto(Employee employee);

    /**
     * Converts an EmployeeRequestDto to an Employee entity.
     *
     * @param employeeRequestDto the EmployeeRequestDto to be converted
     * @return the corresponding Employee entity
     */
    @Mapping(target = "id", ignore = true)
    Employee mapEmployeeRequestDtoToEmployee(EmployeeRequestDto employeeRequestDto);
}
