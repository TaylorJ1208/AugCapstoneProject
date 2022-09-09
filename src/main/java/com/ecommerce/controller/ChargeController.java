package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.model.ChargeRequest;
import com.ecommerce.model.ChargeRequest.Currency;
import com.ecommerce.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

@RestController
@RequestMapping("/stripe")
public class ChargeController {

    @Autowired
    private StripeService paymentsService;
    
	@Value("pk_test_51LflSODncq9KJa816vSKNjdic3V39Y1HZ0ZvX4TmkpoqUdMGFmb9xBklhywlxvwcFPqyiKn0xOlxou0NJkaORrnw00SACxUFmk")
    private String stripePublicKey;

    @PostMapping("/charge")
    public void charge(@RequestBody ChargeRequest cr, Model model)
      throws StripeException {
        cr.setDescription("Order");
        cr.setCurrency(Currency.USD);
        Charge charge1 = paymentsService.charge(cr);
        model.addAttribute("id", charge1.getId());
        model.addAttribute("status", charge1.getStatus());
        model.addAttribute("chargeId", charge1.getId());
    }

    @ExceptionHandler(StripeException.class)
    public void handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
    }
}