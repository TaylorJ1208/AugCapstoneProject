package com.ecommerce.service;

import java.util.List;
import java.util.Optional;

import com.ecommerce.model.Product;
import com.ecommerce.repo.ProductRepo;

public class ProductService {
	
	private ProductRepo productRepo;
	
	public List<Product> getAllProducts() {
		return productRepo.findAll();
	}
	
	public Optional<Product> getProductById(Long id) {
		return productRepo.findById(id);
	}
	
	public void addProduct(Product product) {
		productRepo.save(product);
	}

	public void updateProduct(Product product, Long id) {
		if(productRepo.findById(id).isPresent()) {
			productRepo.save(product);
		}
		return;
	}
	
	public void deleteProduct(Long id) {
		productRepo.deleteById(id);
	}
}
