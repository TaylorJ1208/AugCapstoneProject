package com.ecommerce.service;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.model.UserCartId;
import com.taylor.common.UserDefault;

@ExtendWith(MockitoExtension.class)
class UserCartIdTest {
	
	@Mock
	UserCartId id;
	
	@Mock
	UserDefault defaultRole;
	
	@BeforeEach
	void setup() {
		id = new UserCartId(1L, 2L);
	}
	
	@Test
	void testGetterMethods() {
		assertEquals(1L, id.getUserId().longValue());
		assertEquals(2L, id.getProductId().longValue());
		assertEquals(id.toString(), id.toString());
	}
	
	@Test
	void testSetterMethods() {
		UserCartId userCartId = new UserCartId();
		userCartId.setProductId(1L);
		userCartId.setUserId(2L);
		boolean equalIds = userCartId.equals(id);
		assertEquals(false, equalIds);
		userCartId = id;
		equalIds = userCartId.equals(id);
		assertEquals(true, equalIds);
	}
	
	@Test
	void testDefaultRole() {
		assertEquals(defaultRole.toString(), defaultRole.toString());
		assertEquals(defaultRole.hashCode(), defaultRole.hashCode());

		boolean equals = defaultRole.equals(defaultRole);
		assertEquals(true, equals);
	}

}
