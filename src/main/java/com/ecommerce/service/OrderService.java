package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.model.Orders;
import com.ecommerce.repo.OrdersRepo;

@Service
public class OrderService {

	@Autowired
	private OrdersRepo ordersRepo;
	
	public List<Orders> getAllOrders() {
		return ordersRepo.findAll();
	}
	
	public Orders getOrderById(Long id) {
		return ordersRepo.findById(id)
				.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Order not found with id : "+id) );
	}
	
	public void addOrder(Orders order) {
		ordersRepo.save(order);
	}
	
	public Orders updateOrder(Orders order) {
		ordersRepo
		.findById(order.getOrderId())
		.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Order not found with id : "+order.getOrderId()) );
	return ordersRepo.save(order);
	}
	
	public void deleteOrder(Long id) {
		ordersRepo.deleteById(id);
	}
	
}
