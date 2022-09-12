package com.ecommerce.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.model.Address;
import com.ecommerce.model.Orders;
import com.ecommerce.model.Product;
import com.ecommerce.model.ProductCategory;
import com.ecommerce.model.UserCart;
import com.ecommerce.repo.AddressRepo;
import com.ecommerce.repo.ProductCategoryRepo;
import com.ecommerce.repo.ProductRepo;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	
	@Mock	
	private ProductRepo repo;
	private ProductService service;
	private Product p;
	
	long id = 1;
	private BigDecimal price= new BigDecimal(10000);
	private BigDecimal weigth = new BigDecimal(4);
	long quantity = 1000;
	long rating = 5;
	List<Orders> o = new ArrayList<>();
	ProductCategory pc = new ProductCategory();
	List<UserCart> u = new ArrayList<>();

	
	@BeforeEach
	void setUp() throws Exception {
		service = new ProductService();
		p = new Product(id,"Lenovo Laptop","Legion 5 latop",price,weigth, quantity,"sample URL",rating,o,pc,u);
	}

	@Test
	void testGetAllProducts() {
		service.getAllProducts();
		verify(repo).findAll();
	}

	@Test
	
	void testGetProductById() {
		when(repo.findById(p.getProductId())).thenReturn(Optional.of(p));
		assertThat(service.getProductById(p.getProductId())).isEqualTo(p);
	}

	@Test
	
	void testAddProduct() {
		when(repo.save(p)).thenReturn(p);
		assertEquals(1, repo.count());
	}

	@Test
	
	void testUpdateProduct() {
		String description = "new_description";
		p.setDescription(description);
		when(repo.save(p)).thenReturn(p);
		when(repo.findById(p.getProductId())).thenReturn(Optional.of(p));
		assertThat(service.updateProduct(p)).isEqualTo(p);
		verify(repo).findById(p.getProductId()); 
	}

	@Test

	void testDeleteProductById() {
		long id = 1;
		service.deleteProductById(id);
		verify(repo).deleteById(id);
	}



}
