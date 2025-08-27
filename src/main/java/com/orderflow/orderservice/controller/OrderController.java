package com.orderflow.orderservice.controller;

import com.orderflow.orderservice.domain.Order;
import com.orderflow.orderservice.model.OrderRequest;
import com.orderflow.orderservice.model.OrderResponse;
import com.orderflow.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        Order order = Order.builder()
                .customerName(orderRequest.customerName())
                .product(orderRequest.product())
                .quantity(orderRequest.quantity())
                .build();

        Order savedOrder = orderService.createOrder(order);

        OrderResponse response = new OrderResponse(
                savedOrder.getProductId(), savedOrder.getCustomerName(), savedOrder.getProduct(),
                savedOrder.getQuantity(), savedOrder.getStatus(), savedOrder.getCreatedAt()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        Order order = orderService.getOrder(id);

        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new OrderResponse(
                order.getProductId(), order.getCustomerName(), order.getProduct(),
                order.getQuantity(), order.getStatus(), order.getCreatedAt()
        ));
    }

    @GetMapping
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders().stream()
                .map(order -> new OrderResponse(
                        order.getProductId(), order.getCustomerName(), order.getProduct(),
                        order.getQuantity(), order.getStatus(), order.getCreatedAt()
                )).toList();
    }
}
