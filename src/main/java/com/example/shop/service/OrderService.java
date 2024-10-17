package com.example.shop.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.example.shop.dto.OrderDTO;
import com.example.shop.dto.OrderItemDTO;
import com.example.shop.dto.UserDTO;
import com.example.shop.model.Order;
import com.example.shop.model.OrderItem;
import com.example.shop.model.OrderStatus;
import com.example.shop.model.Product;
import com.example.shop.model.User;
import com.example.shop.repository.OrderRepository;
import com.example.shop.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public Order createOrder(OrderInput orderInput, User user) throws Exception {
    	Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(orderInput.getShippingAddress());

        double totalPrice = 0.0;
        for (OrderItemInput itemInput : orderInput.getItems()) {
            Product product = productRepository.findById(itemInput.getProductId())
                    .orElseThrow(() -> new Exception("Product not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemInput.getQuantity());
            orderItem.setOrder(order);

            totalPrice += product.getSale_price() * itemInput.getQuantity();
            order.getItems().add(orderItem);  
        }

        order.setTotalPrice(totalPrice);
        return orderRepository.save(order);
    }
    
    
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(order -> mapToDTO(order)).collect(Collectors.toList());
    }
    
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow();
        return mapToDTO(order);
    }
    
    public List<OrderDTO> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                     .map(order -> mapToDTO(order)).collect(Collectors.toList());
    }


    
    public List<OrderDTO> getOrdersByStatus(String status) {
    	OrderStatus orderStatus;

        try {
            orderStatus = OrderStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid order status: " + status);
        }
        List<Order> orders = orderRepository.findByOrderStatus(orderStatus);
        return mapToDTOList(orders);
    }

    public OrderDTO updateOrderStatus(Long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow();

        order.setOrderStatus(OrderStatus.valueOf(newStatus));
        orderRepository.save(order);

        return mapToDTO(order);
    }
    
    public List<OrderDTO> mapToDTOList(List<Order> orders) {
        return orders.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setShippingAddress(order.getShippingAddress());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setOrderStatus(order.getOrderStatus().toString());
        UserDTO userDTO = new UserDTO();
        userDTO.setId(order.getUser().getId());
        userDTO.setFullname(order.getUser().getFullname());
        userDTO.setEmail(order.getUser().getEmail());
        dto.setUser(userDTO);
        dto.setItems(order.getItems().stream().map(this::mapToItemDTO).collect(Collectors.toList()));
        return dto;
    }
    
    private OrderDTO mapToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setShippingAddress(order.getShippingAddress());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setOrderStatus(order.getOrderStatus().toString());
        dto.setItems(order.getItems().stream().map(this::mapToItemDTO).collect(Collectors.toList()));
        
        UserDTO userDTO = new UserDTO();
        userDTO.setId(order.getUser().getId());
        userDTO.setFullname(order.getUser().getFullname());
        userDTO.setEmail(order.getUser().getEmail());
        dto.setUser(userDTO);

        return dto;
    }


    private OrderItemDTO mapToItemDTO(OrderItem item) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(item.getId());
        dto.setProductName(item.getProduct().getName());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getProduct().getPrice());
        return dto;
    }


	
}

