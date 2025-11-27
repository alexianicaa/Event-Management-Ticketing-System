package com.eventmanagement.bookingservice.service;

import com.eventmanagement.bookingservice.client.EventServiceClient;
import com.eventmanagement.bookingservice.client.UserServiceClient;
import com.eventmanagement.bookingservice.dto.*;
import com.eventmanagement.bookingservice.model.Ticket;
import com.eventmanagement.bookingservice.model.TicketStatus;
import com.eventmanagement.bookingservice.observer.AnalyticsObserver;
import com.eventmanagement.bookingservice.observer.EmailNotificationObserver;
import com.eventmanagement.bookingservice.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final TicketRepository ticketRepository;
    private final UserServiceClient userServiceClient;
    private final EventServiceClient eventServiceClient;
    private final PaymentService paymentService;
    private final QRCodeService qrCodeService;

    @Transactional
    public BookingResponse bookTicket(BookingRequest request) {
        System.out.println("\nStarting booking process...");

        // Validate user exists
        System.out.println("Validating attendee...");
        UserResponse user = userServiceClient.getUserById(request.getAttendeeId());

        // Get event details
        System.out.println("Fetching event details...");
        EventResponse event = eventServiceClient.getEventById(
                request.getEventId(),
                request.getWithVip(),
                request.getWithMerchandise()
        );

        // Check if tickets are available
        if (event.getAvailableTickets() <= 0) {
            throw new RuntimeException("No tickets available for this event");
        }

        // Process payment using Strategy Pattern
        double finalPrice = event.getFinalPrice() != null ? event.getFinalPrice() : event.getBasePrice();
        System.out.println("Processing payment for $" + finalPrice);

        boolean paymentSuccess = paymentService.processPayment(request.getPayment(), finalPrice);

        if (!paymentSuccess) {
            throw new RuntimeException("Payment failed");
        }

        // Create ticket
        Ticket ticket = Ticket.builder()
                .eventId(request.getEventId())
                .attendeeId(request.getAttendeeId())
                .attendeeEmail(user.getEmail())
                .price(finalPrice)
                .status(TicketStatus.BOOKED)
                .paymentMethod(request.getPayment().getPaymentMethod())
                .build();

        Ticket savedTicket = ticketRepository.save(ticket);

        // Generate QR code
        String qrCode = qrCodeService.generateQRCode(
                savedTicket.getId(),
                savedTicket.getEventId(),
                savedTicket.getAttendeeId()
        );
        savedTicket.setQrCode(qrCode);
        savedTicket = ticketRepository.save(savedTicket);

        System.out.println("Booking Service: Attaching observers...");
        savedTicket.getSubject().attach(new EmailNotificationObserver());
        savedTicket.getSubject().attach(new AnalyticsObserver());

        System.out.println("Booking Service: Confirming ticket...");
        savedTicket.updateStatus(TicketStatus.CONFIRMED);
        savedTicket = ticketRepository.save(savedTicket);

        // Update available tickets in Event Service
        try {
            eventServiceClient.updateTickets(request.getEventId(), 1);
            System.out.println("Booking Service: Event tickets updated");
        } catch (Exception e) {
            System.err.println("Booking Service: Failed to update event tickets: " + e.getMessage());
        }

        System.out.println("Booking completed successfully!");

        return mapToResponse(savedTicket);
    }

    public BookingResponse getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));
        return mapToResponse(ticket);
    }

    public List<BookingResponse> getTicketsByAttendee(Long attendeeId) {
        return ticketRepository.findByAttendeeId(attendeeId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<BookingResponse> getTicketsByEvent(Long eventId) {
        return ticketRepository.findByEventId(eventId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookingResponse cancelTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (ticket.getStatus() == TicketStatus.CANCELLED || ticket.getStatus() == TicketStatus.REFUNDED) {
            throw new RuntimeException("Ticket is already cancelled or refunded");
        }

        System.out.println("Cancelling ticket: " + ticketId);

        ticket.getSubject().attach(new EmailNotificationObserver());
        ticket.getSubject().attach(new AnalyticsObserver());

        ticket.updateStatus(TicketStatus.CANCELLED);

        // Return ticket to event inventory
        try {
            eventServiceClient.updateTickets(ticket.getEventId(), -1); // Add back 1 ticket
            System.out.println("Ticket returned to inventory");
        } catch (Exception e) {
            System.err.println("Failed to return ticket: " + e.getMessage());
        }

        Ticket saved = ticketRepository.save(ticket);
        return mapToResponse(saved);
    }

    @Transactional
    public BookingResponse refundTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (ticket.getStatus() != TicketStatus.CANCELLED) {
            throw new RuntimeException("Ticket must be cancelled before refunding");
        }

        System.out.println("Processing refund for ticket: " + ticketId);

        ticket.getSubject().attach(new EmailNotificationObserver());
        ticket.getSubject().attach(new AnalyticsObserver());

        ticket.updateStatus(TicketStatus.REFUNDED);

        Ticket saved = ticketRepository.save(ticket);
        return mapToResponse(saved);
    }

    public EventAnalytics getEventAnalytics(Long eventId) {
        Long totalTickets = ticketRepository.countByEventId(eventId);
        Long confirmedTickets = ticketRepository.countByEventIdAndStatus(eventId, TicketStatus.CONFIRMED);
        Long cancelledTickets = ticketRepository.countByEventIdAndStatus(eventId, TicketStatus.CANCELLED);

        List<Ticket> tickets = ticketRepository.findByEventId(eventId);
        double totalRevenue = tickets.stream()
                .filter(t -> t.getStatus() == TicketStatus.CONFIRMED)
                .mapToDouble(Ticket::getPrice)
                .sum();

        return EventAnalytics.builder()
                .eventId(eventId)
                .totalTicketsSold(totalTickets)
                .confirmedTickets(confirmedTickets)
                .cancelledTickets(cancelledTickets)
                .totalRevenue(totalRevenue)
                .build();
    }

    private BookingResponse mapToResponse(Ticket ticket) {
        return BookingResponse.builder()
                .ticketId(ticket.getId())
                .eventId(ticket.getEventId())
                .attendeeId(ticket.getAttendeeId())
                .price(ticket.getPrice())
                .status(ticket.getStatus())
                .paymentMethod(ticket.getPaymentMethod())
                .qrCode(ticket.getQrCode())
                .bookingDate(ticket.getBookingDate())
                .message("Booking processed successfully")
                .build();
    }

    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    // Analytics DTO
    public static class EventAnalytics {
        private Long eventId;
        private Long totalTicketsSold;
        private Long confirmedTickets;
        private Long cancelledTickets;
        private Double totalRevenue;
    }
}