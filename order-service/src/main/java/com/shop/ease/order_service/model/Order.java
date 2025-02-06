package com.shop.ease.order_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User Id must be present")
    private Long userId;

    @ElementCollection
    private List<Long> productIds;

    @NotNull(message = "Total amount is required")
    private Double totalAmount;

    private LocalDateTime orderDate=LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus=OrderStatus.PENDING;
}
