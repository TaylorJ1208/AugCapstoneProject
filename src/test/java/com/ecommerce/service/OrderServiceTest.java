package com.ecommerce.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.model.Address;
import com.ecommerce.model.Orders;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.model.UserCart;
import com.ecommerce.repo.AddressRepo;
import com.ecommerce.repo.OrdersRepo;
import com.ecommerce.repo.ProductRepo;
import com.ecommerce.repo.UserCartRepo;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
	
	@Mock	
	private OrdersRepo repo;
	@Mock	
	private UserCartRepo uRepo;
	private OrderService service;
	private Orders o;

	
	long millis=System.currentTimeMillis();  
	java.sql.Date date = new java.sql.Date(millis);
	User u = new User();
	String a = "sample address";
	BigDecimal amount = new BigDecimal(10000);
	List<Product> p = new ArrayList<>();

	@BeforeEach
	void setUp() throws Exception {
		service = new OrderService();
		o = new Orders(1,amount,date,true,u,a,a,p);
	}

	@Test
	void testGetAllOrders() {
		service.getAllOrders();
		verify(repo).findAll();
	}

	@Test
	void testGetOrderById() {
		Orders o1 = new Orders(2,amount,date,true,u,a,a,p);

		when(repo.findById(o1.getOrderId())).thenReturn(Optional.of(o1));

		assertThat(service.getOrderById(o1.getOrderId())).isEqualTo(o1);
	}


	@Test
	void testUpdateOrder() {
		String new_address = "New Address";
		o.setBillingAddress(new_address);
		o.setShippingAddress(new_address);
		when(repo.save(o)).thenReturn(o);
		when(repo.findById(o.getOrderId())).thenReturn(Optional.of(o));
		assertThat(service.updateOrder(o)).isEqualTo(o);
		verify(repo).findById(o.getOrderId());
	}

	@Test
	void testDeleteOrder() {
		long id = 1;
		service.deleteOrder(id);
		verify(repo).deleteById(id);	}

//	@Test
//	@Disabled
//	void testAddProductToOrder() {
//		
//		Product new_p = new Product (2,"sample");
//		when(repo.findById(o.getOrderId())).thenReturn(Optional.of(o));
//		
//	}
//	
//	@Test
//	@Disabled
//	void testAddOrder() {
////		doNothing().when(uRepo).findAll();
//		when(repo.save(o)).thenReturn(o);
//		
//		assertThat(service.addOrder(o)).isEqualTo(o);
//	}


}
