package com.example.shop.userController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.shop.dto.ProductDTO;
import com.example.shop.model.Favorites;
import com.example.shop.service.FavoriteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import myUserDetails.MyUserDetails;

@CrossOrigin(origins = "http://localhost:59464", maxAge = 3600)
@RestController
@RequestMapping("/api/likes")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

	@Operation(security = {@SecurityRequirement(name = "BearerJWT")})
    @PostMapping("/add")
    public ResponseEntity<?> addLike(@RequestParam Long productId) {
        MyUserDetails user = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = user.getId();

        Favorites like = favoriteService.addLike(userId, productId);
        return ResponseEntity.ok(like);
    }

	@Operation(security = {@SecurityRequirement(name = "BearerJWT")})
    @DeleteMapping("/remove")
    public ResponseEntity<?> removeLike(@RequestParam Long productId) {
        MyUserDetails user = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = user.getId();

        favoriteService.removeLike(userId, productId);
        return ResponseEntity.ok("Like removed");
    }

	@Operation(security = {@SecurityRequirement(name = "BearerJWT")})
    @GetMapping("/isLiked")
    public ResponseEntity<Boolean> isLikedByUser(@RequestParam Long productId) {
        MyUserDetails user = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        Long userId = user.getId();
        boolean isLiked = favoriteService.isLikedByUser(userId, productId);
        return ResponseEntity.ok(isLiked);
    }
	
	@Operation(security = {@SecurityRequirement(name = "BearerJWT")})
	@GetMapping("/user/liked-products")
	public ResponseEntity<List<ProductDTO>> getLikedProducts() {
	    MyUserDetails user = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    Long userId = user.getId();

	    List<ProductDTO> likedProducts = favoriteService.getLikedProductsByUserId(userId);
	    return ResponseEntity.ok(likedProducts);
	}

    @GetMapping("/count")
    public ResponseEntity<Long> getLikesCount(@RequestParam Long productId) {
        Long likeCount = favoriteService.getLikesCount(productId);
        return ResponseEntity.ok(likeCount);
    }
}



