package com.ecommerce.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
	@ManyToMany(mappedBy="products")
	private Set<Orders> orders = new HashSet<>();
	@ManyToOne()
	@JoinColumn(nullable=false, name="categoryId", referencedColumnName="categoryId")
	private ProductCategory category;
	
}
