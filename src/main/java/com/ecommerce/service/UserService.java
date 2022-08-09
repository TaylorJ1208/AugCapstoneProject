package com.ecommerce.service;

import java.util.List;
import java.util.Optional;

import com.ecommerce.model.User;
import com.ecommerce.repo.UserRepo;

public class UserService {
	
	private UserRepo userRepo;
	
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}
	
	public Optional<User> getUserById(Long id) {
		return userRepo.findById(id);
	}
	
	public void addUser(User user) {
		userRepo.save(user);
	}
	
	public void updateUser(User user, Long id) {
		if(userRepo.existsById(id)) {
			userRepo.save(user);
		}
		return;
	}
	
	public void deleteUserById(Long id) {
		userRepo.deleteById(id);
	}
		
}
