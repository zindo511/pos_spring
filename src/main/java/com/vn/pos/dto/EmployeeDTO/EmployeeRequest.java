package com.vn.pos.dto.EmployeeDTO;

import com.vn.pos.model.Employee;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {

    @NotBlank(message = "username must be not blank")
    @Size(min = 3, max = 50, message = "Username must be between 3-50 characters")
    private String username;

    @NotBlank(message = "password must be not blank")
    @Size(min = 2, max = 100, message = "Password must be 8-100 characters")
    private String password;

    @NotBlank(message = "fullName must be not blank")
    @Size(min = 2, max = 100, message = "Full name must be between 2-100 characters")
    private String fullName;

    @NotNull(message = "role must be not null")
    private Employee.EmployeeRole role;

    @NotBlank(message = "phone must be not blank")
    private String phone;
}
