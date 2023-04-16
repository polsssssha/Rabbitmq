package com.example.Rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class RabbitmqApplication {
	private static final String EXCHANGE_NAME = "message_exchange";
	public static void main(String[] args) {
		SpringApplication.run(RabbitmqApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(AmqpTemplate template) {
		return args -> {
			Scanner scanner = new Scanner(System.in);
			while (true) {
				System.out.print("Enter message: ");
				String message = scanner.nextLine();
				template.convertAndSend(EXCHANGE_NAME, "", message);
			}
		};
	}
	@Bean
	public Queue queue() {
		return new AnonymousQueue();
	}

	@Bean
	public FanoutExchange exchange() {
		return new FanoutExchange(EXCHANGE_NAME);
	}

	@Bean
	public Binding binding(Queue queue, FanoutExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange);
	}

	@Bean
	public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
													MessageListenerAdapter listenerAdapter,
													Queue queue) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueues(queue);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	public MessageListenerAdapter listenerAdapter(MessageReceiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	@Bean
	public MessageReceiver receiver() {
		return new MessageReceiver();
	}
}