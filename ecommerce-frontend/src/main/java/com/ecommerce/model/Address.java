package com.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="address")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Address {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long addressId;
	@Column(length=45, nullable=false)
	private String city;
	@Column(length=2, nullable=false)
	private String state;
	@Column(length=45, nullable=false)
	private String street;
	@Column(length=5, nullable=false)
	private String zipcode;
	@Column(length=45, nullable=false)
	private String country;
	@Column(length=45)
	private String apartmentNumber;
	// M2O relationship between User entity and Address entity
	@ManyToOne()
	@JoinColumn(nullable=false, name="userId")
	private User user;
}
