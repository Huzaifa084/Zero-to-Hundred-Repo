package org.devcastle.zerotohundred.controllers;

import jakarta.servlet.ServletRequest;
import jakarta.validation.Valid;
import org.devcastle.zerotohundred.dto.EmployeeDTO;
import org.devcastle.zerotohundred.entities.Employee;
import org.devcastle.zerotohundred.exceptions.ResourceNotFoundException;
import org.devcastle.zerotohundred.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController()
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/get/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long employeeId) {
        Optional<EmployeeDTO> employee = employeeService.getEmployeeById(employeeId);
        return employee.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("Employee with id " + employeeId + " not found")
                );
    }

    @GetMapping("/get")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employee = employeeService.getAllEmployees();
        if (employee.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employee);
    }

    @PostMapping("/add")
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody @Valid EmployeeDTO employee) {
        return new ResponseEntity<>(employeeService.createEmployee(employee), HttpStatus.CREATED);
    }

    @PutMapping("/update/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody @Valid EmployeeDTO employee, @PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeService.updateEmployeeById(employeeId, employee));
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long employeeId) {
        boolean deleted = employeeService.deleteEmployeeById(employeeId);
        if (deleted)
            return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/update-partially/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployeePartiallyById(@PathVariable Long employeeId, @RequestBody Map<String, Object> updates) {
        EmployeeDTO employeeDTO = employeeService.updateEmployeePartiallyById(employeeId, updates);
        if (employeeDTO == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDTO);
    }

}
