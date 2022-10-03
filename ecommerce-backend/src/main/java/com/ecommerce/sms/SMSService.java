package com.ecommerce.sms;

import org.springframework.stereotype.Service;

import com.ecommerce.model.Orders;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

@Service
public class SMSService {
    public static final String ACCOUNT_SID = "AC0e3ca7912f7dcf5cf998a60a27914783";
    public static final String AUTH_TOKEN = "a2393dcd1344384e75e1ef2be8783e17";

    public void sendMessage(Orders order) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+" + order.getUser().getContact()), //to
                new com.twilio.type.PhoneNumber("+13862603054"), // from
                "Your Order On " + order.getOrderDate() + " Has Successfully Been Placed")
            .create();

        System.out.println(message.getSid());
    }
}