package com.orderflow.orderservice.model;

public record OrderRequest(String customerName, String product, int quantity) {
}
