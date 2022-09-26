package com.ecommerce.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.model.VendorRequest;
import com.ecommerce.repo.VenderRequestRepo;

@Service
public class VenderRequestService {
	
	@Autowired
	VenderRequestRepo repo;
	
	public List<VendorRequest> getVenderRequests(){
		return this.repo.findAll();
	}
	
	public Optional<VendorRequest> getVenderRequestById(int id) {
		return this.repo.findById(id);
	}

	public int addVenderRequest(VendorRequest vendorRequest) {
		vendorRequest.setRequestdate(LocalDateTime.now(ZoneId.of("America/Toronto")));
		return this.repo.save(vendorRequest).getId();
	}
	
	public void deleteVendorRequest(int id) {
		this.repo.deleteById(id);
	}
	
	public void changeRequestStatus(VendorRequest vendorRequest) {
		this.repo.save(vendorRequest);
	}
}
