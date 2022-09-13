package com.ecommerce.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.ecommerce.controller.ProductController;
import com.ecommerce.model.Orders;
import com.ecommerce.model.Product;
import com.ecommerce.model.ProductCategory;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.UserService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductTest {
	
	@MockBean
	private ProductService productService;
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private MockMvc mockMvc;
	
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
		
		Mockito.when( productService.getAllProducts()).thenReturn(products);
		mockMvc.perform(get("/catalog/customer"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].name", Matchers.is("Shoes")));
	}

}
