package com.eventmanagement.eventservice.messaging;

import com.eventmanagement.eventservice.config.RabbitMQConfig;
import com.eventmanagement.eventservice.dto.TicketBookedEvent;
import com.eventmanagement.eventservice.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketEventListener {

    private final EventService eventService;

    /**
     * Listen for ticket booked events from RabbitMQ
     * Automatically updates event ticket availability
     */
    @RabbitListener(queues = RabbitMQConfig.TICKET_QUEUE)
    public void handleTicketBookedEvent(TicketBookedEvent event) {
        System.out.println("\n=== RECEIVED TICKET_BOOKED EVENT ===");
        System.out.println("   Queue: " + RabbitMQConfig.TICKET_QUEUE);
        System.out.println("   Ticket ID: " + event.getTicketId());
        System.out.println("   Event ID: " + event.getEventId());
        System.out.println("   Attendee: " + event.getAttendeeEmail());
        System.out.println("   Price: $" + event.getPrice());

        try {
            // Update event tickets asynchronously
            eventService.updateAvailableTickets(event.getEventId(), 1);

            System.out.println("Event tickets updated successfully!");
        } catch (Exception e) {
            System.err.println("Failed to update event tickets: " + e.getMessage());
        }
    }
}