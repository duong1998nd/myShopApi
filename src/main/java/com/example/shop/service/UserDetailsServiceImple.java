package com.example.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.example.shop.model.User;
import com.example.shop.repository.AccountRepository;

import myUserDetails.MyUserDetails;


@Repository
public class UserDetailsServiceImple implements UserDetailsService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = accountRepository.getUserByUsername(username);

		if(user == null) {
			throw new UsernameNotFoundException("khong tim thay user");
		}
		return new MyUserDetails(user);
	}

}

