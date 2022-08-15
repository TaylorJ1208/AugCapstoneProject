package com.ecommerce.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

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
				+ "<body style='display: flex; flex-direction:column; align-items: center; justify-content: center; background-color:lightblue;"
				+ "font-family: Arial, Helvetica, sans-serif;'><div style='background-color:lightblue; padding:1em; "
				+ "width: fit-content; text-align: center; border-radius: 5em'>"
				+ "<h1>Welcome " + user.getFirstName() + " " + user.getLastName() + "</h1>"
				+ "<h2 style='font-weight:lighter'>Thank you for Joining eCommerce!<h2>" + "</div><img src='" + this.imageURL
				+ "' alternate='Image'" + "style='width:40em; height:40em; border-radius:5em'>" + "</body>" + "</html>", true);

		mailSender.send(message);
	}
}
