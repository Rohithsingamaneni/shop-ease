package com.shop.ease.product_service.model;

import com.shop.ease.product_service.model.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name cannot be blank")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @PositiveOrZero(message = "Price must be zero or positive")
    private double price;

    @PositiveOrZero(message = "Stock must be zero or positive")
    @Column(name = "stock_quantity")
    private int stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private String mediaUrl;
}
