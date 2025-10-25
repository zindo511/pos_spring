package com.vn.pos.dto.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Integer id;
    private String name;
    private Integer categoryId;
    private Double price;
    private Integer stock;
    private String imagePath;
    private Date createdAt;
}
