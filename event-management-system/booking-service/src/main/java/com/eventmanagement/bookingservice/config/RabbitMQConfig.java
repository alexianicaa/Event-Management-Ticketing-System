package com.eventmanagement.bookingservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Queue names
    public static final String TICKET_QUEUE = "ticket.queue";
    public static final String EMAIL_QUEUE = "email.queue";

    // Exchange name
    public static final String BOOKING_EXCHANGE = "booking.exchange";

    // Routing keys
    public static final String TICKET_ROUTING_KEY = "ticket.booked";
    public static final String EMAIL_ROUTING_KEY = "email.notification";

    // Declare Exchange
    @Bean
    public TopicExchange bookingExchange() {
        return new TopicExchange(BOOKING_EXCHANGE);
    }

    // Declare Queues
    @Bean
    public Queue ticketQueue() {
        return new Queue(TICKET_QUEUE, true); // durable = true
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE, true);
    }

    // Bind Queues to Exchange with Routing Keys
    @Bean
    public Binding ticketBinding(Queue ticketQueue, TopicExchange bookingExchange) {
        return BindingBuilder
                .bind(ticketQueue)
                .to(bookingExchange)
                .with(TICKET_ROUTING_KEY);
    }

    @Bean
    public Binding emailBinding(Queue emailQueue, TopicExchange bookingExchange) {
        return BindingBuilder
                .bind(emailQueue)
                .to(bookingExchange)
                .with(EMAIL_ROUTING_KEY);
    }

    // Message Converter (JSON)
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // RabbitTemplate with JSON converter
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}