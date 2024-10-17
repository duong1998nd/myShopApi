package com.example.shop.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.shop.model.User;


public interface AccountRepository extends CrudRepository<User, Long>{

	@Query("SELECT u FROM User u WHERE u.username = :username")
	public User getUserByUsername(@Param("username") String username);


	User findByUsername(String username);
	
}
