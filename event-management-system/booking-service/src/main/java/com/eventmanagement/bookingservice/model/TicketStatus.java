package com.eventmanagement.bookingservice.model;

// Used by Observer pattern to trigger notifications
public enum TicketStatus {
    PENDING,    // Payment processing
    BOOKED,     // Payment successful, ticket reserved
    CONFIRMED,  // Ticket confirmed with QR code
    CANCELLED,  // Cancelled by user
    REFUNDED,   // Refund processed
    USED        // Ticket used for entry
}