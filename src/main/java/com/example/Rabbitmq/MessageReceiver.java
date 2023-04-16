package com.example.Rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;

public class MessageReceiver {
    @RabbitHandler
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }
}
