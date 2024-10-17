package com.example.shop.userController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop.model.Cart;
import com.example.shop.model.CartItem;
import com.example.shop.model.Product;
import com.example.shop.securityConfig.JwtUtils;
import com.example.shop.service.CartItemService;
import com.example.shop.service.CartService;
import com.example.shop.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import myUserDetails.MyUserDetails;

@RestController
@RequestMapping("api/web/cart")
public class CartController {


	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private CartService cartService;

	@Autowired
	ProductService productService;

	@Autowired
	JwtUtils userJwt;

	@Operation(security = {@SecurityRequirement(name = "BearerJWT")})
	@PostMapping("/create")
	public ResponseEntity<String> createNewCart() {
	    MyUserDetails user = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	    if (cartService.findByUserId(user.getId()) != null) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Đã tạo");
	    }

	    // Create a new cart for the user
	    Cart newCart = new Cart();
	    newCart.setUser(user.getUser());
	    cartService.save(newCart);

	    return ResponseEntity.status(HttpStatus.CREATED).body("Tạo thành công");
	}

	@Operation(security = {@SecurityRequirement(name = "BearerJWT")})
	@GetMapping("/list")
	public Iterable<CartItem> findAll(){
		MyUserDetails myUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return cartItemService.findAllByCartId(myUser);
	}

	@Operation(security = {@SecurityRequirement(name = "BearerJWT")})
	@PostMapping("/items")
	public CartItem addItem(
			@RequestParam("productId") Long proId,
			@RequestParam("quantity") int quantity
			) {
			MyUserDetails user = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Product pro = productService.findById(proId).orElse(null);
			CartItem cartItem = new CartItem(cartService.findByUserId(user.getId()), pro, quantity);
			return cartItemService.save(cartItem);
	}

	@Operation(security = {@SecurityRequirement(name = "BearerJWT")})
	@PutMapping("/items/{id}")
	public String updateCart(@PathVariable Long id,@RequestParam("quantity") int quantity) {
		boolean bl = cartItemService.updateQuantityProductInCart(id,quantity);
		if(bl){
			return "số lượng sản phẩm đã được cập nhật";
		}
		return "đã có lỗi xảy ra không thể cập nhật số lượng";
	}

	@Operation(security = {@SecurityRequirement(name = "BearerJWT")})
	@DeleteMapping("/items/{id}")
	public String deleteItem(@PathVariable("id") Long id) {
		try {
			cartItemService.deleteById(id);
			return "success";
		}catch (Exception e){
			e.printStackTrace();
			return "error";
		}
	}

}