package com.example.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.shop.model.Category;

import jakarta.transaction.Transactional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

	@Override
	@Query("SELECT c FROM Category c")
	public List<Category> findAll();

	@Query(value = "SELECT * FROM category WHERE id=:id", nativeQuery = true)
	public Category findById(@Param("id") int id);

	@Query(value = "INSERT INTO category (name, status) VALUES (:name, :status)", nativeQuery = true)
	@Modifying
	@Transactional
	public boolean insertCategory(@Param("name") String name, @Param("status") byte status);
}
