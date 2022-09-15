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
import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.MetricTelemetry;

@RestController
@RequestMapping("/orders")
public class OrdersController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private EmailService emailService;

	@Autowired
   	TelemetryClient telemetryClient;
	
	@GetMapping("/admin/{id}")
	public Orders getorder(@PathVariable Long id) {
		telemetryClient.trackEvent("/orders/admin/id Request Triggered");
		long startTime = System.nanoTime();
		Orders order = orderService.getOrderById(id);
		long endTime = System.nanoTime();
    
		MetricTelemetry benchmark = new MetricTelemetry();
		benchmark.setName("Get Order By ID Request (ms)");
		double timeInNano = endTime - startTime;
 		benchmark.setValue(timeInNano/1e6);
		telemetryClient.trackMetric(benchmark);
		return order;
	}
	
	@GetMapping("/admin")
	public List<Orders> getorders() {
		telemetryClient.trackEvent("/orders/admin Request Triggered");
		long startTime = System.nanoTime();
		List<Orders> orders = orderService.getAllOrders();
		long endTime = System.nanoTime();
    
		MetricTelemetry benchmark = new MetricTelemetry();
		benchmark.setName("Get All Orders Request (ms)");
		double timeInNano = endTime - startTime;
 		benchmark.setValue(timeInNano/1e6);
		telemetryClient.trackMetric(benchmark);
		return orders;
	}
	
	@PostMapping("/add")
	public void addorder(@RequestBody Orders order) throws MessagingException {
		telemetryClient.trackEvent("/orders/add Request Triggered");
		orderService.addOrder(order);
		emailService.sendReceipt(order);
	}
	
	@PostMapping("/{orderId}/product/{productId}")
	public void addProductToOrder(@PathVariable Long orderId, @PathVariable Long productId) throws Exception {
		telemetryClient.trackEvent("/orderId/product/productId Request Triggered");
		orderService.addProductToOrder(orderId, productId);
		long startTime = System.nanoTime();
		orderService.addProductToOrder(orderId, productId);
		long endTime = System.nanoTime();
    
		MetricTelemetry benchmark = new MetricTelemetry();
		benchmark.setName("Add Product to Order Request (ms)");
		double timeInNano = endTime - startTime;
 		benchmark.setValue(timeInNano/1e6);
		telemetryClient.trackMetric(benchmark);
	}
	
	@PutMapping("/update")
	public void updateorder(@RequestBody Orders order) {
		telemetryClient.trackEvent("/orders/update Request Triggered");
		orderService.updateOrder(order);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteorder(@PathVariable Long id) {
		telemetryClient.trackEvent("/orders/delete/id Request Triggered");
		orderService.deleteOrder(id);
	}
}
