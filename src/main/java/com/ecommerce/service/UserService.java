package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.model.User;
import com.ecommerce.repo.UserRepo;

@Service
public class UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}
	
	public User getUserById(Long id) {
		return userRepo.findById(id)
				.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found with id : "+id) );
	}
	
	public void addUser(User user) {
		userRepo.save(user);
	}
	
	public User updateUser(User user) {
		userRepo
				.findById(user.getUserId())
				.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found with id : "+user.getUserId()) );
		return userRepo.save(user);
	}
	
	public void deleteUserById(Long id) {
		userRepo.deleteById(id);
	}
	
		
}
