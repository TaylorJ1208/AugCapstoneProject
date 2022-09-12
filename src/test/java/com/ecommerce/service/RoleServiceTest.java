package com.ecommerce.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.model.Orders;
import com.ecommerce.model.Product;
import com.ecommerce.model.ProductCategory;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.repo.OrdersRepo;
import com.ecommerce.repo.RoleRepo;
import com.ecommerce.repo.UserCartRepo;


@ExtendWith(MockitoExtension.class)
class RoleServiceTest {
	
	@Mock	
	private RoleRepo repo;
	
	private RoleService service;
	private Role r;
	
	long id= 1;
	List<User> u = new ArrayList<>();

	@BeforeEach
	void setUp() throws Exception {
		service = new RoleService();
		r = new Role(id,"USER",u);
	}

	@Test
	void testGetAllRoles() {
		service.getAllRoles();
		verify(repo).findAll();
	}

	@Test
	void testGetRoleById() {
		long id= 2;
		Role b = new Role(id,"USER",u);
		 
		when(repo.findById(b.getRoleId())).thenReturn(Optional.of(b));

		assertThat(service.getRoleById(b.getRoleId())).isEqualTo(b);
	}

	@Test
	void testAddRole() {
		when(repo.save(r)).thenReturn(r);
		assertEquals(1, repo.count());
	}

	@Test
	void testUpdateRole() {
		String role = "ADMIN";
		r.setRole(role);
		when(repo.save(r)).thenReturn(r);
		when(repo.findById(r.getRoleId())).thenReturn(Optional.of(r));
		assertThat(service.updateRole(r)).isEqualTo(r);
		verify(repo).findById(r.getRoleId()); 
	}

	@Test
	void testDeleteRole() {
		long id = 1;
		service.deleteRole(id);
		verify(repo).deleteById(id);
	}

	@Test
	void testGetRoleByName() {
		when(repo.getRoleByName("USER")).thenReturn(r);
		assertThat(service.getRoleByName("USER")).isEqualTo(r);
		
	}

}
