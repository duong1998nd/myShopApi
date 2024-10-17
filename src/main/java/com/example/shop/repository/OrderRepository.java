package com.example.shop.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.shop.model.Order;
import com.example.shop.model.OrderStatus;


public interface OrderRepository extends CrudRepository<Order, Long> {
    
	@Override
	@Query("SELECT o FROM Order o")
	public List<Order> findAll();
	
	List<Order> findByOrderStatus(OrderStatus orderStatus);
	
	List<Order> findByUserId(Long userId);
}
