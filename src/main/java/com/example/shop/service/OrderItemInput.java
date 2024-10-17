package com.example.shop.service;


import com.example.shop.model.Order;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class OrderItemInput {
    private Long productId;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

    // Getters and Setters
	
    
}

