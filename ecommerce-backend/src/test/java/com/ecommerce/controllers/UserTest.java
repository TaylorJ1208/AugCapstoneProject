package com.ecommerce.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

import com.ecommerce.controller.UserController;
import com.ecommerce.email.EmailService;
import com.ecommerce.model.Address;
import com.ecommerce.model.Orders;
import com.ecommerce.model.Product;
import com.ecommerce.model.ProductCategory;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.model.UserCart;
import com.ecommerce.model.UserCartId;
import com.ecommerce.repo.RoleRepo;
import com.ecommerce.repo.UserRepo;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.RoleService;
import com.ecommerce.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	@MockBean
	private UserRepo userRepo;
	
	@MockBean
	private RoleRepo roleRepo;
	
	public static String asJsonString(final Object obj) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        final String jsonContent = mapper.writeValueAsString(obj);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}  
	
	long id= 1L;
	List<Orders> o = new ArrayList<>();
	Set<Role> r = new HashSet<>();
	List<Address> a = new ArrayList<>();
	List<UserCart> userCart = new ArrayList<>();
	List<Product> products = new ArrayList<>();
	
	@MockBean
	ProductCategory pc;
	
	@MockBean
	UserCartId userCartId;
	
	@MockBean
	Product product;
	
	@MockBean
	User user;
	
	@BeforeEach
	void setUp() throws Exception {
		
		userService = new UserService(userRepo, roleRepo);
		user = new User(id,"firstName","lastName","email","username","password","contact","ssn",o,r,a,userCart);
		pc = new ProductCategory(id, "category", products);
		product = new Product(id,"Lenovo Laptop","Legion 5 latop",
				new BigDecimal(15),new BigDecimal(15), 3,"sample URL",3,o,pc,userCart);
		userCartId = new UserCartId(user.getUserId(), product.getProductId());
	}
	
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
				.andExpect(status().isOk());
	}
	
	@Test
	void testUpdateUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/user/customer/update")
				.content(asJsonString(user))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	void testGetUserById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/user/customer/" + user.getUserId())
				.content(asJsonString(user))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	void testAddRoleToUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/user/admin/" + user.getUserId() + "/role/" + 1L)
				.content(asJsonString(user))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());
	}

}
