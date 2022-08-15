package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.model.Orders;
import com.ecommerce.model.Product;
import com.ecommerce.repo.OrdersRepo;
import com.ecommerce.repo.ProductRepo;

@Service
public class OrderService {

	@Autowired
	private OrdersRepo ordersRepo;
	
	@Autowired
	private ProductRepo productRepo;
	
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
	
	public void addProductToOrder(Long orderId, Long productId) throws Exception {
		Orders order = ordersRepo.findById(orderId)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Order not found with id : "+orderId));
		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found with id : "+productId));
		order.getProducts().add(product);
		product.getOrders().add(order);
		ordersRepo.save(order);
		productRepo.save(product);
	}
	
}
