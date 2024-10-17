//package com.bShop.service;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Service;
//
//import com.bShop.MyUserDetail.MyUserDetails;
//import com.bShop.model.CartEntity;
//import com.bShop.model.CartItemEntity;
//import com.bShop.model.OrderEntity;
//import com.bShop.model.OrderItemEntity;
//import com.bShop.repository.CartItemRepository;
//import com.bShop.repository.OrderItemRepository;
//
//@Service
//public class OrderItemService implements CrudRepository<OrderItemEntity, Long> {
//
//	@Autowired
//	private OrderItemRepository orderItemRepository;
//
//	@Autowired
//	private  OrderService orderService;
//	
//	public OrderItemEntity findByOrderIdAndProductId(Long orderId, Long proId){
//		OrderItemEntity orderItem = orderItemRepository.findByOrderIdAndProductId(orderId,proId);
//		if(orderItem!=null){
//			return orderItem;
//		}
//		return null;
//	}
//	
//	@Override
//	public <S extends OrderItemEntity> S save(S entity) {
//		try {
//			OrderItemEntity findByOrderIdAndProductId = findByOrderIdAndProductId(entity.getOrder(),entity.getProduct());
//			if(findByOrderIdAndProductId!=null){
//				findByOrderIdAndProductId.setQuantity(findByOrderIdAndProductId.getQuantity()+entity.getQuantity());
//				return (S) orderItemRepository.save(findByOrderIdAndProductId);
//			}else{
//				return orderItemRepository.save(entity);
//			}
//		}catch (Exception e){
//			return null;
//		}
//	}
//	
//	public Iterable<OrderItemEntity> findAllByOrderId(MyUserDetails myUser) {
//		 OrderEntity order = orderService.findByUserId(myUser.getId());
//		System.out.println(myUser.getId());
//		return orderItemRepository.findAllByOrderId(order.getId());
//	}
//
//	@Override
//	public <S extends OrderItemEntity> Iterable<S> saveAll(Iterable<S> entities) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Optional<OrderItemEntity> findById(Long id) {
//		// TODO Auto-generated method stub
//		return Optional.empty();
//	}
//
//	@Override
//	public boolean existsById(Long id) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public Iterable<OrderItemEntity> findAll() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Iterable<OrderItemEntity> findAllById(Iterable<Long> ids) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public long count() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public void deleteById(Long id) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void delete(OrderItemEntity entity) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void deleteAllById(Iterable<? extends Long> ids) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void deleteAll(Iterable<? extends OrderItemEntity> entities) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void deleteAll() {
//		// TODO Auto-generated method stub
//		
//	}
//
//}

