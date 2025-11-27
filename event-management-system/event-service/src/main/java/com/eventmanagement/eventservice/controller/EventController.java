package com.eventmanagement.eventservice.controller;

import com.eventmanagement.eventservice.dto.EventRequest;
import com.eventmanagement.eventservice.dto.EventResponse;
import com.eventmanagement.eventservice.model.EventStatus;
import com.eventmanagement.eventservice.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    /**
     * Create new event
     * POST /api/events
     */
    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody EventRequest request) {
        try {
            EventResponse response = eventService.createEvent(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get event by ID with optional decorators
     * GET /api/events/{id}?vip=true&merchandise=true
     */
    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(
            @PathVariable Long id,
            @RequestParam(required = false) Boolean vip,
            @RequestParam(required = false) Boolean merchandise) {
        try {
            EventResponse response = eventService.getEventById(id, vip, merchandise);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get all events
     * GET /api/events
     */
    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    /**
     * Get events by organizer
     * GET /api/events/organizer/{organizerId}
     */
    @GetMapping("/organizer/{organizerId}")
    public ResponseEntity<List<EventResponse>> getEventsByOrganizer(@PathVariable Long organizerId) {
        return ResponseEntity.ok(eventService.getEventsByOrganizer(organizerId));
    }

    /**
     * Get published events (available for booking)
     * GET /api/events/published
     */
    @GetMapping("/published")
    public ResponseEntity<List<EventResponse>> getPublishedEvents() {
        return ResponseEntity.ok(eventService.getPublishedEvents());
    }

    /**
     * Update event status
     * PUT /api/events/{id}/status
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<EventResponse> updateEventStatus(
            @PathVariable Long id,
            @RequestParam EventStatus status) {
        try {
            EventResponse response = eventService.updateEventStatus(id, status);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete event
     * DELETE /api/events/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteEvent(@PathVariable Long id) {
        try {
            eventService.deleteEvent(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Event deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update available tickets
     * PUT /api/events/{id}/tickets
     */
    @PutMapping("/{id}/tickets")
    public ResponseEntity<Map<String, String>> updateTickets(
            @PathVariable Long id,
            @RequestParam Integer reduce) {
        try {
            eventService.updateAvailableTickets(id, reduce);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Tickets updated successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * Health check
     * GET /api/events/health
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Event Service is running");
    }
}