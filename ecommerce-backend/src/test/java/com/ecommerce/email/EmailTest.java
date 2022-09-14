package com.ecommerce.email;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class EmailTest {
	
	@MockBean
	EmailService emailService;
	
	@MockBean
	EmailConfig emailConfig;
	
	@BeforeEach
	void setup() {
		emailConfig = new EmailConfig();
		emailConfig.setHost("localhost:test");
		emailConfig.setPassword("test");
		emailConfig.setPort(8080);
		emailConfig.setUsername("test user");
		emailConfig.toString();
	}
	
	@Test
	void testEmailEqual() {
		EmailConfig config = new EmailConfig("local", 8081, "test2", "test user2");
		assertNotEquals(emailConfig, config);
		EmailConfig config2 = new EmailConfig("wrong local", 8081, "test2", "test user2");
		boolean equals = config.equals(config2);
		assertEquals(false, equals);
		config = config2;
		equals = config.equals(config2);
		assertEquals(true, equals);
	}
	
	@Test
	void testEmailGet() {
		assertEquals("localhost:test", emailConfig.getHost());
		assertEquals( "test", emailConfig.getPassword());
		assertEquals(8080, emailConfig.getPort());
		assertEquals("test user", emailConfig.getUsername());
	}
	
	
	

}
