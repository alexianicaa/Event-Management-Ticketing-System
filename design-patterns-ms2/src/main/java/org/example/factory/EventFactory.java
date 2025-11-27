package org.example.factory;

import org.example.model.Concert;
import org.example.model.Conference;
import org.example.model.Event;
import org.example.model.Workshop;

import java.time.LocalDateTime;

/**
 * Factory Method Pattern Implementation
 * Abstract factory for creating different types of events
 */
public abstract class EventFactory {
    public abstract Event createEvent();

    public Event createAndConfigureEvent(String title, String description,
                                         LocalDateTime dateTime, String location,
                                         double basePrice, int availableTickets) {
        Event event = createEvent();
        event.setTitle(title);
        event.setDescription(description);
        event.setDateTime(dateTime);
        event.setLocation(location);
        event.setBasePrice(basePrice);
        event.setAvailableTickets(availableTickets);

        System.out.println("Created and configured " + event.getEventType() + ": " + title);
        return event;
    }

    public static EventFactory getFactory(String eventType) {
        return switch (eventType.toLowerCase()) {
            case "concert" -> new ConcertFactory();
            case "workshop" -> new WorkshopFactory();
            case "conference" -> new ConferenceFactory();
            default -> throw new IllegalArgumentException("Unknown event type: " + eventType);
        };
    }
}


