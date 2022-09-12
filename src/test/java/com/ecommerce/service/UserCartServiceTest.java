package com.ecommerce.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.model.Address;
import com.ecommerce.model.Orders;
import com.ecommerce.model.Product;
import com.ecommerce.model.ProductCategory;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.model.UserCart;
import com.ecommerce.model.UserCartId;
import com.ecommerce.repo.RoleRepo;
import com.ecommerce.repo.UserCartRepo;

@ExtendWith(MockitoExtension.class)
class UserCartServiceTest {
	
	@Mock	
	private UserCartRepo repo;
	
	private UserCartService service;
	private UserCart u;
	
	long userId = 1;
	long productId = 1;

	UserCartId userCartId = new UserCartId(userId,productId);
	
	long id = 1;
	private BigDecimal price= new BigDecimal(10000);
	private BigDecimal weigth = new BigDecimal(4);
	long quantity = 1000;
	long rating = 5;
	List<Orders> o = new ArrayList<>();
	ProductCategory pc = new ProductCategory();
	List<UserCart> userCart = new ArrayList<>();
	Product product = new Product(id,"Lenovo Laptop","Legion 5 latop",price,weigth, quantity,"sample URL",rating,o,pc,userCart);
	
	
	Set<Role> r = new HashSet<>();
	List<Address> a = new ArrayList<>();
	User user = new User(id,"firstName","lastName","email","username","password","contact","ssn",o,r,a,userCart);
	
	@BeforeEach
	void setUp() throws Exception {
		service = new UserCartService();
		u = new UserCart(userCartId,user,product,10);
	}

	@Test
	void testGetAllCartItems() {
		service.getAllCartItems();
		verify(repo).findAll();
	}


	@Test
	void testAddUserCart() {
		service.addUserCart(u);
		verify(repo).save(u);
	}

//	@Test
//	void testUpdateUserCart() {
//		when(repo.findById(u.getUserCartId())).thenReturn(Optional.of(u));
//		assertThat(service.updateUserCart(u)).isEqualTo(u);
//	}
//
//	@Test
//	void testDeleteUserCart() {
//		fail("Not yet implemented");
//	}

}
