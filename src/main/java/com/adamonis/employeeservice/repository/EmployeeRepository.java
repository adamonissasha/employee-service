package com.adamonis.employeeservice.repository;

import com.adamonis.employeeservice.exception.FileAccessException;
import com.adamonis.employeeservice.model.Employee;
import com.adamonis.employeeservice.service.IdGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Repository
@RequiredArgsConstructor
public class EmployeeRepository {

    private final ObjectMapper objectMapper;
    private final File databaseFile = new File("employees.json");
    private final IdGenerator idGenerator = new IdGenerator("employee-id-sequence.txt");

    public long create(Employee employee) {
        employee.setId(idGenerator.getNextId());
        save(employee);
        return employee.getId();
    }

    public List<Employee> findAll() {
        try {
            if (!databaseFile.exists()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(databaseFile, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new FileAccessException("Error reading employee data", e);
        }
    }

    public void save(Employee employee) {
        try {
            List<Employee> employees = findAll();
            employees.add(employee);
            objectMapper.writeValue(databaseFile, employees);
        } catch (IOException e) {
            throw new FileAccessException("Error saving employee data", e);
        }
    }

    public Optional<Employee> findFirstByPredicate(Predicate<Employee> predicate) {
        return findAll().stream()
                .filter(predicate)
                .findFirst();
    }

    public List<Employee> findByPredicate(Predicate<Employee> predicate) {
        return findAll().stream()
                .filter(predicate)
                .toList();
    }
}
