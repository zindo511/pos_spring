package com.vn.pos.dto.ProductDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotNull(message = "Product name is required")
    @Size(max = 100, message = "Product name must not exceed 100 characters")
    private String name;

    @NotNull(message = "Product name must not exceed 100 characters")
    private Integer categoryId;

    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "Price must be greater than or equal to 0")
    private Double price;

    @NotNull(message = "Stock quantity is required")
    @PositiveOrZero(message = "The stock quantity must be greater than or equal to 0")
    private Integer stock;

    @Size(max = 255, message = "Image path must not exceed 255 characters")
    private String imagePath;
}
