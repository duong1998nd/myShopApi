package com.example.shop.userController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop.model.Cart;
import com.example.shop.model.User;
import com.example.shop.securityConfig.PasswordGenerator;
import com.example.shop.service.AccountService;
import com.example.shop.service.CartService;

@CrossOrigin(origins = "http://localhost:59464", maxAge = 3600)
@RestController
@RequestMapping("api")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private CartService cartService;

	@GetMapping("/account/username={username}")
	public User findByName(@PathVariable("username") String username){
		User acc = accountService.find(username);
		return acc;
	}
	
	@PostMapping("/account/register")
	public String addAccount(
			@RequestParam("fullname") String fullname,
			@RequestParam("email") String email,
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("phone") String phone
	) {
		System.out.println(fullname);
		System.out.println(email);
		System.out.println(username);
		System.out.println(password);
		System.out.println(phone);
		User acc = new User(fullname,email,username,password,phone);
		acc.setPassword(new PasswordGenerator().bcryptPassword(password));
		try {
			accountService.addUser(acc);
			User user = accountService.findByUsername(username);
			Cart cart = new Cart();
			cart.setUser(user);
			cartService.save(cart);
			return "thêm mới thành công";
		}catch (Exception e){
			e.printStackTrace();
			return "thêm mới thất bại";
		}
	}
}