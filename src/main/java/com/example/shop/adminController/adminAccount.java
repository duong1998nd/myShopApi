package com.example.shop.adminController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop.model.User;
import com.example.shop.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("api/admin/account")
public class adminAccount {

	@Autowired
	private AccountService accountService;

	@Operation(security = {@SecurityRequirement(name = "BearerJWT")})
	@GetMapping(value = "/show")
	public List<User> findAll(){
		return accountService.getAllUsers();
	}

	@Operation(security = {@SecurityRequirement(name = "BearerJWT")})
	@GetMapping(value = "/login-admin?name=?")
	public User findById(@RequestParam("username") String username ) {
		return accountService.find(username);
	}

}