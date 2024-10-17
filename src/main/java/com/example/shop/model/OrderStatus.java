package com.example.shop.model;

public enum OrderStatus {
    PENDING,  
    SHIPPED,
    DELIVERED,
    CANCELED;

    @Override
    public String toString() {
        switch (this) {
            case PENDING: return "Pending";
            case SHIPPED: return "Shipped";
            case DELIVERED: return "Delivered";
            case CANCELED: return "CANCELED";
            default: throw new IllegalArgumentException();
        }
    }
}
