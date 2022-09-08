package com.ecommerce.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.annotation.Configuration;

import com.ecommerce.service.UserService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserService userService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userService);
	}
	
	protected void configure(HttpSecurity http) throws Exception{
		//csrf->cross site request forgery
//	    http.authorizeRequests().antMatchers("/user/customer/**", "/orders/admin/**"
//	    		, "catalog/customer/**","orders/customer", "/cart/**", "/user/admin/**", "/catalog/admin/**", "cart/admin/**").permitAll().and().authorizeRequests()
//	            .antMatchers("user/admin/admin").hasAuthority("ROLE_ADMIN").and().httpBasic().and().formLogin();
	}
	
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
