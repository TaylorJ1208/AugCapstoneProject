package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.model.Product;
import com.ecommerce.repo.ProductRepo;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepo productRepo;
	
	public ProductService(ProductRepo repo) {
		this.productRepo = repo;
	}

	public List<Product> getAllProducts() {
		return productRepo.findAll();
	}
	
	public Product getProductById(Long id) {
		return productRepo.findById(id)
				.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found with id : "+id) );
	}
	
	public Product addProduct(Product product) {
		return productRepo.save(product);
	}

	public Product updateProduct(Product product) {
		productRepo
			.findById(product.getProductId())
			.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found with id : "+product.getProductId()) );
		return productRepo.save(product);
	}
	
	public void deleteProductById(Long id) {
		productRepo.deleteById(id);
	}
	
	public List<Product> getProductByName(String name) {
		return productRepo.getProductsByName(name);
	}
}
