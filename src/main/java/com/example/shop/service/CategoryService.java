package com.example.shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.example.shop.model.Category;
import com.example.shop.repository.CategoryRepository;


@Service
public class CategoryService implements CrudRepository<Category, Long> {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public <S extends Category> S save(S entity) {
		if(entity.getId()!=null){
			Category cat = categoryRepository.findById(entity.getId()).orElse(null);
			if(cat!=null){
				cat.setName(entity.getName());
				cat.setStatus(entity.getStatus());
				return (S) categoryRepository.save(cat);
			}
		}
		return categoryRepository.save(entity);
	}

	@Override
	public <S extends Category> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean existsById(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Category> findAll() {
		List<Category> cats = categoryRepository.findAll();
		return cats;
	}
	
	public void deleteCategory(Long categoryId) {
        if (categoryRepository.existsById(categoryId)) {
            categoryRepository.deleteById(categoryId);
        } else {
            throw new RuntimeException("Category not found with id: " + categoryId);
        }
    }


	@Override
	public Optional<Category> findById(Long id) {
		Optional<Category> cat = categoryRepository.findById(id);
		return cat;
	}
	

	@Override
	public Iterable<Category> findAllById(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(Long id) {
		categoryRepository.deleteById(id);
	}

	@Override
	public void delete(Category entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll(Iterable<? extends Category> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}


}
