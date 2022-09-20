package com.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.VendorRequest;

public interface VenderRequestRepo extends JpaRepository<VendorRequest, Integer> {

}
