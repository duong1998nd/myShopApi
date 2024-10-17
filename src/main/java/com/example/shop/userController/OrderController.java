package com.example.shop.userController;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop.dto.OrderDTO;
import com.example.shop.model.Order;
import com.example.shop.model.User;
import com.example.shop.service.OrderInput;
import com.example.shop.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import myUserDetails.MyUserDetails;

@CrossOrigin(origins = "http://localhost:59464", maxAge = 3600)
@RestController
@RequestMapping("api/order")
public class OrderController {

	@Autowired
    private OrderService orderService;

	@Operation(security = {@SecurityRequirement(name = "BearerJWT")})
    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderInput orderInput) {
    	MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	User user = userDetails.getUser();
        try {
        	Order order = orderService.createOrder(orderInput, user);
            return ResponseEntity.status(HttpStatus.OK).body("Tạo thành công");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
	
//	@Operation(security = {@SecurityRequirement(name = "BearerJWT")})
	@GetMapping("")
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        OrderDTO order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
	
	@GetMapping("/status/{status}")
    public List<OrderDTO> getOrdersByStatus(@PathVariable String status) {
        return orderService.getOrdersByStatus(status);
    }

	@Operation(security = {@SecurityRequirement(name = "BearerJWT")})
	@GetMapping("/myOder")
    public ResponseEntity<List<OrderDTO>> getOrdersByUserId() {
		
    	MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

   
    	Long userId = userDetails.getId();
        List<OrderDTO> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

	@PutMapping("/{orderId}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long orderId, @RequestBody Map<String, String> request) {
        String newStatus = request.get("status");
        OrderDTO updatedOrder = orderService.updateOrderStatus(orderId, newStatus);
        return ResponseEntity.ok(updatedOrder);
    }
//    @PutMapping("/order/update/{id}")
//    public ResponseEntity<?> updateOrderStatus(@PathVariable Long id, @RequestBody OrderStatusUpdateRequest request) {
//        try {
//            OrderEntity order = orderService.updateOrderStatus(id, request.getStatus());
//            return ResponseEntity.ok(new ApiResponse("Order status updated successfully", true));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                                 .body(new ApiResponse("Failed to update order status", false));
//        }
//    }
}
