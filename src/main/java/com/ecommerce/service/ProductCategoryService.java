package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.model.ProductCategory;
import com.ecommerce.repo.ProductCategoryRepo;

@Service
public class ProductCategoryService {
	
	@Autowired
	private ProductCategoryRepo categoryRepo;
	
	public List<ProductCategory> getAllCategory() {
		return categoryRepo.findAll();
	}
	
	public ProductCategory getCategoryById(Long id) {
		return categoryRepo.findById(id)
				.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Product category not found with id : "+id) );
	}
	
	public void addCategory(ProductCategory category) {
		categoryRepo.save(category);
	}
	
	public ProductCategory updateCategory(ProductCategory category) {
		categoryRepo
			.findById(category.getCategoryId())
			.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Product category not found with id : "+category.getCategoryId()) );
		return categoryRepo.save(category);
	}
	
	public void deleteCategory(Long id) {
		categoryRepo.deleteById(id);
	}

}
