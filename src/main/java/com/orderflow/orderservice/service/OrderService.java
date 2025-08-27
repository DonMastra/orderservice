package com.orderflow.orderservice.service;

import com.orderflow.orderservice.domain.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    Order getOrder(Long id);
    List<Order> getAllOrders();
}
