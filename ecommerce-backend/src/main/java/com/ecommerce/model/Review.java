package com.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "review")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long reviewId;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "review", nullable = false)
	private String review;
	
	@Column(name = "rating", nullable = false)
	@Max(5L)
	private int rating;
	
	@ManyToOne()
	@JoinColumn(nullable=false, name="productId")
	private Product product;
}
