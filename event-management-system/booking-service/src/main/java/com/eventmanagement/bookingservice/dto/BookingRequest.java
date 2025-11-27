package com.eventmanagement.bookingservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Request to book a ticket
public class BookingRequest {

    @NotNull(message = "Event ID is required")
    private Long eventId;

    @NotNull(message = "Attendee ID is required")
    private Long attendeeId;

    @NotNull(message = "Payment details are required")
    private PaymentRequest payment;

    // Apply decorators
    private Boolean withVip;
    private Boolean withMerchandise;
}