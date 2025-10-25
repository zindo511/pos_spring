package com.vn.pos.repository;

import com.vn.pos.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findProductsByName(String name);

    List<Product> findByCategory_Id(Integer categoryId);

    List<Product> findByPriceBetween(Double min, Double max);

    List<Product> findByStockLessThan(Integer quantity);


    @Query("SELECT p FROM Product p WHERE p.stock > 0")
    List<Product> findProductsAvailable();

    boolean existsByNameContainingIgnoreCase(String name);
}
