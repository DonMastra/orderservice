package com.orderflow.orderservice.model;

import com.orderflow.orderservice.domain.OrderStatus;

import java.time.LocalDateTime;

public record OrderResponse(Long id, String customerName, String product,
                            int quantity, OrderStatus status, LocalDateTime createdAt) {
}
