package com.eventmanagement.eventservice.service;

import com.eventmanagement.eventservice.client.UserServiceClient;
import com.eventmanagement.eventservice.decorator.EventDecorator;
import com.eventmanagement.eventservice.decorator.MerchandiseBundleDecorator;
import com.eventmanagement.eventservice.decorator.VIPAccessDecorator;
import com.eventmanagement.eventservice.dto.*;
import com.eventmanagement.eventservice.factory.EventFactory;
import com.eventmanagement.eventservice.model.*;
import com.eventmanagement.eventservice.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final UserServiceClient userServiceClient;
    private final EventFactoryService eventFactoryService;

    public EventResponse createEvent(EventRequest request) {
        // Validate organizer
        System.out.println("Validating organizer with ID: " + request.getOrganizerId());
        UserValidationResponse validation = userServiceClient.validateOrganizer(request.getOrganizerId());

        if (!validation.getHasRole()) {
            throw new RuntimeException("User is not an organizer");
        }

        // Use EventFactoryService to create event
        Event event = eventFactoryService.createEventByType(request);

        // Save event
        Event savedEvent = eventRepository.save(event);
        System.out.println("Event saved with ID: " + savedEvent.getId());

        return mapToResponse(savedEvent);
    }

    public EventResponse getEventById(Long id, Boolean applyVip, Boolean applyMerchandise) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));

        // Apply decorators if requested
        if (Boolean.TRUE.equals(applyVip) || Boolean.TRUE.equals(applyMerchandise)) {
            return applyDecorators(event, applyVip, applyMerchandise);
        }

        return mapToResponse(event);
    }

    private EventResponse applyDecorators(Event event, Boolean applyVip, Boolean applyMerchandise) {
        System.out.println("Applying decorators to event: " + event.getTitle());

        EventDecorator decoratedEvent = null;

        // Wrap event with decorators
        if (Boolean.TRUE.equals(applyVip)) {
            decoratedEvent = new VIPAccessDecorator(event);
        }

        if (Boolean.TRUE.equals(applyMerchandise)) {
            Event baseEvent = decoratedEvent != null ? decoratedEvent.getWrappedEvent() : event;
            decoratedEvent = new MerchandiseBundleDecorator(baseEvent);
        }

        // If decorators were applied, use decorated price
        if (decoratedEvent != null) {
            EventResponse response = mapToResponse(event);
            response.setFinalPrice(decoratedEvent.getFinalPrice());
            response.setHasVipAccess(applyVip);
            response.setHasMerchandise(applyMerchandise);
            System.out.println("Final price after decorators: $" + decoratedEvent.getFinalPrice());
            return response;
        }

        return mapToResponse(event);
    }

    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<EventResponse> getEventsByOrganizer(Long organizerId) {
        return eventRepository.findByOrganizerId(organizerId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<EventResponse> getPublishedEvents() {
        return eventRepository.findByStatus(EventStatus.PUBLISHED).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public EventResponse updateEventStatus(Long id, EventStatus status) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        event.setStatus(status);
        Event updated = eventRepository.save(event);

        System.out.println("Event status updated to: " + status);
        return mapToResponse(updated);
    }

    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        eventRepository.delete(event);
        System.out.println("Event deleted: " + id);
    }

    /**
     * Update available tickets
     */
    public void updateAvailableTickets(Long eventId, Integer ticketsToReduce) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        int newAvailable = event.getAvailableTickets() - ticketsToReduce;
        if (newAvailable < 0) {
            throw new RuntimeException("Not enough tickets available");
        }

        event.setAvailableTickets(newAvailable);
        eventRepository.save(event);
        System.out.println("Tickets updated. Remaining: " + newAvailable);
    }

    private EventResponse mapToResponse(Event event) {
        EventResponse response = EventResponse.builder()
                .id(event.getId())
                .eventType(event.getEventType())
                .title(event.getTitle())
                .description(event.getDescription())
                .eventDateTime(event.getEventDateTime())
                .location(event.getLocation())
                .basePrice(event.getBasePrice())
                .finalPrice(event.getBasePrice())
                .availableTickets(event.getAvailableTickets())
                .organizerId(event.getOrganizerId())
                .status(event.getStatus())
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .hasVipAccess(false)
                .hasMerchandise(false)
                .build();

        // Add type-specific fields
        if (event instanceof Concert) {
            Concert concert = (Concert) event;
            response.setArtist(concert.getArtist());
            response.setGenre(concert.getGenre());
        } else if (event instanceof Workshop) {
            Workshop workshop = (Workshop) event;
            response.setInstructor(workshop.getInstructor());
            response.setMaxParticipants(workshop.getMaxParticipants());
        } else if (event instanceof Conference) {
            Conference conference = (Conference) event;
            response.setSpeakers(conference.getSpeakers());
            response.setTracks(conference.getTracks());
        }

        return response;
    }
}