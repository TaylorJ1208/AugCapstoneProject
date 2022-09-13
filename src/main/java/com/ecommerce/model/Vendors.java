package com.ecommerce.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="vendors")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Vendors {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long vendorId;
	@Column(length=45, nullable=false)
	private String email;
	@Column(length=45, nullable=false)
	private String name;
	// M2O relationship between User entity and Address entity
	/*
	 * @OneToMany(mappedBy="vendors", cascade = CascadeType.ALL)
	 * 
	 * @JsonIgnore private List<Product> products;// HAS A
	 */}
