package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.model.Vendors;
import com.ecommerce.repo.VendorsRepo;

@Service
public class VendorsService {
	
	@Autowired
	private VendorsRepo vendorRepo;
	
	public List<Vendors> getAllVendors() {
		return vendorRepo.findAll();
	}
	
	public Vendors getVendorsById(Long id) {
		return vendorRepo.findById(id)
				.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Vendor not found with id : " + id) );
	}
	
	public void addVendor(Vendors vendor) {
		vendorRepo.save(vendor);
	}
	
	public Vendors updateVendor(Vendors vendor) {
		vendorRepo
			.findById(vendor.getVendorId())
			.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Vendor not found with id : " + vendor.getVendorId()) );
		return vendorRepo.save(vendor);
	}
	
	public void deleteVendor(Long id) {
		vendorRepo.deleteById(id);
	}
}
