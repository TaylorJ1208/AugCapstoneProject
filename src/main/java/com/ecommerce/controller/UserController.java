package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.email.EmailService;
import com.ecommerce.model.User;
import com.ecommerce.service.RoleService;
import com.ecommerce.service.UserService;
import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.MetricTelemetry;
import com.taylor.common.UserDefault;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;

	@Autowired
   	TelemetryClient telemetryClient;
	
	@Autowired
	private RoleService roleService;
	
	@GetMapping("/customer/{id}")
	public User getUser(@PathVariable Long id) {
		telemetryClient.trackEvent("/user/custumer/id Request Triggered");
		long startTime = System.nanoTime();
		User user = userService.getUserById(id);
		long endTime = System.nanoTime();
    
		MetricTelemetry benchmark = new MetricTelemetry();
		benchmark.setName("Get All User By ID Query (ms)");
		double timeInNano = endTime - startTime;
 		benchmark.setValue(timeInNano/1e6);
		telemetryClient.trackMetric(benchmark);
		return user;
	}
	
	@GetMapping("/admin")
	public List<User> getUsers() {
		telemetryClient.trackEvent("/user/admin Request Triggered");
		long startTime = System.nanoTime();
		List<User> users = userService.getAllUsers();
		long endTime = System.nanoTime();
    
		MetricTelemetry benchmark = new MetricTelemetry();
		benchmark.setName("Get All User Query (ms)");
		double timeInNano = endTime - startTime;
 		benchmark.setValue(timeInNano/1e6);
		telemetryClient.trackMetric(benchmark);
		return users;
	}
	
	@PostMapping("/customer/register")
	public void addUser(@RequestBody User user) throws Exception {
		telemetryClient.trackEvent("/user/customer/register Request Triggered");
		String encryptPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encryptPassword);
		userService.addUser(user);
		emailService.sendEmail(user);
		userService.addRoleToUser(user.getUserId(), roleService.getRoleByName(UserDefault.DEFAULT_ROLE).getRoleId());
	}
	
	@PostMapping("/admin/{userId}/role/{roleId}")
	public void addRoleToUser(@PathVariable Long userId, @PathVariable Long roleId) throws Exception {
		telemetryClient.trackEvent("/user/admin/role Request Triggered");
		userService.addRoleToUser(userId, roleId);
	}
	
	@PutMapping("/customer/update")
	public void updateUser(@RequestBody User user) {
		String encryptPassword = passwordEncoder.encode(user.getPassword());
		telemetryClient.trackEvent("/user/customer/update Request Triggered");
		user.setPassword(encryptPassword);
		userService.updateUser(user);
	}
	
	@DeleteMapping("/customer/delete/{id}")
	public void deleteUser(@PathVariable Long id) {
		telemetryClient.trackEvent("/user/customer/delete Request Triggered");
		userService.deleteUserById(id);
	}
}
