package com.ecommerce.sms;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.ecommerce.model.Orders;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

@Service
public class SMSService {

	private final Environment env;
    
    public SMSService(Environment env) {
    	this.env = env;
    }

    public void sendMessage(Orders order) {
        Twilio.init(env.getProperty("ACCOUNT_SID"), env.getProperty("AUTH_TOKEN"));
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+1" + order.getUser().getContact()), //to
                new com.twilio.type.PhoneNumber("+13862603054"), // from
                "Your Order On " + order.getOrderDate() + " Has Successfully Been Placed")
            .create();

        System.out.println(message.getSid());
    }
}