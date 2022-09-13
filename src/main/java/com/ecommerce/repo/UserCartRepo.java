package com.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.model.UserCart;
import com.ecommerce.model.UserCartId;

@Repository
public interface UserCartRepo extends JpaRepository<UserCart, UserCartId>{

}
