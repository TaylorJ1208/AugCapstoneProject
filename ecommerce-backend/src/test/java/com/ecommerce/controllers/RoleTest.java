package com.ecommerce.controllers;

import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.ecommerce.controller.RoleController;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.repo.RoleRepo;
import com.ecommerce.service.RoleService;
import com.ecommerce.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RoleController.class)
@AutoConfigureMockMvc(addFilters = false)
class RoleTest {

	@MockBean
	private RoleService roleService;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private RoleRepo roleRepo;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private Role role;
	
	List<User> users = new ArrayList<>();
	
	@BeforeEach
	void setUp() throws Exception {
		
		roleService = new RoleService(roleRepo);
		role = new Role(1L, "ROLE_ADMIN", users);
		
	}
	
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
				.andExpect(status().isOk());
	}
	
	@Test
	void testUpdateRole() throws Exception {
		Role newRole = new Role();
		List<User> users = new ArrayList<>();
		newRole.setRoleId(1L);
		newRole.setRole("ROLE_TEST");
		newRole.setUsers(users);
		newRole.toString();
		assertNotEquals(newRole, role);
	}
	
	@Test
	void testAddRoleController() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/role/add")
				.content(asJsonString(role))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	void testGetRole() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/role")
				.content(asJsonString(role))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	void testGetRoleById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/role/" + role.getRoleId())
				.content(asJsonString(role))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	void testUpdateRoleController() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/role/update")
				.content(asJsonString(role))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        final String jsonContent = mapper.writeValueAsString(obj);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}  
	
}
