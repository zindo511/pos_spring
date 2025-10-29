package com.vn.pos.service.Impl;

import com.vn.pos.dto.ProductDTO.ProductRequest;
import com.vn.pos.dto.ProductDTO.ProductResponse;
import com.vn.pos.dto.ProductDTO.ProductUpdateRequest;
import com.vn.pos.exception.Custom.InvalidOperationException;
import com.vn.pos.exception.Custom.ResourceNotFoundException;
import com.vn.pos.mapper.ProductMapper;
import com.vn.pos.model.Category;
import com.vn.pos.model.Product;
import com.vn.pos.repository.CategoryRepository;
import com.vn.pos.repository.ProductRepository;
import com.vn.pos.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        log.debug("Creating product with name: {}", request.getName());
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new InvalidOperationException("Category not found with id " + request.getCategoryId()));

        Product product = productMapper.toEntity(request, category);
        Product saved = productRepository.save(product);

        log.info("Product created successfully with id: {}", saved.getId());
        return productMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        log.debug("Fetching all products");
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Integer id) {
        log.debug("Fetching product with id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource", "id", id));
        return productMapper.toResponse(product);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(ProductUpdateRequest request) {
        Product product = productRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Resource", "id", request.getId()));

        Category category = request.getCategoryId() != null ?
                categoryRepository.findById(request.getCategoryId())
                                .orElseThrow(() -> new ResourceNotFoundException("Resource", "Category", request.getCategoryId()))
                : product.getCategory();

        product.setName(request.getName());
        product.setCategory(category);
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setImagePath(request.getImagePath());

        Product saved = productRepository.save(product);
        return productMapper.toResponse(saved);
    }

    @Override
    public void deleteProductById(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductResponse> findByName(String name) {
        List<Product> products = productRepository.findProductsByName(name);
        if (products.isEmpty()) {
            throw new RuntimeException("Product not found with: " + name);
        }
        return products.stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> findByCategoryId(Integer categoryId) {
        List<Product> products =  productRepository.findByCategory_Id(categoryId);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("Product", "Category", categoryId);
        }
        return products.stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> findByPriceBetween(Double min, Double max) {
        List<Product> products = productRepository.findByPriceBetween(min, max);
        if (products.isEmpty()) {
            throw new InvalidOperationException("Product not found with between: " + min + " and " + max);
        }
        return products.stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findByStockLessThan(Integer quantity) {
        List<Product> products = productRepository.findByStockLessThan(quantity);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("Product", "Stock", quantity);
        }
        return products.stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByNameContainingIgnoreCase(name);
    }

    @Override
    @Transactional
    public ProductResponse updateStock(Integer productId, Integer newStock) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with: " + productId));
        product.setStock(newStock);
        Product saved = productRepository.save(product);
        return productMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public ProductResponse increaseStock(Integer productId, Integer amount) {
        if (!productRepository.existsById(productId)) {
            throw new RuntimeException("Product not found with: " + productId);
        }
        Product product =  productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with: " + productId));
        product.setStock(product.getStock() + amount);
        Product saved = productRepository.save(product);
        return productMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public ProductResponse decreaseStock(Integer productId, Integer amount) {
        Product product =  productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with: " + productId));

        if (product.getStock() < amount) {
            throw new RuntimeException("Product stock less than: " + amount);
        }

        product.setStock(product.getStock() - amount);
        Product saved = productRepository.save(product);
        return productMapper.toResponse(saved);
    }

    public List<ProductResponse> findProductsAvailable() {
        List<Product> products = productRepository.findProductsAvailable();
        if (products.isEmpty()) {
            throw new RuntimeException("There are no products in stock.");
        }
        return products.stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }
}
