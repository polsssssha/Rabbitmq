package com.example.Rabbitmq;

import java.util.concurrent.CountDownLatch;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {

    private static final int count = 1;
    private CountDownLatch latch = new CountDownLatch(count);

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public void updateLatch() {
        this.latch = new CountDownLatch(count);
    }
}