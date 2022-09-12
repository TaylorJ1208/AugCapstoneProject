package com.ecommerce.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.ecommerce.controller.ProductCategoryController;
import com.ecommerce.model.Product;
import com.ecommerce.model.ProductCategory;
import com.ecommerce.service.ProductCategoryService;
import com.ecommerce.service.UserService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductCategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductCategoryTest {
	
	@MockBean
	private ProductCategoryService service;
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void testFindAllCategories() throws Exception {
		// Instantiate necessary objects
		List<Product> products = new ArrayList<>();
		
		ProductCategory category = new ProductCategory(1L, "Supplies", products);
		
		// List to compare
		List<ProductCategory> categories = new ArrayList<>();
		categories.add(category);
		
		Mockito.when( service.getAllCategory()).thenReturn(categories);
		mockMvc.perform(get("/category"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].category", Matchers.is("Supplies")));
	}
	
}
