package com.vn.pos.service;

import com.vn.pos.dto.CategoryDTO.CategoryRequest;
import com.vn.pos.dto.CategoryDTO.CategoryResponse;
import com.vn.pos.dto.CategoryDTO.CategoryUpdateRequest;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest requestDTO);
    List<CategoryResponse> getAllCategories();
    CategoryResponse getCategoryById(Integer id);
    CategoryResponse updateCategory(CategoryUpdateRequest requestDTO);
    void deleteCategoryById(Integer id);

    List<CategoryResponse> findByName(String name);
    boolean existsByName(String name);
}
