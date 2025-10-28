package com.vn.pos.controller;

import com.vn.pos.dto.CategoryDTO.CategoryRequest;
import com.vn.pos.dto.CategoryDTO.CategoryResponse;
import com.vn.pos.dto.CategoryDTO.CategoryUpdateRequest;
import com.vn.pos.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @Valid @RequestBody CategoryRequest requestDTO) {
        CategoryResponse responseDTO = categoryService.createCategory(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> responseDTOS = categoryService.getAllCategories();
        return ResponseEntity.ok(responseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Integer id) {
        CategoryResponse responseDTO = categoryService.getCategoryById(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CategoryResponse> updateCategory(
            @Valid @RequestBody CategoryUpdateRequest request) {
        CategoryResponse responseDTO = categoryService.updateCategory(request);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Integer id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<CategoryResponse>> findByName(@RequestParam String name) {
        List<CategoryResponse> responseDTO = categoryService.findByName(name);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsByName(@RequestParam String name) {
        boolean exists = categoryService.existsByName(name);
        return ResponseEntity.ok(exists);
    }
}
