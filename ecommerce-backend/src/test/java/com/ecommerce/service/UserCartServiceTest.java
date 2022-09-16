package com.ecommerce.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
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
import com.ecommerce.repo.UserCartRepo;

@ExtendWith(MockitoExtension.class)
class UserCartServiceTest {
	
	@Mock	
	private UserCartRepo repo;
	
	private UserCartService service;
	private UserCart u;
	
	long userId = 1;
	long productId = 1;

	
	
	long id = 1;
	private BigDecimal price= new BigDecimal(10000);
	private BigDecimal weigth = new BigDecimal(4);
	long quantity = 1000;
	long rating = 5;
	List<Orders> o = new ArrayList<>();
	ProductCategory pc = new ProductCategory();
	List<UserCart> userCart = new ArrayList<>();
	UserCartId userCartId = new UserCartId(userId,productId);
	Product product = new Product(id,"Lenovo Laptop","Legion 5 latop",price,weigth, quantity,"sample URL",rating,o,pc,userCart);
	
	
	Set<Role> r = new HashSet<>();
	List<Address> a = new ArrayList<>();
	User user = new User(id,"firstName","lastName","email","username","password","contact","ssn",o,r,a,userCart);
	
	@BeforeEach
	void setUp() throws Exception {
		service = new UserCartService(repo);
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

	@Test
	void testUserCartEquals() {
		UserCart u1 = new UserCart(userCartId,user,product,10);
		UserCart u3 = new UserCart(userCartId,user,product,10);
		boolean equals = u1.equals(u3);
		assertEquals(true, equals);
		assertNotEquals(false, equals);
		assertNotSame(u1, u3);
		u1 = u3;
		assertSame(u1, u3);
	}
	
	@Test
	void testUserCartHashCode() {
		assertNotEquals(u.toString(), u);
		UserCart u2 = new UserCart(userCartId,user,product,15);
		assertEquals(u.hashCode(), u.hashCode());
		assertNotEquals(u.hashCode(), u2.hashCode());
	}
	
	@Test
	void testSetterMethods() {
		UserCart u1 = new UserCart(userCartId,user,product,10);
		UserCart u2 = new UserCart();
		u2.setProduct(product);
		u2.setQuantity(15);
		u2.setUser(user);
		u2.setUserCartId(userCartId);
		assertNotEquals(u1, u2);
	}
	
	@Test
	void testGetUserCartById() {
		when(repo.findById(u.getUserCartId())).thenReturn(Optional.of(u));
		assertThat(service.getUserCartById(u.getUserCartId().getProductId(), u.getUserCartId().getUserId())).isEqualTo(u);
	}
	
	@Test
	void testDeleteUserCartById() {
		service.deleteUserCart(userCartId);
		verify(repo).deleteById(userCartId);
	}

}
