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
	private String secretKey = "sk_test_51LflSODncq9KJa81wkyR9lv5ThzXjDzFgDBXqKpMpNnioBJreCd4VbnTrZQHjRBCIA5u953LQl8kg2pbwFnUvnR6001FXYongm";

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
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
