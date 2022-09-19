package com.ecommerce.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.model.User;

public interface UserRepo extends JpaRepository<User, Long> {

	Optional<User> findByUserName(String username);
	
	@Query("SELECT u FROM User u WHERE u.oktaId = ?1")
	User findByOktaId(String oktaId);

}
