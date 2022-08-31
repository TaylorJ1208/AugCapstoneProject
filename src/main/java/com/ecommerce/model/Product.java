package com.ecommerce.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="productId")
	private long productId;
	// Unique to reduce possibility of same product input
	@Column(nullable=false, unique=true)
	private String name;
	@Lob
	@Column()
	private String description;
	@Column(nullable=false, precision=8, scale=2)
	private BigDecimal price;
	@Column(nullable=false, precision=6, scale=1)
	private BigDecimal weight;
	@Column(nullable=false)
	private long quantity;
	@Lob
	@Column(nullable=false)
	private String image;
	@Column(nullable=false)
	@Max(5L)
	private long rating;
	@ManyToMany(mappedBy="products", cascade = {CascadeType.ALL})
	@JsonIgnore
	List<Orders> orders;
	@ManyToOne()
	@JoinColumn(nullable=false, name="categoryId")
	private ProductCategory category;
	@OneToMany(mappedBy="product")
	@JsonIgnore
	private List<UserCart> userCart;
	
}
