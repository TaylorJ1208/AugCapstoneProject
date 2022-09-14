package com.ecommerce.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String QUEUE = "restock-queue";
    public static final String EXCHANGE = "restock-reminder";
	public static final String ROUTING_KEY = "foo.bar.work";

	 @Bean
	  Queue queue() {
	    return new Queue(QUEUE, true);
	  }

	  @Bean
	  TopicExchange exchange() {
	    return new TopicExchange(EXCHANGE);
	  }

	  @Bean
	  Binding binding(Queue queue, TopicExchange exchange) {
	    return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
	  }
}
