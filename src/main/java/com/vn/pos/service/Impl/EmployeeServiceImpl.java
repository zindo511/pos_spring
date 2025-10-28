package com.vn.pos.service.Impl;

import com.vn.pos.exception.Custom.DuplicateResourceException;
import com.vn.pos.exception.Custom.InvalidOperationException;
import com.vn.pos.exception.Custom.ResourceNotFoundException;
import com.vn.pos.mapper.EmployeeMapper;
import com.vn.pos.dto.EmployeeDTO.ChangePasswordRequest;
import com.vn.pos.dto.EmployeeDTO.EmployeeRequest;
import com.vn.pos.dto.EmployeeDTO.EmployeeResponse;
import com.vn.pos.dto.EmployeeDTO.EmployeeUpdateRequest;
import com.vn.pos.model.Employee;
import com.vn.pos.repository.EmployeeRepository;
import com.vn.pos.service.EmployeeService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        if (employeeRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("Resource", "username", request.getUsername());
        }
        Employee employee = employeeMapper.toEntity(request);
        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.toResponse(savedEmployee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(employeeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse getEmployeeById(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource", "id", id));
        return employeeMapper.toResponse(employee);
    }

    @Override
    public EmployeeResponse updateEmployee(EmployeeUpdateRequest employee) {
        Employee existing = employeeRepository.findById(employee.getId())
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employee.getId()));

        if (employee.getUsername() != null && !employee.getUsername().equals(existing.getUsername())) {
            existing.setUsername(employee.getUsername());
        }

        if (employee.getFullName() != null) {
            existing.setFullName(employee.getFullName());
        }

        if (employee.getRole() != null) {
            existing.setRole(employee.getRole());
        }

        if (employee.getPhone() != null) {
            existing.setPhone(employee.getPhone());
        }

        Employee updatedEmployee = employeeRepository.save(existing);
        return employeeMapper.toResponse(updatedEmployee);
    }

    @Override
    public void deleteEmployeeById(Integer id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Resource", "id", id);
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public EmployeeResponse findByUsername(String username) {
        Employee employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Resource", "username", username));
        return employeeMapper.toResponse(employee);
    }

    @Override
    public boolean existsByUsername(String username) {
        return employeeRepository.existsByUsername(username);
    }

    @Override
    public List<EmployeeResponse> findByRole(Employee.EmployeeRole role) {
        List<Employee> employees = employeeRepository.findByRole(role);
        if (employees.isEmpty()) {
            throw new ResourceNotFoundException("Resource", "role", role);
        }
        return employees.stream()
                .map(employeeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void changePassword(Integer id, ChangePasswordRequest request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource", "id", id));

        if (!employee.getPassword().equals(request.getOldPassword())) {
            throw new InvalidOperationException("Old password not match");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new InvalidOperationException("ConfirmPassword don't match NewPassword");
        }

        employee.setPassword(request.getNewPassword());
        employeeRepository.save(employee);
    }
}
