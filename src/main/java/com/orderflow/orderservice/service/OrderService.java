package com.orderflow.orderservice.service;

import com.orderflow.orderservice.domain.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    Order getOrder(Long id);
    List<Order> getAllOrders();
    void markAsConfirmed(Long orderId);
    void markAsCancelled(Long orderId, String reason);
}
