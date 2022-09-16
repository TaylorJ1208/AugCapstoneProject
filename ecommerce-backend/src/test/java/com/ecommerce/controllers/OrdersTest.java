package com.ecommerce.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.ecommerce.controller.OrdersController;
import com.ecommerce.email.EmailService;
import com.ecommerce.model.Orders;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrdersController.class)
@AutoConfigureMockMvc(addFilters = false)
class OrdersTest {
	
	@MockBean
	private OrderService orderService;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private EmailService emailService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	
	@Test
	void testFindAllOrders() throws Exception {
		// Instantiate necessary objects
		Orders order = new Orders();
		User user = new User();
		List<Product> products = new ArrayList<>();
		java.sql.Date date = new java.sql.Date(1500);
		order = new Orders(1L, new BigDecimal(15.00), date, true, user, "913 Bridlemine Dr.", "913 Bridlemine Dr.",
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
	
	@Test
	void testAddOrder() throws Exception {
		Orders order = new Orders();
		User user = new User();
		List<Product> products = new ArrayList<>();
		java.sql.Date date = new java.sql.Date(1500);
		order = new Orders(1L, new BigDecimal(15.00), date, true, user, "913 Bridlemine Dr.", "913 Bridlemine Dr.",
				products);
		
		// List to compare
		List<Orders> orders = new ArrayList<>();
		orders.add(order);
		
		Mockito.when( orderService.addOrder(order)).thenReturn(order);
		String json = mapper.writeValueAsString(order);
		mockMvc.perform(post("/orders/add").contentType(MediaType.APPLICATION_JSON)
					.characterEncoding("utf-8")
					.content(json)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
	}
	
	@Test
	void testupdateOrder() throws Exception {
		List<Product> products = new ArrayList<>();
		User user = new User();
		Orders newOrder = new Orders();
		newOrder.toString();
		newOrder.setOrderId(1L);
		newOrder.setAmount(new BigDecimal(10));
		newOrder.setBillingAddress("3008 Ashland Grove Dr.");
		newOrder.setOrderDate(new java.sql.Date(1500));
		newOrder.setOrderId(1L);
		newOrder.setProducts(products);
		newOrder.setUser(user);
		newOrder.setStatus(false);
		newOrder.setShippingAddress("3008 Ashland Grove Dr.");
		newOrder.toString();
		Mockito.when(orderService.updateOrder(newOrder)).thenReturn(newOrder);
		assertEquals(newOrder, orderService.updateOrder(newOrder));
		verify(orderService).updateOrder(newOrder);
	}
}
