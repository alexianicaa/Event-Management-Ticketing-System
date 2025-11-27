package com.eventmanagement.eventservice.decorator;

import com.eventmanagement.eventservice.model.Event;
import com.eventmanagement.eventservice.model.EventType;
import lombok.Getter;

/**
 * DECORATOR PATTERN
 *
 * Abstract decorator that wraps Event objects to add features dynamically.
 * Allows adding features like VIP access and merchandise bundles
 * without modifying the base Event class.
 */
@Getter
public abstract class EventDecorator {

    protected Event wrappedEvent;
    protected String featureName;
    protected Double additionalPrice;

    public EventDecorator(Event event) {
        this.wrappedEvent = event;
        this.additionalPrice = 0.0;
    }

    public abstract double getFinalPrice();

    public abstract String getEventDetails();

    public Event getBaseEvent() {
        return wrappedEvent;
    }

    public EventType getEventType() {
        return wrappedEvent.getEventType();
    }
}