package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.model.Product;
import com.ecommerce.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("/{id}")
	public Product getproduct(@PathVariable Long id) {
		return productService.getProductById(id);
	}
	
	@GetMapping()
	public List<Product> getproducts() {
		return productService.getAllProducts();
	}
	
	@PostMapping("/add")
	public void addproduct(@RequestBody Product product) {
		productService.addProduct(product);
	}
	
	@PutMapping("/update")
	public void updateproduct(@RequestBody Product product) {
		productService.updateProduct(product);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteproduct(@PathVariable Long id) {
		productService.deleteProduct(id);
	}
}
