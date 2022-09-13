package com.ecommerce.rabbitmq;

import java.util.concurrent.CountDownLatch;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.ecommerce.config.RabbitConfig;

@Component
public class Receiver {

  private CountDownLatch latch = new CountDownLatch(1);

  @RabbitListener(queues = RabbitConfig.QUEUE)
  public void receiveMessage(String orderStatus) {
      System.out.println("Message recieved from queue : " + orderStatus);
  }
  
  public CountDownLatch getLatch() {
    return latch;
  }

}