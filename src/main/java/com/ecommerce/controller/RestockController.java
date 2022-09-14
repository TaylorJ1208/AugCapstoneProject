package com.ecommerce.controller;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.config.RabbitConfig;
import com.ecommerce.model.Product;
import com.ecommerce.service.ProductService;

@RestController
@CrossOrigin("http://localhost:8081")
public class RestockController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private TopicExchange exchange;

	
	@GetMapping("/restock/remind/{id}")
	public void restockMessage(@PathVariable Long id) throws InterruptedException {
		Product product = productService.getProductById(id);
		String send = "The product: " + product.getName() + " is out-of-stock. Please request restock from " + product.getVendors().getEmail();
		rabbitTemplate.convertAndSend(exchange.getName(), RabbitConfig.ROUTING_KEY, send);
	}

}
