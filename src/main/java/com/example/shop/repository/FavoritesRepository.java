package com.example.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.shop.model.Favorites;
import com.example.shop.model.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoritesRepository extends JpaRepository<Favorites, Long> {
    List<Favorites> findAllByUserId(Long userId);
    Optional<Favorites> findByUserIdAndProductId(Long userId, Long productId);
    void deleteByUserIdAndProductId(Long userId, Long productId);
    Long countByProductId(Long productId);
    List<Favorites> findAllByUserIdAndProductIdIn(Long userId, List<Long> productIds);
    
    @Query("SELECT p FROM Product p JOIN Favorites l ON p.id = l.productId WHERE l.userId = :userId")
    List<Product> findLikedProductsByUserId(@Param("userId") Long userId);
}

