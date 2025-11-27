package com.eventmanagement.eventservice.service;

import com.eventmanagement.eventservice.dto.EventRequest;
import com.eventmanagement.eventservice.factory.EventFactory;
import com.eventmanagement.eventservice.model.*;
import org.springframework.stereotype.Service;

/**
 * Service that demonstrates Factory Method Pattern usage
 * Separates factory logic from main event service
 */
@Service
public class EventFactoryService {
    public Event createEventByType(EventRequest request) {
        System.out.println("Creating " + request.getEventType() + " event");

        EventFactory factory = EventFactory.getFactory(request.getEventType());

        Event event = factory.createAndConfigureEvent();

        configureCommonProperties(event, request);
        configureTypeSpecificProperties(event, request);

        System.out.println("Event created: " + event.getTitle());
        return event;
    }

    private void configureCommonProperties(Event event, EventRequest request) {
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setEventDateTime(request.getEventDateTime());
        event.setLocation(request.getLocation());
        event.setBasePrice(request.getBasePrice());
        event.setAvailableTickets(request.getAvailableTickets());
        event.setOrganizerId(request.getOrganizerId());
        event.setStatus(EventStatus.DRAFT);
    }

    private void configureTypeSpecificProperties(Event event, EventRequest request) {
        switch (request.getEventType()) {
            case CONCERT:
                configureConcert((Concert) event, request);
                break;
            case WORKSHOP:
                configureWorkshop((Workshop) event, request);
                break;
            case CONFERENCE:
                configureConference((Conference) event, request);
                break;
        }
    }

    private void configureConcert(Concert concert, EventRequest request) {
        concert.setArtist(request.getArtist());
        concert.setGenre(request.getGenre());
        System.out.println("Concert configured: Artist=" + request.getArtist());
    }

    private void configureWorkshop(Workshop workshop, EventRequest request) {
        workshop.setInstructor(request.getInstructor());
        workshop.setMaxParticipants(request.getMaxParticipants());
        System.out.println("Workshop configured: Instructor=" + request.getInstructor());
    }

    private void configureConference(Conference conference, EventRequest request) {
        conference.setSpeakers(request.getSpeakers());
        conference.setTracks(request.getTracks());
        System.out.println("Conference configured: " +
                (request.getSpeakers() != null ? request.getSpeakers().size() : 0) + " speakers");
    }
}