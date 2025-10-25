package com.vn.pos.dto.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequest {private Integer id;
    private String name;
    private Integer categoryId;
    private Double price;
    private Integer stock;
    private String imagePath;
}
