package com.ecommerce.service;

import java.util.List;
import java.util.Optional;

import com.ecommerce.model.ProductCategory;
import com.ecommerce.repo.ProductCategoryRepo;

public class ProductCategoryService {
	
	private ProductCategoryRepo categoryRepo;
	
	public List<ProductCategory> getAllCategory() {
		return categoryRepo.findAll();
	}
	
	public Optional<ProductCategory> getCategoryById(Long id) {
		return categoryRepo.findById(id);
	}
	
	public void addCategory(ProductCategory category) {
		categoryRepo.save(category);
	}
	
	public void updateCategory(ProductCategory category, Long id) {
		if(categoryRepo.findById(id).isPresent()) {
			categoryRepo.save(category);
		}
		return;
	}
	
	public void deleteCategory(Long id) {
		categoryRepo.deleteById(id);
	}

}
