package org.example;

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

class ConcertFactory extends EventFactory {
    @Override
    public Event createEvent() {
        System.out.println("ConcertFactory: Creating a new Concert");
        Concert concert = new Concert();
        concert.setGenre("General"); // default value
        return concert;
    }

    public Concert createConcertWithArtist(String artist, String genre) {
        Concert concert = (Concert) createEvent();
        concert.setArtist(artist);
        concert.setGenre(genre);
        return concert;
    }
}

class WorkshopFactory extends EventFactory {
    @Override
    public Event createEvent() {
        System.out.println("WorkshopFactory: Creating a new Workshop");
        Workshop workshop = new Workshop();
        workshop.setMaxParticipants(30); // default value
        return workshop;
    }

    public Workshop createWorkshopWithInstructor(String instructor, int maxParticipants) {
        Workshop workshop = (Workshop) createEvent();
        workshop.setInstructor(instructor);
        workshop.setMaxParticipants(maxParticipants);
        return workshop;
    }
}

class ConferenceFactory extends EventFactory {
    @Override
    public Event createEvent() {
        System.out.println("ConferenceFactory: Creating a new Conference");
        Conference conference = new Conference();
        conference.setSpeakers(new String[]{});
        conference.setTracks(new String[]{"General"});
        return conference;
    }

    public Conference createConferenceWithSpeakers(String[] speakers, String[] tracks) {
        Conference conference = (Conference) createEvent();
        conference.setSpeakers(speakers);
        conference.setTracks(tracks);
        return conference;
    }
}