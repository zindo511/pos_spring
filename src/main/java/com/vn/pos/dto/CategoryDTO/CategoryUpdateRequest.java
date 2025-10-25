package com.vn.pos.dto.CategoryDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUpdateRequest {

    @NotNull(message = "ID cannot be null")
    private Integer id;

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @Size(max = 500, message = "Mô tả không được quá 500 ký tự")
    private String description;
}
