package com.orderflow.orderservice.events.consumer;

import com.orderflow.orderservice.commons.InventoryRejectedEvent;
import com.orderflow.orderservice.commons.InventoryReservedEvent;
import com.orderflow.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventsConsumer {
    private final OrderService orderService;

    @KafkaListener(topics = "inventory.reserved", groupId = "order-service")
    public void onInventoryReserved(InventoryReservedEvent event) {
        orderService.markAsConfirmed(event.orderId());
    }

    @KafkaListener(topics = "inventory.rejected", groupId = "order-service")
    public void onInventoryRejected(InventoryRejectedEvent event) {
        orderService.markAsCancelled(event.orderId(), event.reason());
    }
}
