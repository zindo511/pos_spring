package com.vn.pos.service.Impl;

import com.vn.pos.exception.Custom.DuplicateResourceException;
import com.vn.pos.exception.Custom.ResourceNotFoundException;
import com.vn.pos.mapper.CategoryMapper;
import com.vn.pos.dto.CategoryDTO.CategoryRequest;
import com.vn.pos.dto.CategoryDTO.CategoryResponse;
import com.vn.pos.dto.CategoryDTO.CategoryUpdateRequest;
import com.vn.pos.model.Category;
import com.vn.pos.repository.CategoryRepository;
import com.vn.pos.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CategoryRequest requestDTO) {
        if (categoryRepository.existsByName(requestDTO.getName())) {
            throw new DuplicateResourceException("Resource", "name", requestDTO.getName());
        }
        Category category = categoryMapper.toEntity(requestDTO);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toResponseDTO(savedCategory);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategoryById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource", "id", id));
        return categoryMapper.toResponseDTO(category);
    }

    @Override
    public CategoryResponse updateCategory(CategoryUpdateRequest requestDTO) {
        Category existing = categoryRepository.findById(requestDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Resource", "id", requestDTO.getId()));

        // Update only non-null fields
        if (requestDTO.getName() != null) {
            existing.setName(requestDTO.getName());
        }
        if (requestDTO.getDescription() != null) {
            existing.setDescription(requestDTO.getDescription());
        }

        Category updatedCategory = categoryRepository.save(existing);
        return categoryMapper.toResponseDTO(updatedCategory);

    }

    @Override
    public void deleteCategoryById(Integer id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryResponse> findByName(String name) {
        List<Category> categories = categoryRepository.findByNameContainingIgnoreCase(name);
        if (categories.isEmpty()) {
            throw new ResourceNotFoundException("Resource", "name", name);
        }
        return categories.stream()
                .map(categoryMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }
}
