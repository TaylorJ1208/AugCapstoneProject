package com.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.model.Product;
import com.ecommerce.model.Review;
import com.ecommerce.repo.ProductRepo;
import com.ecommerce.repo.ReviewRepo;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewRepo reviewRepo;
	
	@Autowired
	private ProductRepo productRepo;
	
	public List<Review> getAllReviews() {
		return reviewRepo.findAll();
	}
	
	public List<Review> getReviewsByName(String name) {
		return reviewRepo.getReviewsByName(name);
	}
	
	public Optional<Review> getReviewById(Long id) {
		return reviewRepo.findById(id);
	}
	
	public void addReview(Review review) {
		Optional<Product> product = productRepo.findById(review.getProduct().getProductId());
		product.get().setRating(Math.round((review.getRating() + product.get().getRating()) / 2));;
		reviewRepo.save(review);
	}
	
	public void updateReview(Review review) {
		if(reviewRepo.findById(review.getReviewId()).isPresent()) {
			reviewRepo.save(review);
		}
	}
	
	public void deleteReview(Long id) {
		reviewRepo.deleteById(id);
	}

}
