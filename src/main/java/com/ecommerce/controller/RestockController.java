package com.ecommerce.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.config.RabbitConfig;
import com.ecommerce.model.Product;
import com.ecommerce.rabbitmq.Receiver;
import com.ecommerce.service.ProductService;

@RestController
@CrossOrigin("http://localhost:8081")
public class RestockController {
	
	@Autowired
	private ProductService productService;
	
	private final RabbitTemplate rabbitTemplate;
	private final Receiver receiver;


	public RestockController(Receiver receiver, RabbitTemplate rabbitTemplate) {
		this.receiver = receiver;
		this.rabbitTemplate = rabbitTemplate;
	}
	
	@GetMapping("/restock/remind/{id}")
	public void restockMessage(@PathVariable Long id) throws InterruptedException {
		Product product = productService.getProductById(id);
		String send = "The product: " + product.getName() + " is out-of-stock. Please request restock from " + product.getVendors().getEmail();
		rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_KEY, send);
		receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
	}

}
