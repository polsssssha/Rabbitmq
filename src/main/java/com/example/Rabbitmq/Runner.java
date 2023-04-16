package com.example.Rabbitmq;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner{
    private final RabbitTemplate rabbitTemplate;
    private final MessageReceiver receiver;

    public Runner(MessageReceiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter message:");
            String message = scanner.next();
            rabbitTemplate.convertAndSend(RabbitmqApplication.fanoutExchangeName, "", message);
            receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
            receiver.updateLatch();
        }
    }
}
