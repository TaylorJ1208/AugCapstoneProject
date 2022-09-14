package com.ecommerce.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.model.Orders;
import com.ecommerce.model.Product;
import com.ecommerce.model.ProductCategory;
import com.ecommerce.model.UserCart;
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
		service = new ProductService(repo);
		p = new Product(id,"Lenovo Laptop","Legion 5 latop",price,weigth, quantity,"sample URL",rating,o,pc,u);
	}

	@Test
	void testGetAllProducts() {
		service.getAllProducts();
		verify(repo).findAll();
	}

	@Test
	void testGetProductById() throws Exception {
		when(repo.findById(p.getProductId())).thenReturn(Optional.of(p));
		assertThat(service.getProductById(p.getProductId())).isEqualTo(p);
	}
	
	@Test
	void testGetProductByName() {
		List<Product> products = new ArrayList<>();
		products.add(p);
		when(repo.getProductsByName(p.getName())).thenReturn(products);
		assertThat(service.getProductByName(p.getName())).isEqualTo(products);
	}
	@Test
	void testAddProduct() {
		when(repo.save(p)).thenReturn(p);
		assertThat(service.addProduct(p)).isEqualTo(p);
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
	
	@Test
	void testProductEquals() {
		Product p1 = new Product(id,"Lenovo Laptop","Legion 5 latop",price,weigth, quantity,"sample URL",rating,o,pc,u);
		Product p2 = new Product(id,"wrong product name","Legion 5 latop",price,weigth, quantity,"sample URL",rating,o,pc,u);
		Product p3 = new Product(id,"Lenovo Laptop","Legion 5 latop",price,weigth, quantity,"sample URL",rating,o,pc,u);
		assertTrue(p1.equals(p3));
		assertFalse(p1.equals(p2));
		assertFalse(p1 == p3);
		p1 = p3;
		assertTrue(p1 == p3);
	}
	
	@Test
	void testProductHashCode() {
		Product p2 = new Product(id,"wrong product name","Legion 5 latop",price,weigth, quantity,"sample URL",rating,o,pc,u);
		assertEquals(p.hashCode(), p.hashCode());
		assertNotEquals(p.hashCode(), p2.hashCode());
	}



}
