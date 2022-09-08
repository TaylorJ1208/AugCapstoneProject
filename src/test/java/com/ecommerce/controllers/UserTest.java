package com.ecommerce.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.ecommerce.controller.UserController;
import com.ecommerce.email.EmailService;
import com.ecommerce.model.Address;
import com.ecommerce.model.Orders;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.RoleService;
import com.ecommerce.service.UserService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserTest {
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private RoleService roleService;
	
	@MockBean
	private EmailService emailService;
	
	@MockBean
	private OrderService orderService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void testFindAllUsers() throws Exception {
		// Instantiate necessary objects
		List<Orders> orders = new ArrayList<>();
		Set<Role> roles = new HashSet<>();
		List<Address> addresses = new ArrayList<>();
		
		User user = new User(1L, "Taylor", "Joostema", "TaylorJ1208@yahoo.com", "tay", "123", "919", "8604",
				orders, roles, addresses, null);
		
		// List to compare
		List<User> users = new ArrayList<>();
		users.add(user);
		
		Mockito.when( userService.getAllUsers()).thenReturn(users);
		mockMvc.perform(get("/user/admin"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].firstName", Matchers.is("Taylor")));
	}

}
