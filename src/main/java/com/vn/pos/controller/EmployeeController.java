package com.vn.pos.controller;

import com.vn.pos.dto.EmployeeDTO.ChangePasswordRequest;
import com.vn.pos.dto.EmployeeDTO.EmployeeRequest;
import com.vn.pos.dto.EmployeeDTO.EmployeeResponse;
import com.vn.pos.dto.EmployeeDTO.EmployeeUpdateRequest;
import com.vn.pos.exception.Custom.InvalidOperationException;
import com.vn.pos.model.Employee;
import com.vn.pos.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(
            @Valid @RequestBody EmployeeRequest request) {
        EmployeeResponse response = employeeService.createEmployee(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        List<EmployeeResponse> responses = employeeService.getAllEmployees();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Integer id) {
        EmployeeResponse response = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @PathVariable Integer id,
            @Valid @RequestBody EmployeeUpdateRequest request) {
        request.setId(id);
        EmployeeResponse response = employeeService.updateEmployee(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Integer id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<EmployeeResponse> findByUsername(@RequestParam String username){
        EmployeeResponse response = employeeService.findByUsername(username);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsByUsername(@RequestParam String username) {
        boolean exists = employeeService.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/role")
    public ResponseEntity<List<EmployeeResponse>> findByRole(@RequestParam Employee.EmployeeRole role) {
        List<EmployeeResponse> responses = employeeService.findByRole(role);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("{id}/change-password")
    public ResponseEntity<Void> changePassword(
            @PathVariable Integer id,
            @Valid @RequestBody ChangePasswordRequest request) {
        employeeService.changePassword(id, request);
        return ResponseEntity.noContent().build();
    }
}
