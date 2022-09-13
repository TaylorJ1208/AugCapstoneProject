package com.ecommerce.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="orderId")
	private long orderId;
	@Column(nullable=false, precision=8, scale=2)
	private BigDecimal amount;
	@Column(nullable=false)
	private Date orderDate;
	@Column(name="status")
	private boolean status;
	@ManyToOne()
	@JoinColumn(nullable=false, name="userId", referencedColumnName="userId")
	private User user;
	@Column(nullable=false, length=100)
	private String billingAddress;
	@Column(nullable=false, length=100)
	private String shippingAddress;
	@ManyToMany()
	@JoinTable(name="orders_product",
	joinColumns= @JoinColumn(name="productId"),
	inverseJoinColumns= @JoinColumn(name="ordersId"))
	List<Product> products;
}
