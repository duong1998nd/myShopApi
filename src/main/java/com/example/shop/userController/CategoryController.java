package com.example.shop.userController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop.model.Category;
import com.example.shop.service.CategoryService;


@RestController
@RequestMapping("api")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/category")
	public Iterable<Category> listCategory(){
		return categoryService.findAll();
	}

}