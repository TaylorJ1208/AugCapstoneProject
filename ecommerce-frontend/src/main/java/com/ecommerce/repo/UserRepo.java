package com.ecommerce.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.User;

public interface UserRepo extends JpaRepository<User, Long> {

	Optional<User> findByUserName(String username);

}
