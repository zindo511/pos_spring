package com.vn.pos.mapper;

import com.vn.pos.dto.ProductDTO.ProductRequest;
import com.vn.pos.dto.ProductDTO.ProductResponse;
import com.vn.pos.model.Category;
import com.vn.pos.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    // request --> entity (client --> database)
    public Product toEntity(ProductRequest request, Category category) {
        Product product = new Product();
        product.setName(request.getName());
        product.setCategory(category);
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setImagePath(request.getImagePath());
        return product;
    }

    // Entity --> response (database --> client)
    public ProductResponse toResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : null);
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());
        response.setImagePath(product.getImagePath());
        response.setImagePath(product.getImagePath());

        return response;
    }
}
