package com.orderflow.orderservice.commons;

public record InventoryRejectedEvent(Long orderId, String reason) {
}
