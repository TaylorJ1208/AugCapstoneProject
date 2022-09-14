package com.ecommerce.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
	
	@Mock
	ProductCategory category;
	
	@MockBean
	private ProductCategoryService service;
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private List<Product> products;
	
	@BeforeEach
	void setup() {
		category = new ProductCategory(1L, "Supplies", products);
		products = new ArrayList<>();
	}
	
	@Test
	void testFindAllCategories() throws Exception {
		// List to compare
		List<ProductCategory> categories = new ArrayList<>();
		categories.add(category);
		
		Mockito.when( service.getAllCategory()).thenReturn(categories);
		mockMvc.perform(get("/category"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].category", Matchers.is("Supplies")));
	}
	
	@Test
	void testupdateCategory() throws Exception {
		ProductCategory newCategory = new ProductCategory();
		newCategory.setCategoryId(1L);
		newCategory.setCategory("Test");
		newCategory.setProducts(products);
		newCategory.toString();
		Mockito.when(service.updateCategory(newCategory)).thenReturn(newCategory);
		assertEquals(newCategory, service.updateCategory(newCategory));
		verify(service).updateCategory(newCategory);
	}
	
}
