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

import com.ecommerce.controller.AddressController;
import com.ecommerce.model.Address;
import com.ecommerce.model.User;
import com.ecommerce.service.AddressService;
import com.ecommerce.service.UserService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AddressController.class)
@AutoConfigureMockMvc(addFilters = false)
class AddressTest {	
	
	@MockBean
	private AddressService addressService;
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void testFindAllAddresses() throws Exception {
		// Instantiate necessary objects
		User user = new User();
		
		Address address = new Address(1L, "Cary", "NC", "400 Regency SQ.", "27545", "USA", null, user);
		
		// List to compare
		List<Address> addresses = new ArrayList<>();
		addresses.add(address);
		
		Mockito.when( addressService.getAllAddresses()).thenReturn(addresses);
		mockMvc.perform(get("/address"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].city", Matchers.is("Cary")));
	}

}
