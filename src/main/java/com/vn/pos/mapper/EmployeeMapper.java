package com.vn.pos.mapper;

import com.vn.pos.dto.EmployeeDTO.EmployeeRequest;
import com.vn.pos.dto.EmployeeDTO.EmployeeResponse;
import com.vn.pos.model.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    // Request --> Entity (Client --> Database)
    public Employee toEntity(EmployeeRequest request) {
        Employee employee = new Employee();
        employee.setUsername(request.getUsername());
        employee.setPassword(request.getPassword());
        employee.setFullName(request.getFullName());
        employee.setRole(request.getRole());
        employee.setPhone(request.getPhone());
        return employee;
    }

    // Entity --> Response (Database --> Client)
    public EmployeeResponse toResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .username(employee.getUsername())
                .fullName(employee.getFullName())
                .role(employee.getRole())
                .phone(employee.getPhone())
                .build();
    }
}
