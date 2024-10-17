package com.example.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shop.dto.ProductDTO;
import com.example.shop.model.Favorites;
import com.example.shop.model.Product;
import com.example.shop.repository.FavoritesRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    @Autowired
    private FavoritesRepository favoriteRepository;

    // Add like for one product
    public Favorites addLike(Long userId, Long productId) {
        // Check if the product is already liked by the user
        Optional<Favorites> existingLike = favoriteRepository.findByUserIdAndProductId(userId, productId);
        if (existingLike.isEmpty()) {
        	Favorites like = new Favorites(userId, productId);
            return favoriteRepository.save(like);
        }
        // If the like already exists, return the existing like
        return existingLike.get();
    }

    // Remove like for one product
    public void removeLike(Long userId, Long productId) {
        Optional<Favorites> existingLike = favoriteRepository.findByUserIdAndProductId(userId, productId);
        existingLike.ifPresent(like -> favoriteRepository.delete(like));
    }

    // Check if a product is liked by a user
    public boolean isLikedByUser(Long userId, Long productId) {
        return favoriteRepository.findByUserIdAndProductId(userId, productId).isPresent();
    }

    // Get the like count for a product
    public Long getLikesCount(Long productId) {
        return favoriteRepository.countByProductId(productId);
    }
    
    public List<com.example.shop.dto.ProductDTO> getLikedProductsByUserId(Long userId) {
        List<Product> likedProducts = favoriteRepository.findLikedProductsByUserId(userId);
        
        // Convert the list of Product entities to ProductDTO
        return likedProducts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Helper method to map Product to ProductDTO
    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImage() // Assuming your Product entity has an image field
        );
    }

}


