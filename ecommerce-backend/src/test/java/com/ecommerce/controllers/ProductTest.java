package com.ecommerce.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
	
	public static String asJsonString(final Object obj) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        final String jsonContent = mapper.writeValueAsString(obj);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}  
	
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
	
	@Test
	void testUpdateProduct() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/catalog/admin/update")

				.content(asJsonString(p))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	void testDeleteProduct() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/catalog/admin/delete/" + p.getProductId())

				.content(asJsonString(p))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	void testGetProductByName() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/catalog/customer/product/" + p.getName())
				.content(asJsonString(p))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	void testGetProductById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/catalog/customer/" + p.getProductId())
				.content(asJsonString(p))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());
	}

}
