package com.shop.ease.product_service.repository;

import com.shop.ease.product_service.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
   Page<Product> findAll(Specification<Product> spec, Pageable pageable);

   Page<Product> findByNameContainingIgnoreCase(String query,Pageable pageable);

}
