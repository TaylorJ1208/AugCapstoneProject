package com.ecommerce.service;

import java.util.List;
import java.util.Optional;

import com.ecommerce.model.Orders;
import com.ecommerce.repo.OrdersRepo;

public class OrderService {

	private OrdersRepo ordersRepo;
	
	public List<Orders> getAllOrders() {
		return ordersRepo.findAll();
	}
	
	public Optional<Orders> getOrderById(Long id) {
		return ordersRepo.findById(id);
	}
	
	public void addOrder(Orders order) {
		ordersRepo.save(order);
	}
	
	public void updateOrder(Orders order, Long id) {
		if(ordersRepo.findById(id).isPresent()) {
			ordersRepo.save(order);
		}
		return;
	}
	
	public void deleteOrder(Long id) {
		ordersRepo.deleteById(id);
	}
	
}
