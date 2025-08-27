package com.orderflow.orderservice.commons;

public record OrderCreatedEvent(Long orderId, Long productId, int quantity) {
}
