package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.model.UserCart;
import com.ecommerce.model.UserCartId;
import com.ecommerce.repo.UserCartRepo;

@Service
public class UserCartService {
	
	@Autowired
	private UserCartRepo repo;
	
	public UserCartService(UserCartRepo repo2) {
		this.repo = repo2;
	}

	public List<UserCart> getAllCartItems() {
		return repo.findAll();
	}
	
	public UserCart getUserCartById(Long userId, Long productId) {
		return repo.findById(new UserCartId(userId,productId))
				.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"UserCart not found with user id: " 
						+ userId + " and product id: " + productId) );
	}
	
	public void addUserCart(UserCart userCart) {
		repo.save(userCart);
	}

	public UserCart updateUserCart(UserCart userCart) {
		repo
			.findById(userCart.getUserCartId())
			.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"UserCart not found with user id: "+userCart.getUserCartId()) );
		return repo.save(userCart);
	}
	
	public void deleteUserCart(UserCartId id) {
		repo.deleteById(id);
	}

}
