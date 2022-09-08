package com.ecommerce.controller;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.email.EmailService;
import com.ecommerce.model.Orders;
import com.ecommerce.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrdersController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private EmailService emailService;
	
	@GetMapping("/admin/{id}")
	public Orders getorder(@PathVariable Long id) {
		return orderService.getOrderById(id);
	}
	
	@GetMapping("/admin")
	public List<Orders> getorders() {
		return orderService.getAllOrders();
	}
	
	@PostMapping("/add")
	public void addorder(@RequestBody Orders order) throws MessagingException {
		orderService.addOrder(order);
		emailService.sendReceipt(order);
	}
	
	@PostMapping("/{orderId}/product/{productId}")
	public void addProductToOrder(@PathVariable Long orderId, @PathVariable Long productId) throws Exception {
		orderService.addProductToOrder(orderId, productId);
	}
	
	@PutMapping("/update")
	public void updateorder(@RequestBody Orders order) {
		orderService.updateOrder(order);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteorder(@PathVariable Long id) {
		orderService.deleteOrder(id);
	}
}
