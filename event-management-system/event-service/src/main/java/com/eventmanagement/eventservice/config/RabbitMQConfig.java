package com.eventmanagement.eventservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Queue names - must match booking service
    public static final String TICKET_QUEUE = "ticket.queue";

    // Exchange name - must match booking service
    public static final String BOOKING_EXCHANGE = "booking.exchange";

    // Routing key - must match booking service
    public static final String TICKET_ROUTING_KEY = "ticket.booked";

    // Declare Exchange
    @Bean
    public TopicExchange bookingExchange() {
        return new TopicExchange(BOOKING_EXCHANGE);
    }

    // Declare Queue
    @Bean
    public Queue ticketQueue() {
        return new Queue(TICKET_QUEUE, true); // durable = true
    }

    // Bind Queue to Exchange
    @Bean
    public Binding ticketBinding(Queue ticketQueue, TopicExchange bookingExchange) {
        return BindingBuilder
                .bind(ticketQueue)
                .to(bookingExchange)
                .with(TICKET_ROUTING_KEY);
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