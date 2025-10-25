package com.vn.pos.service;

import com.vn.pos.dto.EmployeeDTO.ChangePasswordRequest;
import com.vn.pos.dto.EmployeeDTO.EmployeeRequest;
import com.vn.pos.dto.EmployeeDTO.EmployeeResponse;
import com.vn.pos.dto.EmployeeDTO.EmployeeUpdateRequest;
import com.vn.pos.model.Employee;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse createEmployee(EmployeeRequest employee);
    List<EmployeeResponse> getAllEmployees();
    EmployeeResponse getEmployeeById(Integer id);
    EmployeeResponse updateEmployee(EmployeeUpdateRequest employee);
    void deleteEmployeeById(Integer id);

    EmployeeResponse findByUsername(String username);
    boolean existsByUsername(String username);
    List<EmployeeResponse> findByRole(Employee.EmployeeRole role);
    void changePassword(Integer id, ChangePasswordRequest newPassword);
}
