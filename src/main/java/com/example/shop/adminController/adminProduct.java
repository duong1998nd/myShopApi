package com.example.shop.adminController;


import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.shop.model.Category;
import com.example.shop.model.Product;
import com.example.shop.service.CategoryService;
import com.example.shop.service.ProductService;
import com.example.shop.service.StorageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("api/admin/product")
public class adminProduct {

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private StorageService service;
	
	@Operation(security = {@SecurityRequirement(name = "BearerJWT")})
	@RequestMapping("")
	public List<Product> findAll() {
		List<Product> products = productService.findAll();
		return products;
	}

	@Operation(security = {@SecurityRequirement(name = "BearerJWT")})
	@GetMapping(value = "/details")
	public Optional<Product> findById(@RequestParam("id") Long id) {
		return productService.findById(id);
	}

	@Operation(
			security = {@SecurityRequirement(name = "BearerJWT")}
	)
	@PostMapping(value = "",consumes = {
			"multipart/form-data"
	})
	public ResponseEntity<String> insertProduct(@RequestParam("image") MultipartFile file,
			@RequestParam("name") String name,
			@RequestParam("price") Float price,
			@RequestParam("sale_price") Float sale_price,
			@RequestParam("desciption") String desc,
			@RequestParam("author") String author,
			@RequestParam("category_id") Long category_id) {
try {
// Validate inputs
if (file == null || file.isEmpty()) {
return new ResponseEntity<>("File is required", HttpStatus.BAD_REQUEST);
}
if (category_id == null) {
return new ResponseEntity<>("Category ID is required", HttpStatus.BAD_REQUEST);
}

// Check if category exists
Category category = categoryService.findById(category_id).orElse(null);
if (category == null) {
return new ResponseEntity<>("Category not found", HttpStatus.NOT_FOUND);
}

// Handle image upload
String uploadImage = service.uploadImage(file);
if (uploadImage == null) {
return new ResponseEntity<>("Image upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
}

// Create and save product
Product pro = new Product(name, file.getOriginalFilename(), price, sale_price, author, desc, category);
Product savedProduct = productService.save(pro);
if (savedProduct == null) {
return new ResponseEntity<>("Product creation failed", HttpStatus.INTERNAL_SERVER_ERROR);
}

return new ResponseEntity<>("Product added successfully", HttpStatus.OK);
} catch (Exception e) {
// Log the exception
e.printStackTrace();
return new ResponseEntity<>("Internal Server Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
}
}


	@Operation(security = {@SecurityRequirement(name = "BearerJWT")})
	@PutMapping(value = "",consumes = {
			"multipart/form-data"
	})
	public String updateProduct(@RequestParam("image") MultipartFile file,
								@RequestParam("id") Long id,
								@RequestParam("name") String name,
								@RequestParam("price") Float price,
								@RequestParam("sale_price") Float sale_price,
								@RequestParam("desciption") String desc,
								@RequestParam("author") String author,
								@RequestParam("category_id") Long category_id) throws IOException {
		Product pro = new Product(id,name,file.getOriginalFilename(),price,sale_price,author,desc,categoryService.findById(category_id).orElse(null));
		String uploadImage = service.uploadImage(file);
		if(uploadImage != null) {
			if(productService.save(pro)!=null){
				return "update thành công";
			}
			return "update thất bại";
		}
		return "update thất bại";
	}

	@Operation(security = {@SecurityRequirement(name = "BearerJWT")})
	@DeleteMapping("")
	public String deleteProduct(@RequestParam("id") Long id){
		Product proById = productService.findById(id).orElse(null);
		System.out.println(proById.getId());
		if(proById!=null){
			productService.deleteById(id);
			return "đã xóa sản phẩm có id="+id;
		}
		return "Không tìm thấy sản phẩm có id="+id;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();
		List<String> errors = bindingResult.getAllErrors().stream()
				.map(ObjectError::getDefaultMessage)
				.collect(Collectors.toList());
		return ResponseEntity.badRequest().body(String.join(", ", errors));
	}



	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	}
}