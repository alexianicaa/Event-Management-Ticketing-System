package org.example.decorator;

import org.example.model.Event;

/**
 * Decorator Pattern Implementation
 * Abstract decorator class that wraps Event objects to add features dynamically
 */
public abstract class EventDecorator extends Event {

    protected Event wrappedEvent;

    public EventDecorator(Event event) {
        this.wrappedEvent = event;
        // Inherit properties from wrapped event
        this.eventId = event.getEventId();
        this.title = event.getTitle();
        this.description = event.getDescription();
        this.dateTime = event.getDateTime();
        this.location = event.getLocation();
        this.basePrice = event.getBasePrice();
        this.availableTickets = event.getAvailableTickets();
    }

    @Override
    public abstract double getPrice();

    @Override
    public abstract String getEventDetails();

    @Override
    public String getEventType() {
        return wrappedEvent.getEventType();
    }

    public Event getWrappedEvent() {
        return wrappedEvent;
    }
}

