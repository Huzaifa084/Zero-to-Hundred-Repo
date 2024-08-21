package org.devcastle.zerotohundred.services;

import org.aspectj.util.Reflection;
import org.devcastle.zerotohundred.dto.EmployeeDTO;
import org.devcastle.zerotohundred.entities.Employee;
import org.devcastle.zerotohundred.exceptions.ResourceNotFoundException;
import org.devcastle.zerotohundred.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.util.ReflectionUtils;


@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = new ModelMapper();
    }

    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        return employeeRepository.findById(id).map(employee -> modelMapper.map(employee, EmployeeDTO.class));
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .toList();
    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeInput) {
        Employee newEmployee = modelMapper.map(employeeInput, Employee.class);
        Employee employee1 = employeeRepository.save(newEmployee);
        return modelMapper.map(employee1, EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployeeById(Long employeeId, EmployeeDTO employee) {
        Employee employee1 = modelMapper.map(employee, Employee.class);
        employee1.setEmployeeId(employeeId);
        Employee employee2 = employeeRepository.save(employee1);
        return modelMapper.map(employee2, EmployeeDTO.class);
    }

    public boolean deleteEmployeeById(Long employeeId) {
        isNotExistsById(employeeId);
        employeeRepository.deleteById(employeeId);
        return true;
    }

    public EmployeeDTO updateEmployeePartiallyById(Long employeeId, Map<String, Object> updates) {
        isNotExistsById(employeeId);

        Employee employee = employeeRepository.findById(employeeId).get();

        updates.forEach((field, updatedValue) -> {
            Field fieldToUpdate = ReflectionUtils.findField(Employee.class, field);
            if (fieldToUpdate != null) {
                fieldToUpdate.setAccessible(true);
                // Handle conversion for LocalDate fields
                if (fieldToUpdate.getType().equals(LocalDate.class)) {
                    LocalDate date = LocalDate.parse(updatedValue.toString()); // Convert String to LocalDate
                    ReflectionUtils.setField(fieldToUpdate, employee, date);
                } else {
                    ReflectionUtils.setField(fieldToUpdate, employee, updatedValue);
                }
            }
        });

        Employee updatedEmployee = employeeRepository.save(employee);
        return modelMapper.map(updatedEmployee, EmployeeDTO.class);
    }

    private void isNotExistsById(Long employeeId) {
        boolean exists = employeeRepository.existsById(employeeId);
        if (!exists) throw new ResourceNotFoundException("Employee not found with id: " + employeeId);
    }
}
