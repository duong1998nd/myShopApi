package com.example.shop.service;

import java.util.List;

public class OrderInput {
    private List<OrderItemInput> items;
    private String shippingAddress;
    private String status;
	public List<OrderItemInput> getItems() {
		return items;
	}
	public void setItems(List<OrderItemInput> items) {
		this.items = items;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

    // Getters and Setters
    
}

