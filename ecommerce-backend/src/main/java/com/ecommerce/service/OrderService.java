package com.ecommerce.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.model.Orders;
import com.ecommerce.model.Product;
import com.ecommerce.model.UserCart;
import com.ecommerce.repo.OrdersRepo;
import com.ecommerce.repo.ProductRepo;
import com.ecommerce.repo.UserCartRepo;

@Service
public class OrderService {

	@Autowired
	private OrdersRepo ordersRepo;

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private UserCartRepo repo;
	
	@Autowired
	private UserCartService service;

	public OrderService(OrdersRepo repo2, UserCartRepo uRepo) {
		this.ordersRepo = repo2;
		this.repo = uRepo;
	}

	public List<Orders> getAllOrders() {
		return ordersRepo.findAll();
	}

	public Orders getOrderById(Long id) {
		return ordersRepo.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found with id : " + id));
	}

	public Orders addOrder(Orders order) {
		// Gets all items in cart
		List<UserCart> userCart = repo.findAll();
		BigDecimal total = new BigDecimal(0);
		// Loops through all items
		for (UserCart u : userCart) {
			// If UserCart userId == Order userId THEN add to order and delete from cart
			if (u.getUserCartId().getUserId() == order.getUser().getUserId()) {
				order.getProducts().add(u.getProduct());
				service.deleteUserCart(u.getUserCartId());
			}
		}
		
		for(Product p : order.getProducts()) {
			total = total.add(p.getPrice());
		}
		order.setAmount(total);
		return ordersRepo.save(order);
		
	}

	public Orders updateOrder(Orders order) {
		ordersRepo.findById(order.getOrderId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
				"Order not found with id : " + order.getOrderId()));
		return ordersRepo.save(order);
	}

	public void deleteOrder(Long id) {
		ordersRepo.deleteById(id);
	}

	public void addProductToOrder(Long orderId, Long productId) throws Exception {
		Orders order = ordersRepo.findById(orderId).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found with id : " + orderId));
		Product product = productRepo.findById(productId).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id : " + productId));
		order.getProducts().add(product);
		product.getOrders().add(order);
		ordersRepo.save(order);
		productRepo.save(product);
	}

}
