package com.ecommerce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.model.Review;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {
	
	@Query("SELECT r FROM Review r WHERE r.name = ?1")
	public List<Review> getReviewsByName(String name);
}
