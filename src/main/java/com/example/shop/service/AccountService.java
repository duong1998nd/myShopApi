package com.example.shop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shop.model.User;


@Service
public class AccountService {

	@Autowired
	private com.example.shop.repository.AccountRepository AccountRepository;

	public List<User> getAllUsers(){
	    List<User>userRecords = new ArrayList<>();
	    AccountRepository.findAll().forEach(userRecords::add);
	    return userRecords;
	  }

	  public boolean addUser(User Account) {
		 if( AccountRepository.save(Account) != null) {
			 return true;
		 }else {
			 return false;
		 }
	  }

	  public User findByUsername(String username){
		  User user = AccountRepository.findByUsername(username);
		return user;
	  }
	  
	  public User find(String username){
		  User abc =  AccountRepository.getUserByUsername(username);
			return abc;
		}
}
