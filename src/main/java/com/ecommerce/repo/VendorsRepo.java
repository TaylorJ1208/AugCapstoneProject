package com.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Vendors;

public interface VendorsRepo extends JpaRepository<Vendors, Long> {
	
}
