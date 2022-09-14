package com.ecommerce.controllers;

import static org.junit.Assert.assertTrue;
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

import com.ecommerce.controller.RoleController;
import com.ecommerce.model.ProductCategory;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.service.RoleService;
import com.ecommerce.service.UserService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RoleController.class)
@AutoConfigureMockMvc(addFilters = false)
class RoleTest {

	@MockBean
	private RoleService roleService;
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void testFindAllRoles() throws Exception {
		// Instantiate necessary objects
		List<User> users = new ArrayList<>();
		
		Role role = new Role(1L, "ROLE_ADMIN", users);
		
		// List to compare
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		
		Mockito.when(roleService.getAllRoles()).thenReturn(roles);
		mockMvc.perform(get("/role"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].role", Matchers.is("ROLE_ADMIN")));
	}
	
	@Test
	void testupdateCategory() throws Exception {
		Role newRole = new Role();
		List<User> users = new ArrayList<>();
		newRole.setRoleId(1L);
		newRole.setRole("ROLE_TEST");
		newRole.setUsers(users);
		newRole.toString();
		Mockito.when(roleService.updateRole(newRole)).thenReturn(newRole);
		assertTrue(newRole.equals(roleService.updateRole(newRole)));
	}
	
}
