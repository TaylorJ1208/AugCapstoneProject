package com.ecommerce.service;

import java.util.List;
import java.util.Optional;

import com.ecommerce.model.Address;
import com.ecommerce.repo.AddressRepo;

public class AddressService {

	private AddressRepo addressRepo;
	
	public List<Address> getAllAddresses() {
		return addressRepo.findAll();
	}
	
	public Optional<Address> getAddressById(Long id) {
		return addressRepo.findById(id);
	}
	
	public void addAddress(Address address) {
		addressRepo.save(address);
	}
	
	public void updateAddress(Address address, Long id) {
		if(addressRepo.findById(id).isPresent()) {
			addressRepo.save(address);
		}
	}
	
	public void deleteAddressById(Long id) {
		addressRepo.deleteById(id);
	}
	
}
