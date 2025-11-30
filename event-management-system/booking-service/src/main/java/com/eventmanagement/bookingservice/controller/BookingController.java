package com.eventmanagement.bookingservice.controller;

import com.eventmanagement.bookingservice.dto.BookingRequest;
import com.eventmanagement.bookingservice.dto.BookingResponse;
import com.eventmanagement.bookingservice.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    /**
     * Book a ticket
     * POST /api/bookings
     */
    @PostMapping
    public ResponseEntity<BookingResponse> bookTicket(@Valid @RequestBody BookingRequest request) {
        try {
            BookingResponse response = bookingService.bookTicket(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            BookingResponse errorResponse = BookingResponse.builder()
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    /**
     * Get ticket by ID
     * GET /api/bookings/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getTicketById(@PathVariable Long id) {
        try {
            BookingResponse response = bookingService.getTicketById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get all tickets for an attendee
     * GET /api/bookings/attendee/{attendeeId}
     */
    @GetMapping("/attendee/{attendeeId}")
    public ResponseEntity<List<BookingResponse>> getTicketsByAttendee(@PathVariable Long attendeeId) {
        return ResponseEntity.ok(bookingService.getTicketsByAttendee(attendeeId));
    }

    /**
     * Get all tickets for an event
     * GET /api/bookings/event/{eventId}
     */
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<BookingResponse>> getTicketsByEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(bookingService.getTicketsByEvent(eventId));
    }

    /**
     * Cancel ticket
     * PUT /api/bookings/{id}/cancel
     */
    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookingResponse> cancelTicket(@PathVariable Long id) {
        try {
            BookingResponse response = bookingService.cancelTicket(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    BookingResponse.builder().message(e.getMessage()).build()
            );
        }
    }

    /**
     * Refund ticket
     * PUT /api/bookings/{id}/refund
     */
    @PutMapping("/{id}/refund")
    public ResponseEntity<BookingResponse> refundTicket(@PathVariable Long id) {
        try {
            BookingResponse response = bookingService.refundTicket(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    BookingResponse.builder().message(e.getMessage()).build()
            );
        }
    }

    /**
     * Get analytics for an event
     * GET /api/bookings/analytics/{eventId}
     */
    @GetMapping("/analytics/{eventId}")
    public ResponseEntity<BookingService.EventAnalytics> getEventAnalytics(@PathVariable Long eventId) {
        return ResponseEntity.ok(bookingService.getEventAnalytics(eventId));
    }

    /**
     * Health check
     * GET /api/bookings/health
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Booking Service is running");
    }
}