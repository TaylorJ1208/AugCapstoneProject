package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.model.UserCart;
import com.ecommerce.model.UserCartId;
import com.ecommerce.service.UserCartService;

@RestController
@RequestMapping("/cart")
public class UserCartController {

	@Autowired
	private UserCartService service;
	
	@GetMapping("/customer/{userId}/{productId}")
	public UserCart getUserCartById(@PathVariable Long userId, @PathVariable Long productId) {
		return service.getUserCartById(userId, productId);
	}
	
	@GetMapping("/customer")
	public List<UserCart> getUserCart() {
		return service.getAllCartItems();
	}
	
	@PostMapping("/customer/add")
	public void addItemToCart(@RequestBody UserCart userCart) {
		service.addUserCart(userCart);
	}
	
	@PutMapping("/customer/update")
	public void updateItem(@RequestBody UserCart userCart) {
		service.updateUserCart(userCart);
	}
	
	@DeleteMapping("/customer/delete/{userId}/{productId}")
	public void deleteUser(@PathVariable Long userId, @PathVariable Long productId) {
		service.deleteUserCart(new UserCartId(userId, productId));
	}
}
