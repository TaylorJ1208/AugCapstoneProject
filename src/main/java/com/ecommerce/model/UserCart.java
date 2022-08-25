package com.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_cart")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCart {
	
	@EmbeddedId
	private UserCartId userCartId;
	
    @ManyToOne
    @MapsId("userId")
    private User user;
    
    @ManyToOne
    @MapsId("productId")
    private Product product;
    
    @Column()
    private int quantity;
}