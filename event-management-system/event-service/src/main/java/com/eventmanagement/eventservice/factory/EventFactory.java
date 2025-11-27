package com.eventmanagement.eventservice.factory;

import com.eventmanagement.eventservice.model.Event;
import com.eventmanagement.eventservice.model.EventType;

/**
 * FACTORY METHOD PATTERN
 *
 * Abstract factory for creating different event types.
 * Each concrete factory (ConcertFactory, WorkshopFactory, ConferenceFactory)
 * implements the createEvent() method to instantiate specific event types.
 */
public abstract class EventFactory {

    public abstract Event createEvent();

    public Event createAndConfigureEvent() {
        Event event = createEvent();
        System.out.println("Factory: Created " + event.getEventType() + " event");
        return event;
    }

    public static EventFactory getFactory(EventType eventType) {
        System.out.println("Getting factory for: " + eventType);

        switch (eventType) {
            case CONCERT:
                return new ConcertFactory();
            case WORKSHOP:
                return new WorkshopFactory();
            case CONFERENCE:
                return new ConferenceFactory();
            default:
                throw new IllegalArgumentException("Unknown event type: " + eventType);
        }
    }
}