package com.vn.pos.repository;

import com.vn.pos.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByNameContainingIgnoreCase(String name);

    boolean existsByName(String name);
}
