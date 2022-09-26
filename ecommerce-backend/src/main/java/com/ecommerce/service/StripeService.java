package com.ecommerce.service;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import com.ecommerce.model.ChargeRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import io.github.cdimascio.dotenv.Dotenv;

@Service
public class StripeService {
	

	Dotenv dotenv = Dotenv.load();

    @PostConstruct
    public void init() {
        Stripe.apiKey = dotenv.get("STRIPE_SK");
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