package com.example.shop.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shop.model.Category;
import com.example.shop.model.Product;
import com.example.shop.repository.CategoryRepository;
import com.example.shop.repository.ProductRepository;


@Service
public class ProductService{

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CategoryRepository categoryRepository;

	public Product save(Product entity) {
		if(entity.getId()!=null){
			Product pro = productRepository.findById(entity.getId()).orElse(null);
			if(pro!=null){
				Category category = categoryRepository.findById(entity.getCategory_id()).orElse(null);
				if(category!=null){
					pro.setName(entity.getName());
					pro.setImage(entity.getImage());
					pro.setAuthor(entity.getAuthor());
					pro.setDescription(entity.getDescription());
					pro.setPrice(entity.getPrice());
					pro.setSale_price(entity.getSale_price());
					pro.setCategory_id(category);
					System.out.printf("sadsa", pro.getImage());
					return productRepository.save(pro);
				}
			}
		}
		return productRepository.save(entity);
	}


	public Optional<Product> findById(Long id) {
		Optional<Product> product = productRepository.findById(id);
        if(!product.isPresent()) {
            return null;
        }
		return product;
	}

	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public List<Product> findAllByCategoryId(Long categoryId){
		return productRepository.findAllByCategory_id(categoryId);
	}

	public void deleteById(Long id) {
		productRepository.deleteById(id);
	}
}

