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
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.model.Orders;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.model.UserCartId;
import com.ecommerce.repo.OrdersRepo;
import com.ecommerce.repo.UserCartRepo;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
	
	@Mock	
	private OrdersRepo repo;
	@Mock	
	private UserCartRepo uRepo;
	
	@Mock
	private OrderService service;
	
	@Mock
	private UserCartService uService;
	
	@Mock
	private Orders order;
	
	@Mock
	private Product product;
	
	long userId = 1L;
	long productId = 1L;

	UserCartId userCartId = new UserCartId(userId,productId);

	long millis=System.currentTimeMillis();  
	java.sql.Date date = new java.sql.Date(millis);
	User user = new User();
	String a = "sample address";
	BigDecimal amount = new BigDecimal(10000);
	List<Product> p = new ArrayList<>();

	@BeforeEach
	void setUp() throws Exception {
		service = new OrderService(repo, uRepo);
		uService = new UserCartService(uRepo);
		order = new Orders(1L,amount,date,true,user,a,a,p);
	}

	@Test
	void testGetAllOrders() {
		service.getAllOrders();
		verify(repo).findAll();
	}

	@Test
	void testGetOrderById() {
		Orders o1 = new Orders(2,amount,date,true,user,a,a,p);

		when(repo.findById(o1.getOrderId())).thenReturn(Optional.of(o1));

		assertThat(service.getOrderById(o1.getOrderId())).isEqualTo(o1);
	}


	@Test
	void testUpdateOrder() {
		String new_address = "New Address";
		order.setBillingAddress(new_address);
		order.setShippingAddress(new_address);
		when(repo.save(order)).thenReturn(order);
		when(repo.findById(order.getOrderId())).thenReturn(Optional.of(order));
		assertThat(service.updateOrder(order)).isEqualTo(order);
		verify(repo).findById(order.getOrderId());
	}

	@Test
	void testDeleteOrder() {
		long id = 1;
		service.deleteOrder(id);
		verify(repo).deleteById(id);	
		
	}
	
	@Test
	void testAddOrder() {
		when(repo.save(order)).thenReturn(order);
		
		service.addOrder(order);
		verify(repo).save(order);
	}
	
	@Test
	void testOrderEquals() {
		Orders o1 = new Orders(1L,amount,date,true,user,a,a,p);
		Orders o3 = new Orders(1L,amount,date,true,user,a,a,p);
		boolean equals = o1.equals(o3);
		assertEquals(true, equals);
		assertNotEquals(false, equals);
		assertNotSame(o1, o3);
		o1 = o3;
		assertSame(o1, o3);
	}
	
	@Test
	void testOrderHashCode() {
		Orders o2 = new Orders(1L,amount,date,false,user,a,a,p);
		assertEquals(order.hashCode(), order.hashCode());
		assertNotEquals(order.hashCode(), o2.hashCode());
	}


}
