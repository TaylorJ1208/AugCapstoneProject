package com.ecommerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.model.Review;
import com.ecommerce.service.ReviewService;

@RestController
@RequestMapping("review")
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	@GetMapping()
	public List<Review> getAllReviews() {
		return reviewService.getAllReviews();
	}
	
	@GetMapping("/{id}")
	public Optional<Review> getReviewById(@PathVariable Long id) {
		return reviewService.getReviewById(id);
	}
	
	@GetMapping("/name/{name}")
	public List<Review> getReviewsByName(@PathVariable String name) {
		return reviewService.getReviewsByName(name);
	}
	
	@PostMapping("/add")
	public void addReview(@RequestBody Review review) {
		reviewService.addReview(review);
	}
	
	@PutMapping("/update")
	public void updateReview(@RequestBody Review review) {
		reviewService.updateReview(review);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteReview(@PathVariable Long id) {
		reviewService.deleteReview(id);
	}

}
