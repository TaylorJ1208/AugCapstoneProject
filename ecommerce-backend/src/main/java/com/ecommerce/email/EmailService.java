package com.ecommerce.email;

import java.math.BigDecimal;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ecommerce.model.Orders;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;

@Service
public class EmailService {
	
	@Autowired
	private EmailConfig emailConfig;
	private String imageURL = 
		"https://res.cloudinary.com/drukcz14j/image/upload/v1660239854/samples/ecommerce-logo_lpz0mu.jpg";
	
	public void sendEmail(User user) throws MessagingException {

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost(this.emailConfig.getHost());
		mailSender.setPort(this.emailConfig.getPort());
		mailSender.setUsername(this.emailConfig.getUsername());
		mailSender.setPassword(this.emailConfig.getPassword());

		MimeMessage message = mailSender.createMimeMessage();


		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("eCommerce@hcl.com");
		helper.setTo(user.getEmail());
		helper.setSubject("Welcome to eCommerce!");
		helper.setText("<html>"
				+ "<body style='display: flex; flex-direction:column; align-items: center; justify-content: center; background-color: cornsilk;"
				+ "font-family: Arial, Helvetica, sans-serif;'><div style='background-color: cornsilk; padding:1em; "
				+ "width: fit-content; text-align: center; border-radius: 5em'>"
				+ "<h1>Welcome " + user.getFirstName() + " " + user.getLastName() + "</h1>"
				+ "<h2 style='font-weight:lighter'>Thank you for Joining eCommerce!<h2>" + "</div><img src='" + this.imageURL
				+ "' alternate='Image'" + "style='width:40em; height:40em; border-radius:5em'>" + "</body>" + "</html>", true);

		mailSender.send(message);
	}
	
	public void sendReceipt(Orders order) throws MessagingException {
		List<Product> products = order.getProducts();
		String productString = "";
		String imgUrl = "https://res.cloudinary.com/drukcz14j/image/upload/v1661205170/ecommerce/238-2381636_happy-face-color-in-smiley-face-hd-png_gyzizn.png";
		BigDecimal productTotal = new BigDecimal(0);
		int totalItems = 0;
		for(Product p : products) {
			totalItems++;
			productTotal = productTotal.add(p.getPrice());
			productString += p.getName() + "  "; 
		}
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost(this.emailConfig.getHost());
		mailSender.setPort(this.emailConfig.getPort());
		mailSender.setUsername(this.emailConfig.getUsername());
		mailSender.setPassword(this.emailConfig.getPassword());

		MimeMessage message = mailSender.createMimeMessage();


		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("eCommerce@hcl.com");
		helper.setTo(order.getUser().getEmail());
		helper.setSubject("Thank you for your purchase!");
		helper.setText("<html>"
				+ "<body style='margin-left: 5em; background-color:cornsilk;"
				+ "font-family: Arial, Helvetica, sans-serif;'><div>\r\n"
				+ "  <h5> Thank you for your order, " + order.getUser().getFirstName() + "</h5>\r\n"
				+ "<img style='width: 18em; border-radium: 15px;' src='" + imgUrl + "' alt='Picture of a smiley face'>"
				+ "  <h5>Order details</h5>\r\n"
				+ "  <div>\r\n"
				+ "    <p>Products : " + productString + "</p> \r\n"
				+ "    <p>Total items : " + totalItems + "</p>\r\n"
				+ "    <p>Shipping to : " + order.getShippingAddress() + "</p>\r\n"
				+ "    <p>Billed to : " + order.getBillingAddress() + "</p>\r\n"
				+ "    <p>Total Price : <span style='font-weight:bold'>$" + productTotal + "</span></p>\r\n"
				+ "  </div>\r\n"
				+ "</div>" + "</body>" + "</html>", true);

		mailSender.send(message);
	}
	
	public void sendVendorEmail(String vendorEmail, Product product, int amount) throws MessagingException {

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost(this.emailConfig.getHost());
		mailSender.setPort(this.emailConfig.getPort());
		mailSender.setUsername(this.emailConfig.getUsername());
		mailSender.setPassword(this.emailConfig.getPassword());

		MimeMessage message = mailSender.createMimeMessage();


		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("eCommerce@hcl.com");
		helper.setTo(product.getVendors().getEmail());
		helper.setSubject("Restocking eCommerce!");
		helper.setText("<html>"
				+ "<body style='display: flex; flex-direction:column; align-items: center; justify-content: center; background-color: cornsilk;"
				+ "font-family: Arial, Helvetica, sans-serif;'><div style='background-color: cornsilk; padding:1em; "
				+ "width: fit-content; text-align: center; border-radius: 5em'>"
				+ "<h1>Hello " + product.getVendors().getName() + "</h1>"
				+ "<h2 style='font-weight:lighter'>Stock on the product you supply is currently running low or out of stock. "
				+ "<br>Product details: <br>"
				+"<br> Name: "+product.getName()
				+"<br> Price: "+product.getPrice() 
				+ "<br> Please ship " + amount + " units.<h2>" + "</div><img src='" + this.imageURL
				+ "' alternate='Image'" + "style='width:40em; height:40em; border-radius:5em'>" + "</body>" + "</html>", true);

		System.out.println("Sending email to " + product.getVendors().getName()+" at " +product.getVendors().getEmail()+" .\n Product details:"
				+"\n Name: "+product.getName()
				+"\n Price: "+product.getPrice() 
				
				+"\n Please ship " + amount +" units.");
		
		mailSender.send(message);
	}

	public void sendLowStockEmail(String msg) throws MessagingException {

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost(this.emailConfig.getHost());
		mailSender.setPort(this.emailConfig.getPort());
		mailSender.setUsername(this.emailConfig.getUsername());
		mailSender.setPassword(this.emailConfig.getPassword());

		MimeMessage message = mailSender.createMimeMessage();


		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("eCommerce@hcl.com");
		helper.setTo("eCommerce@hcl.com");
		helper.setSubject("Low Stock - eCommerce!");
		helper.setText("<html>"
				+ "<body style='display: flex; flex-direction:column; align-items: center; justify-content: center; background-color: cornsilk;"
				+ "font-family: Arial, Helvetica, sans-serif;'><div style='background-color: cornsilk; padding:1em; "
				+ "width: fit-content; text-align: center; border-radius: 5em'>"
				+ "<h1>Hello,</h1>"
				+ "<h2 style='font-weight:lighter'>" +msg+"<h2>" + "</div><img src='" + this.imageURL
				+ "' alternate='Image'" + "style='width:40em; height:40em; border-radius:5em'>" + "</body>" + "</html>", true);

		mailSender.send(message);
	}
}
