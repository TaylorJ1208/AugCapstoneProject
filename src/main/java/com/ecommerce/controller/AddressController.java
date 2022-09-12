package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.model.Address;
import com.ecommerce.service.AddressService;



@RestController
@RequestMapping("/address")
public class AddressController {

	@Autowired
	private AddressService addressService;
	
	@GetMapping("/{id}")
	public Address getaddress(@PathVariable Long id) {
		return addressService.getAddressById(id);
	}
	
	@GetMapping()
	public List<Address> getaddresss() {
		return addressService.getAllAddresses();
	}
	
	@PostMapping("/add")
	public void addaddress(@RequestBody Address address) {
		addressService.addAddress(address);
	}
	
	@PutMapping("/update")
	public void updateaddress(@RequestBody Address address) {
		addressService.updateAddress(address);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteaddress(@PathVariable Long id) {
		addressService.deleteAddressById(id);
	}
}