package com.ecommerce.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.mail.MessagingException;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.config.RabbitConfig;
import com.ecommerce.email.EmailService;
import com.ecommerce.model.Product;
import com.ecommerce.model.VendorRequest;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.VenderRequestService;

@CrossOrigin(origins = { "https://e-frontend.azurewebsites.net", "http://localhost:4200" })
@RestController
public class RestockController {
	
	@Autowired
	private ProductService productService;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private TopicExchange exchange;

	@Autowired
	private EmailService emailService;

	@Autowired
	private VenderRequestService venderRequestService;
	
	@GetMapping("/restock/remind/{id}")
	public void restockMessage(@PathVariable Long id) throws InterruptedException {
		Product product = productService.getProductById(id);
		String send = "The product " + product.getName().toUpperCase() + " (Product Id : " + product.getProductId()
		+ ") has only " + product.getQuantity() + " units left in stock. Requesting "+(5)+" more units from "
		+ product.getVendors().getName().toUpperCase()+".";
		String[] arr = {send, id.toString()};
		
		System.out.println("Sending message: " + arr[0]+" Product Id: "+arr[1]);
		rabbitTemplate.convertAndSend(exchange.getName(), RabbitConfig.ROUTING_KEY, arr);
	}

	 @RabbitListeners( value = { @RabbitListener(queues = RabbitConfig.QUEUE) })
	public void receieve(String[] result) throws InterruptedException {
		System.out.println("Received message from " + RabbitConfig.EXCHANGE + " : " + result[0]);
		try {
			this.emailService.sendLowStockEmail(result[0]);
			this.requestVendorRestock(Long.parseLong(result[1]));
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	 public void requestVendorRestock(Long id) {
		 Product product = this.productService.getProductById(id);
		 try {
			 this.emailService.sendVendorEmail(product.getVendors().getEmail(), product, 5);
			 VendorRequest vendorRequest = new VendorRequest(0, product, 5, LocalDateTime.now(ZoneId.of("America/Toronto")) ,product.getVendors(),"Sent");
			 int reqId = this.venderRequestService.addVenderRequest(vendorRequest);
			 this.receiveVendorRequest(reqId, product);	 
		 }
		 catch (Exception e) {
			
		}
	 }
	 
	 public void receiveVendorRequest(int reqId, Product product) {
		 VendorRequest vendorRequest = this.venderRequestService.getVenderRequestById(reqId).get();
		 vendorRequest.setStatus("Received");
		 this.venderRequestService.changeRequestStatus(vendorRequest);
		 
		 product.setQuantity(product.getQuantity()+vendorRequest.getQuantityrequested());
		 productService.updateProduct(product);	 
	 }
}
