package com.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.model.User;
import com.ecommerce.model.UsersDetails;
import com.ecommerce.model.Role;
import com.ecommerce.repo.RoleRepo;
import com.ecommerce.repo.UserRepo;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	public UserService(UserRepo repo, RoleRepo rRepo) {
		this.userRepo = repo;
		this.roleRepo = rRepo;
	}

	public List<User> getAllUsers() {
		return userRepo.findAll();
	}
	
	public User getUserById(Long id) {
		return userRepo.findById(id)
				.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found with id : "+id) );
	}
	
	public User addUser(User user) {
		return userRepo.save(user);
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
	
	public void addRoleToUser(Long userId, Long roleId) throws Exception {
		User user = userRepo.findById(userId)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found with id : "+userId));
		Role role = roleRepo.findById(roleId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Role not found with id : "+roleId));
		user.getRoles().add(role);
		role.getUsers().add(user);
		userRepo.save(user);
		roleRepo.save(role);
	}
	
	public void deleteRoleFromUser(Long userId, Long roleId) {
		userRepo.deleteById(userId);
		roleRepo.deleteById(roleId);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepo.findByUserName(username);
        return user.map(UsersDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + " Not Found"));
	}
		
}
