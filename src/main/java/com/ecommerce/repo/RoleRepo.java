package com.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Role;

public interface RoleRepo extends JpaRepository<Role, Long>{

}
