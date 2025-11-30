package com.eventmanagement.bookingservice.messaging;

import com.eventmanagement.bookingservice.config.RabbitMQConfig;
import com.eventmanagement.bookingservice.dto.TicketBookedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    /**
     * Publish ticket booked event to RabbitMQ
     * This event will be consumed by Event Service to update ticket availability
     */
    public void publishTicketBookedEvent(TicketBookedEvent event) {
        System.out.println("Publishing TICKET_BOOKED event to RabbitMQ");
        System.out.println("   Exchange: " + RabbitMQConfig.BOOKING_EXCHANGE);
        System.out.println("   Routing Key: " + RabbitMQConfig.TICKET_ROUTING_KEY);
        System.out.println("   Event ID: " + event.getEventId());

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.BOOKING_EXCHANGE,
                RabbitMQConfig.TICKET_ROUTING_KEY,
                event
        );

        System.out.println("Event published successfully!");
    }

    /**
     * Publish email notification event (optional - for demonstration)
     */
    public void publishEmailNotification(TicketBookedEvent event) {
        System.out.println("Publishing EMAIL_NOTIFICATION event to RabbitMQ");

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.BOOKING_EXCHANGE,
                RabbitMQConfig.EMAIL_ROUTING_KEY,
                event
        );

        System.out.println("Email notification event published!");
    }
}