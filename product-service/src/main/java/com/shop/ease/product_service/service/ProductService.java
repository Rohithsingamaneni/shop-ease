package com.shop.ease.product_service.service;

import com.shop.ease.product_service.dto.ProductDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Page<ProductDto> getAllProducts(Pageable pageable);

    ProductDto getProductById(Long id);

    ProductDto saveProduct(@Valid ProductDto productDto);

    ProductDto updateProduct(Long id, @Valid ProductDto productDto);

    void deleteProduct(Long id);

    Page<ProductDto> searchProducts(String query, Pageable pageable);
}
