package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.model.Role;
import com.ecommerce.repo.RoleRepo;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepo roleRepo;
	
	public RoleService(RoleRepo repo) {
		this.roleRepo = repo;
	}

	public List<Role> getAllRoles() {
		return roleRepo.findAll();
	}
	
	public Role getRoleById(Long id) {
		return roleRepo.findById(id)
				.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Role not found with id : "+id) );
	}
	
	public Role addRole(Role role) {
		return roleRepo.save(role);
	}
	
	public Role updateRole(Role role) {
		roleRepo
			.findById(role.getRoleId())
			.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Role not found with id : "+role.getRoleId()) );
		return roleRepo.save(role);
	}
	
	public void deleteRole(Long id) {
		roleRepo.deleteById(id);
	}
	
	public Role getRoleByName(String name) {
		return roleRepo.getRoleByName(name);
	}
	
	
}
