package com.vn.pos.dto.EmployeeDTO;

import com.vn.pos.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponse {
    private Integer id;
    private String username;
    private String fullName;
    private Employee.EmployeeRole role;
    private String phone;
    private Date createdAt;
}
