package com.eventmanagement.bookingservice.dto;

import com.eventmanagement.bookingservice.model.PaymentMethod;
import com.eventmanagement.bookingservice.model.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Response after booking a ticket
public class BookingResponse {

    private Long ticketId;
    private Long eventId;
    private Long attendeeId;
    private Double price;
    private TicketStatus status;
    private PaymentMethod paymentMethod;
    private String qrCode;
    private LocalDateTime bookingDate;
    private String message;
}