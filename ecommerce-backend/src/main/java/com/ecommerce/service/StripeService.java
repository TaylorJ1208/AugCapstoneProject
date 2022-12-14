package com.ecommerce.service;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import com.ecommerce.model.ChargeRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

@Service
public class StripeService {
	
	String STRIPE_SK= "sk_test_51LflSODncq9KJa81wkyR9lv5ThzXjDzFgDBXqKpMpNnioBJreCd4VbnTrZQHjRBCIA5u953LQl8kg2pbwFnUvnR6001FXYongm";
	String STRIPE_PK= "pk_test_51LflSODncq9KJa816vSKNjdic3V39Y1HZ0ZvX4TmkpoqUdMGFmb9xBklhywlxvwcFPqyiKn0xOlxou0NJkaORrnw00SACxUFmk";

    @PostConstruct
    public void init() {
        Stripe.apiKey = STRIPE_SK;
    }

	public Charge charge(ChargeRequest cr) throws StripeException {
		Map<String, Object> chargeParams = new HashMap<>();
		chargeParams.put("amount", cr.getAmount() * 100);
		chargeParams.put("currency", cr.getCurrency());
		chargeParams.put("description", cr.getDescription());
		chargeParams.put("source", cr.getSource());
		
		return Charge.create(chargeParams);
	}

}