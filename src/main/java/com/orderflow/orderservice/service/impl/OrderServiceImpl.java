package com.orderflow.orderservice.service.impl;

import com.orderflow.orderservice.commons.OrderCreatedEvent;
import com.orderflow.orderservice.domain.Order;
import com.orderflow.orderservice.domain.OrderStatus;
import com.orderflow.orderservice.events.producer.OrderEventsProducer;
import com.orderflow.orderservice.repository.OrderRepository;
import com.orderflow.orderservice.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventsProducer eventsProducer;

    public OrderServiceImpl(OrderRepository orderRepository, OrderEventsProducer eventsProducer) {
        this.orderRepository = orderRepository;
        this.eventsProducer = eventsProducer;
    }

    @Transactional
    @Override
    public Order createOrder(Order order) {
        orderRepository.save(order);
        eventsProducer.publishOrderCreated(new OrderCreatedEvent(
                order.getProductId(), order.getProductId(), order.getQuantity()
        ));
        return order;
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public void markAsConfirmed(Long orderId) {
        var order = orderRepository.findById(orderId);
        if (order == null) return; // lanzar excepción!
        if (order.getStatus() != OrderStatus.PENDING) return; // idempotencia básica
        order.setStatus(OrderStatus.CONFIRMED);
    }

    @Transactional
    public void markAsCancelled(Long orderId, String reason) {
        Order order = orderRepository.findById(orderId);
        if (order == null) return;
        if (order.getStatus() != OrderStatus.PENDING) return;
        order.setStatus(OrderStatus.CANCELLED);
        order.setCancelReason(reason);
    }
}
