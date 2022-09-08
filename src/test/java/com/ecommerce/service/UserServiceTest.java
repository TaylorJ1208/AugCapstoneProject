package com.ecommerce.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.model.Address;
import com.ecommerce.model.Orders;
import com.ecommerce.model.Product;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.model.UserCart;
import com.ecommerce.repo.RoleRepo;
import com.ecommerce.repo.UserRepo;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	
	@Mock	
	private UserRepo repo;
	
	@Mock	
	private RoleRepo rRepo;
	
	private UserService service;
	private User u;
	
	long id= 1;
	List<Orders> o = new ArrayList<>();
	Set<Role> r = new HashSet<>();
	List<Address> a = new ArrayList<>();
	List<UserCart> userCart = new ArrayList<>();

	@BeforeEach
	void setUp() throws Exception {
		service = new UserService();
		u = new User(id,"firstName","lastName","email","username","password","contact","ssn",o,r,a,userCart);
	}

	@Test
	void testGetAllUsers() {
		service.getAllUsers();
		verify(repo).findAll();
	}

	@Test
	void testGetUserById() {
//		long id= 2;
//		Role b = new Role(id,"USER",u);
		 
		when(repo.findById(u.getUserId())).thenReturn(Optional.of(u));
		assertThat(service.getUserById(u.getUserId())).isEqualTo(u);
	}

	@Test
	void testAddUser() {
		when(repo.save(u)).thenReturn(u);
		assertThat(service.addUser(u)).isEqualTo(u);
	}

	@Test
	void testUpdateUser() {
		String new_firstname = "new_firstname";
		u.setFirstName(new_firstname);
		when(repo.save(u)).thenReturn(u);
		when(repo.findById(u.getUserId())).thenReturn(Optional.of(u));
		assertThat(service.updateUser(u)).isEqualTo(u);
		verify(repo).findById(u.getUserId()); 
	}

	@Test
	void testDeleteUserById() {
		long id = 1;
		service.deleteUserById(id);
		verify(repo).deleteById(id);
	}

	@Test
	void testAddRoleToUser() throws Exception {
		long id= 1;
		List<User> users = new ArrayList<>();
		Role r = new Role(id,"USER",users);
		
		when(repo.findById(u.getUserId())).thenReturn(Optional.of(u));
		when(rRepo.findById(r.getRoleId())).thenReturn(Optional.of(r));
		
		u.getRoles().add(r);
		r.getUsers().add(null);
		service.addRoleToUser(u.getUserId(), r.getRoleId());
		
		ArgumentCaptor<User> addressCaptor = ArgumentCaptor.forClass(User.class);
		verify(repo).save(addressCaptor.capture());
		User capturedUser = addressCaptor.getValue();
		
		assertThat(capturedUser).isEqualTo(u);
	}

	@Test
	void testDeleteRoleFromUser() {
		service.deleteUserById(u.getUserId());
		verify(repo).deleteById(u.getUserId());
	}


}
