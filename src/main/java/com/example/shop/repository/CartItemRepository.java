package com.example.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shop.model.CartItem;


@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>{

	CartItem findByProductId(Long proId);

	CartItem findByCartId(Long cartId);

	CartItem findByCartIdAndProductId(Long cartId, Long proId);
    
    Iterable<CartItem> findAllByCartId(Long id);
}
