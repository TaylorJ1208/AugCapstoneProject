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

import com.ecommerce.controller.OrdersController;
import com.ecommerce.model.Orders;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.UserService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrdersController.class)
@AutoConfigureMockMvc(addFilters = false)
class OrdersTest {

	@MockBean
	private OrderService orderService;
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void testFindAllOrders() throws Exception {
		// Instantiate necessary objects
		List<Product> products = new ArrayList<>();
		User user = new User();
		 long millis=System.currentTimeMillis();  
		java.sql.Date date = new java.sql.Date(millis);
		Orders order = new Orders(1L, new BigDecimal(15.00), date, true, user, "913 Bridlemine Dr.", "913 Bridlemine Dr.",
				products);
		
		// List to compare
		List<Orders> orders = new ArrayList<>();
		orders.add(order);
		
		Mockito.when( orderService.getAllOrders()).thenReturn(orders);
		mockMvc.perform(get("/orders/admin"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].amount", Matchers.is(15)));
	}
}
