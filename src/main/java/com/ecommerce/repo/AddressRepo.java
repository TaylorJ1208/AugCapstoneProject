package com.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Address;

public interface AddressRepo extends JpaRepository<Address, Long> {
	
}
