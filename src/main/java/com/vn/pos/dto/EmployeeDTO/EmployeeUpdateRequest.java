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
public class EmployeeUpdateRequest {

    @NotNull
    private Integer id;

    @Size(min = 1, max = 50)
    private String username;

    @NotBlank(message = "fullName must be not blank")
    private String fullName;

    @Pattern(regexp = "ADMIN|CASHIER")
    private Employee.EmployeeRole role;

    @Pattern(regexp = "^[0-9]{10,11}$")
    private String phone;
}
