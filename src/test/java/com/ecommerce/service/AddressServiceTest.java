package com.ecommerce.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.model.Address;
import com.ecommerce.repo.AddressRepo;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {
	@Mock
	private AddressRepo repo;
	private AddressService service;
	private Address a;
	
	@BeforeEach
	void setUp() {
		a = new Address(1,"Cary","NC","Lucent Str","27606","USA","104", null);
		
	}
	
	@Test
	void testGetAllAddresses() {
		service.getAllAddresses();
		verify(repo).findAll();
	}

	@Test	
	void testGetAddressById() {
	
		Address b = new Address(2,"Cary","NC","Lucent Str","27606","USA","104", null);
//		declare the returned result when the inner method (repo) is invoked. 
		when(repo.findById(b.getAddressId())).thenReturn(Optional.of(b));
//		compare to results when outer method is invoked.
		assertThat(service.getAddressById(b.getAddressId())).isEqualTo(b);
	}

//	
//	@Test
//	void testAddAddress2() {
////		call the service to get trace.
//		service.addAddress(a);
//		
////		create the capture to capture the return parameter object from repo method.
//		ArgumentCaptor<Address> addressCaptor = ArgumentCaptor.forClass(Address.class);
////		capture the parament from repo
//		verify(repo).save(addressCaptor.capture());
////		get the object from captor 
//		Address capturedAddress = addressCaptor.getValue();
////		compare
//		assertThat(capturedAddress).isEqualTo(a);
//	}
	
	@Test
	void testAddAddress() {
		when(repo.save(a)).thenReturn(a);
		assertEquals(1, repo.count());
	}
	
	
	@Test
	void testUpdateAddress() {
		String city = "Raleigh";
		a.setCity(city);
		when(repo.save(a)).thenReturn(a);
		when(repo.findById(a.getAddressId())).thenReturn(Optional.of(a));
		assertThat(service.updateAddress(a)).isEqualTo(a);
		verify(repo).findById(a.getAddressId());
	}
	
	
	@Test
	void testDeleteAddressById() {
		long id = 1;
		service.deleteAddressById(id);
		verify(repo).deleteById(id);
	}

}
