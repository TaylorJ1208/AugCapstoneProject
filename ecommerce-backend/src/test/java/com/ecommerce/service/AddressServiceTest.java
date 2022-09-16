package com.ecommerce.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
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
		service = new AddressService(repo);
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
	
	@Test
	void testAddAddress() {
		when(repo.save(a)).thenReturn(a);
		assertThat(service.addAddress(a)).isEqualTo(a);
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
	
	@Test
	void testAddressEquals() {
		Address a1 = new Address(1,"Cary","NC","Lucent Str","27606","USA","104", null);
		Address a3 = new Address(1,"Cary","NC","Lucent Str","27606","USA","104", null);
		boolean equals = a1.equals(a3);
		assertEquals(true, equals);
		assertNotEquals(false, equals);
		assertNotSame(a1, a3);
		a1 = a3;
		assertSame(a1, a3);
	}
	
	@Test
	void testAddressHashCode() {
		Address a2 = new Address(1,"wrong city","NC","Lucent Str","27606","USA","104", null);
		assertEquals(a.hashCode(), a.hashCode());
		assertNotEquals(a.hashCode(), a2.hashCode());
	}

}
