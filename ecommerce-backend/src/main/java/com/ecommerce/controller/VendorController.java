package com.ecommerce.controller;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.email.EmailService;
import com.ecommerce.model.Product;
import com.ecommerce.model.Vendors;
import com.ecommerce.service.VendorsService;


@CrossOrigin(origins = {"https://e-frontend.azurewebsites.net", "http://localhost:4200" })
@RestController
@RequestMapping("/vendors")
public class VendorController {
	
	@Autowired
	private VendorsService vendorService;
	
	@Autowired
	private EmailService emailService;
	
	@GetMapping("/{id}")
	public Vendors getVendor(@PathVariable Long id) {
		return vendorService.getVendorsById(id);
	}
	
	@GetMapping()
	public List<Vendors> getAllVendors() {
		return vendorService.getAllVendors();
	}
	
	@PostMapping("/add")
	public void addVendor(@RequestBody Vendors vendor) {
		vendorService.addVendor(vendor);
	}
	
	@PutMapping("/update")
	public void updateVendor(@RequestBody  Vendors vendor) {
		vendorService.updateVendor(vendor);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteVendor(@PathVariable Long id) {
		vendorService.deleteVendor(id);
	}
	
	@PostMapping("/restock/{vendorEmail}/{amount}")
	public void requestVendorRestock(@PathVariable String vendorEmail, @RequestBody Product product, @PathVariable int amount) throws MessagingException {
		emailService.sendVendorEmail(vendorEmail, product, amount);
	}
}
