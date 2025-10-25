package com.vn.pos.service;

import com.vn.pos.dto.ProductDTO.ProductRequest;
import com.vn.pos.dto.ProductDTO.ProductResponse;
import com.vn.pos.dto.ProductDTO.ProductUpdateRequest;
import com.vn.pos.model.Product;

import java.util.List;

public interface ProductService {

    // CRUD
    ProductResponse createProduct(ProductRequest request);
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(Integer id);
    ProductResponse updateProduct(ProductUpdateRequest request);
    void deleteProductById(Integer id);

    // Search & Filter
    List<ProductResponse> findByName(String name);
    List<ProductResponse> findByCategoryId(Integer categoryId);
    List<ProductResponse> findByPriceBetween(Double min, Double max);
    List<ProductResponse> findByStockLessThan(Integer quantity);

    // Inventory checking and handling
    boolean existsByName(String name);
    ProductResponse updateStock(Integer productId, Integer newStock);
    ProductResponse increaseStock(Integer productId, Integer amount);
    ProductResponse decreaseStock(Integer productId, Integer amount);

    // List of products in stock
    List<ProductResponse> findProductsAvailable();
}
