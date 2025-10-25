package com.vn.pos.mapper;

import com.vn.pos.dto.CategoryDTO.CategoryRequest;
import com.vn.pos.dto.CategoryDTO.CategoryResponse;
import com.vn.pos.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    // Request --> Entity (Client --> Database)
    public Category toEntity(CategoryRequest requestDTO) {
        Category category = new Category();
        category.setName(requestDTO.getName());
        category.setDescription(requestDTO.getDescription());
        return category;
    }

    // Entity --> Response
    public CategoryResponse toResponseDTO(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .createdAt(category.getCreatedAt())
                .build();
    }

}
