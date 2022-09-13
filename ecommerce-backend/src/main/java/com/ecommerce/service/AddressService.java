package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.model.Address;
import com.ecommerce.repo.AddressRepo;

@Service
public class AddressService {

	@Autowired
	private AddressRepo addressRepo;
	
	public AddressService(AddressRepo repo) {
		this.addressRepo = repo;
	}

	public List<Address> getAllAddresses() {
		return addressRepo.findAll();
	}
	
	public Address getAddressById(Long id) {
		return addressRepo.findById(id)
				.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Address not found with id : "+id) );
	}
	
	public Address addAddress(Address address) {
		return addressRepo.save(address);
	}
	
	public Address updateAddress(Address address) {
		addressRepo
		.findById(address.getAddressId())
		.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Address not found with id : "+address.getAddressId()) );
	return addressRepo.save(address);
	}
	
	public void deleteAddressById(Long id) {
		addressRepo.deleteById(id);
	}
	
}
