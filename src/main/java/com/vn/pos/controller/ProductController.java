package com.vn.pos.controller;

import com.vn.pos.dto.ProductDTO.ProductRequest;
import com.vn.pos.dto.ProductDTO.ProductResponse;
import com.vn.pos.dto.ProductDTO.ProductUpdateRequest;
import com.vn.pos.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor

public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request) {
        ProductResponse response = productService.createProduct(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> responses = productService.getAllProducts();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Integer id) {
        ProductResponse response = productService.getProductById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ProductResponse> updateProduct(
            @RequestBody ProductUpdateRequest request) {
        ProductResponse response = productService.updateProduct(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Integer id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> findByName(@RequestParam String name) {
        List<ProductResponse> responses = productService.findByName(name);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponse>> findByCategoryId(@PathVariable Integer categoryId) {
        List<ProductResponse> responses = productService.findByCategoryId(categoryId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/statistics/between")
    public ResponseEntity<List<ProductResponse>> findByPriceBetween(@RequestParam Double min, @RequestParam Double max) {
        List<ProductResponse> responses = productService.findByPriceBetween(min, max);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/statistics/stock")
    public ResponseEntity<List<ProductResponse>> findByStockLessThan(@RequestParam Integer quantity) {
        List<ProductResponse> responses = productService.findByStockLessThan(quantity);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsByName(String name) {
        boolean exists = productService.existsByName(name);
        return ResponseEntity.ok(exists);
    }

    @PutMapping("/{productId}/newStock")
    public ResponseEntity<ProductResponse> updateStock(@PathVariable Integer productId, @RequestParam Integer newStock) {
        ProductResponse response = productService.updateStock(productId, newStock);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{productId}/increase")
    public ResponseEntity<ProductResponse> increaseStock(@PathVariable Integer productId, @RequestParam Integer amount) {
        ProductResponse response = productService.increaseStock(productId, amount);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{productId}/decrease")
    public ResponseEntity<ProductResponse> decreaseStock(@PathVariable Integer productId, @RequestParam Integer amount) {
        ProductResponse response = productService.decreaseStock(productId, amount);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/available")
    public ResponseEntity<List<ProductResponse>> findProductsAvailable() {
        List<ProductResponse> responses = productService.findProductsAvailable();
        return ResponseEntity.ok(responses);
    }
}
