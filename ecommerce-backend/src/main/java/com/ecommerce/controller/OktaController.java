package com.ecommerce.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost8081")
public class OktaController {
	
//	@Autowired
//    private AuthenticationManager authenticationManager;
//	
//	@Autowired
//	private JwtAuthenticationToken jwt;
//	
//	@GetMapping("/api/userProfile")
//    @PreAuthorize("hasAuthority('SCOPE_profile')")
//    public <A extends AbstractOAuth2TokenAuthenticationToken<AbstractOAuth2Token>> Map<String, Object> getUserDetails(A authentication) {
//        return authentication.getTokenAttributes();
//    }
//
//    //For JWT only
//    @GetMapping("/api/userProfileJWT")
//    @PreAuthorize("hasAuthority('SCOPE_profile')")
//    public Map<String, Object> getUserDetails(JwtAuthenticationToken authentication) {
//        return authentication.getTokenAttributes();
//    }
//
//    //For Opaque Token only
//    @GetMapping("/api/userProfileOpaque")
//    @PreAuthorize("hasAuthority('SCOPE_profile')")
//    public Map<String, Object> getUserDetails(BearerTokenAuthentication authentication) {
//        return authentication.getTokenAttributes();
//    }

}
