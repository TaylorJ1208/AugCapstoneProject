package com.ecommerce.service;

import java.util.List;
import java.util.Optional;

import com.ecommerce.model.Role;
import com.ecommerce.repo.RoleRepo;

public class RoleService {
	
	private RoleRepo roleRepo;
	
	public List<Role> getAllRoles() {
		return roleRepo.findAll();
	}
	
	public Optional<Role> getRoleById(Long id) {
		return roleRepo.findById(id);
	}
	
	public void addRole(Role role) {
		roleRepo.save(role);
	}
	
	public void updateRole(Role role, Long id) {
		if(roleRepo.findById(id).isPresent()) {
			roleRepo.save(role);
		}
		return;
	}
	
	public void deleteRole(Long id) {
		roleRepo.deleteById(id);
	}
	
}
