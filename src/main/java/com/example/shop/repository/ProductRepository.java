package com.example.shop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shop.model.ImageData;
import com.example.shop.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

    List<Product> findAllByCategory_id(Long category_id);

    Optional<ImageData> findByName(String filename);
}
