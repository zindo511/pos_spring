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

    @NotNull(message = "role must be not null")
    private Employee.EmployeeRole role;

    @NotNull(message = "phone must be not null")
    private String phone;
}
