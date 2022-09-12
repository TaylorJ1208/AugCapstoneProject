package com.ecommerce.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="userId")
	private long userId; 
	@Column(nullable=false, length = 100)
	private String firstName;
	@Column(nullable=false, length = 100)
	private String lastName;
	@Column(nullable=false, unique=true, length = 255)
	private String email;
	@Column(nullable=false, length = 20)
	private String userName;
	@Column(nullable=false, length = 100)
	private String password;
	@Column(nullable=false, length = 12)
	private String contact;
	@Column(nullable=false, length = 4)
	private String ssn;
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Orders> orders;
	// M2M relationship between Role
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="user_role",
			joinColumns= {@JoinColumn(name="userId")},
			inverseJoinColumns= {@JoinColumn(name="roleId")})
	private Set<Role> roles;
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Address> addresses;
	@OneToMany(mappedBy="user")
	@JsonIgnore
	private List<UserCart> userCart;
	
}
