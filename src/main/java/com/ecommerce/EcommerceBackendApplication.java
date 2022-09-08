package com.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

//@ServletComponentScan
@SpringBootApplication
public class EcommerceBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceBackendApplication.class, args);
		
	}

}
