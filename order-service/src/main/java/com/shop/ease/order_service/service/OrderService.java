package com.shop.ease.order_service.service;

import com.shop.ease.order_service.model.Order;
import com.shop.ease.order_service.model.OrderStatus;
import com.shop.ease.order_service.repository.OrderRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository=orderRepository;
    }

    public Order createOrder(@Valid Order order) {
        return orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).get();
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order updateOrder(Long id, String status) {
        Order order=orderRepository.findById(id).get();
        order.setOrderStatus(OrderStatus.valueOf(status.toUpperCase()));

        return orderRepository.save(order);

//        nOrder.setProductIds();
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
