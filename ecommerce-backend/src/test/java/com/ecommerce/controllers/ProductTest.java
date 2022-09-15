package com.ecommerce.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ecommerce.controller.ProductController;
import com.ecommerce.model.Orders;
import com.ecommerce.model.Product;
import com.ecommerce.model.ProductCategory;
import com.ecommerce.model.User;
import com.ecommerce.model.UserCart;
import com.ecommerce.repo.ProductRepo;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductTest {
	
	@MockBean
	private ProductService productService;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private ProductRepo productRepo;
	
	@Autowired
	private MockMvc mockMvc;
	
	ObjectMapper mapper = new ObjectMapper();
	Product p;
	List<Product> products = new ArrayList<>();
	ProductCategory pc = new ProductCategory(1L, "misc", products);
	List<Orders> o = new ArrayList<>();
	List<UserCart> u = new ArrayList<>();
	
	@BeforeEach
	void setUp() throws Exception {
		productService = new ProductService(productRepo);
		p = new Product(1L,"Lenovo Laptop","Legion 5 latop",new BigDecimal(15),new BigDecimal(15), 15,"sample URL",3,o,pc,u);
	}
	
	@Test
	void testFindAllProduct() throws Exception {
		// Instantiate necessary objects
		List<Orders> order = new ArrayList<>();
		ProductCategory category = new ProductCategory();
		
		Product product = new Product(1L, "Shoes", "A pair of shoes", new BigDecimal(15.99),
				new BigDecimal(15), 12L, "http", 2, order, category, null);
		
		// List to compare
		List<Product> products = new ArrayList<>();
		
		category.setCategoryId(1L);
		category.setCategory("misc");
		product.setCategory(category);
		products.add(product);
		productRepo.save(product);
		Mockito.when( productRepo.findAll()).thenReturn(products);
		mockMvc.perform(get("/catalog/customer"))
				.andExpect(status().isOk());
	}
	
//	@Test
//	void testUpdateProduct() throws Exception {
//		Product newProduct = new Product();
//		List<Product> products = new ArrayList<>();
//		ProductCategory category = new ProductCategory(1L, "misc", products);
//		List<Orders> orders = new ArrayList<>();
//		newProduct.setProductId(1L);
//		newProduct.setCategory(category);
//		newProduct.setDescription("Testing product");
//		newProduct.setImage("http");
//		newProduct.setName("test product");
//		newProduct.setOrders(orders);
//		newProduct.setPrice(new BigDecimal(500));
//		newProduct.setQuantity(15);
//		newProduct.setRating(3);
//		newProduct.setUserCart(null);
//		newProduct.setWeight(new BigDecimal(5.5));
//		newProduct.toString();
//		Mockito.when(productService.updateProduct(newProduct)).thenReturn(newProduct);
//		String json = mapper.writeValueAsString(newProduct);
//		mockMvc.perform(put("/catalog/admin/update").contentType(MediaType.APPLICATION_JSON)
//				.content(json)
//				.accept(MediaType.APPLICATION_JSON))
//		.andExpect(status().isOk());
//		assertEquals(newProduct, productService.updateProduct(newProduct));
//		verify(productRepo).deleteById(newProduct.getProductId());
//	}

}
