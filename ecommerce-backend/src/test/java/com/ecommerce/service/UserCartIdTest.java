package com.ecommerce.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.model.UserCartId;
import com.taylor.common.UserDefault;

@ExtendWith(MockitoExtension.class)
public class UserCartIdTest {
	
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
		assertEquals(id.getUserId().longValue(), 1L);
		assertEquals(id.getProductId().longValue(), 2L);
		assertNotEquals(id.toString(), id);
	}
	
	@Test
	void testSetterMethods() {
		UserCartId userCartId = new UserCartId();
		userCartId.setProductId(1L);
		userCartId.setUserId(2L);
		boolean equalIds = userCartId.equals(id);
		assertEquals(equalIds, false);
		userCartId = id;
		equalIds = userCartId.equals(id);
		assertEquals(equalIds, true);
	}
	
	@Test
	void testDefaultRole() {
		UserDefault defaultRole = new UserDefault();
		assertNotEquals(defaultRole.toString(), defaultRole);
		assertEquals(defaultRole.hashCode(), defaultRole.hashCode());
		UserDefault defaultRole2 = new UserDefault();
		boolean equals = defaultRole.equals(defaultRole2);
		assertEquals(equals, true);
	}

}
