package com.shop.ease.product_service.service;

import com.shop.ease.product_service.dto.ProductDto;
import com.shop.ease.product_service.model.Category;
import com.shop.ease.product_service.model.Product;
import com.shop.ease.product_service.repository.CategoryRepository;
import com.shop.ease.product_service.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository=productRepository;
        this.categoryRepository=categoryRepository;
    }

    @Override
    public Page<ProductDto> getAllProducts(Pageable pageable){
        return productRepository.findAll(pageable).map(this::convertToDto);
    }

    @Override
    public ProductDto getProductById(Long id){
//        Product product=productRepository.findById(id).
//                orElseThrow(()->new EntityNotFoundException("Product Id dosn't exist"));
//        return convertToDto(product);
        return productRepository.findById(id).map(this::convertToDto)
                .orElseThrow(()->new EntityNotFoundException("Product id not exist"));
    }

    @Override
    public ProductDto saveProduct(ProductDto productDto){
        Product product=convertToEntity(productDto);

        if(productDto.getCategoryName()!=null){
            Category category= categoryRepository.findByName(productDto.getCategoryName())
                    .orElseGet(()->{
                        Category newCategory=new Category();
                        newCategory.setName(productDto.getCategoryName());
                        return categoryRepository.save(newCategory);
                    });
            product.setCategory(category);

        }
       // return productRepository.save(product).map(this::convertToDto);
        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }

    @Override
    public ProductDto updateProduct(Long id,ProductDto productDto){
        Product existingProduct=productRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Not found"));
        existingProduct.setName(productDto.getName());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setStock(productDto.getStock());
        existingProduct.setMediaUrl(productDto.getMediaUrl());

        if(productDto.getCategoryName()!=null){
            Category category=categoryRepository.findByName(productDto.getCategoryName())
                    .orElseGet(()->{
                        Category newCategory=new Category();
                        newCategory.setName(productDto.getCategoryName());
                        return categoryRepository.save(newCategory);
                    });
            existingProduct.setCategory(category);
        }
        Product updatedProduct=productRepository.save(existingProduct);
        return convertToDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    @Override
    public Page<ProductDto> searchProducts(String query,Pageable pageable){
        return productRepository.findByNameContainingIgnoreCase(query,pageable).map(this::convertToDto);
    }


    private ProductDto convertToDto(Product product){
        ProductDto dto=new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setMediaUrl(product.getMediaUrl());
        if (product.getCategory() != null) {
            dto.setCategoryName(product.getCategory().getName());
        }
        return dto;
    }
    private Product convertToEntity(ProductDto dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setMediaUrl(dto.getMediaUrl());
        return product;
    }

}
