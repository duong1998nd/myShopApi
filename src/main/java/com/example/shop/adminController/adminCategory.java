package com.example.shop.adminController;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop.model.Category;
import com.example.shop.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/category")
public class adminCategory {

	@Autowired
	private CategoryService categoryService;

	@Operation(security = {@SecurityRequirement(name = "BearerJWT")})
	@GetMapping("")
	public Iterable<Category> listCategory(){
		return categoryService.findAll();
	}
	
	@Operation(security = {@SecurityRequirement(name = "BearerJWT")})
	@GetMapping(value = "/details")
	public Optional<Category> findById(@RequestParam("id") Long id) {
		return categoryService.findById(id);
	}

	@Operation(security = {@SecurityRequirement(name = "BearerJWT")})
	@PostMapping("")
	public String insertCategory(@Valid @RequestBody Category cat) {
		if(categoryService.save(cat) != null) {
			return "thêm mới thành công";
		}
		return "thêm mới thất bại";
	}
	@Operation(security = {@SecurityRequirement(name = "BearerJWT")})
	@PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @Valid @RequestBody Category categoryDetails) {
        Optional<Category> optionalCategory = categoryService.findById(id);
        
        if (!optionalCategory.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Category categoryToUpdate = optionalCategory.get();
        categoryToUpdate.setName(categoryDetails.getName());
        categoryToUpdate.setStatus(categoryDetails.getStatus());
        
        Category updatedCategory = categoryService.save(categoryToUpdate);
        
        return ResponseEntity.ok(updatedCategory);
    }

	@Operation(security = {@SecurityRequirement(name = "BearerJWT")})
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}