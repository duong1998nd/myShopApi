package com.example.shop.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.example.shop.model.Cart;
import com.example.shop.model.CartItem;

import com.example.shop.repository.CartItemRepository;

import myUserDetails.MyUserDetails;
@Service
public class CartItemService implements CrudRepository<CartItem, Long>{

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private  CartService cartService;

	@Override
	public <S extends CartItem> S save(S entity) {
		try {
			CartItem findByCartIdAndProductId = findByCartIdAndProductId(entity.getCart(),entity.getProduct());
			if(findByCartIdAndProductId!=null){
				findByCartIdAndProductId.setQuantity(findByCartIdAndProductId.getQuantity()+entity.getQuantity());
				return (S) cartItemRepository.save(findByCartIdAndProductId);
			}else{
				return cartItemRepository.save(entity);
			}
		}catch (Exception e){
			return null;
		}
	}

	public CartItem findByCartIdAndProductId(Long cartId, Long proId){
		CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cartId,proId);
		if(cartItem!=null){
			return cartItem;
		}
		return null;
	}

	public boolean updateQuantityProductInCart(Long id,int quantity){
		CartItem cartItem = cartItemRepository.findById(id).orElse(null);
		if(cartItem!=null){
			cartItem.setQuantity(quantity);
			return cartItemRepository.save(cartItem)!=null?true:false;
		}
		return false;
	}

	@Override
	public <S extends CartItem> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<CartItem> findById(Long id) {
		return cartItemRepository.findById(id);
	}

	@Override
	public boolean existsById(Long id) {
		return false;
	}

	@Override
	public Iterable<CartItem> findAll() {
		return cartItemRepository.findAll();
	}

	public Iterable<CartItem> findAllByCartId(MyUserDetails myUser) {
		 Cart cart = cartService.findByUserId(myUser.getId());
		System.out.println(myUser.getId());
		return cartItemRepository.findAllByCartId(cart.getId());
	}

	@Override
	public Iterable<CartItem> findAllById(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(Long id) {
		cartItemRepository.deleteById(id);
	}

	@Override
	public void delete(CartItem entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll(Iterable<? extends CartItem> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}


}
