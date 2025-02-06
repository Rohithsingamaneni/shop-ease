package com.shop.ease.product_service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private String mediaUrl;
    private String categoryName;
}
