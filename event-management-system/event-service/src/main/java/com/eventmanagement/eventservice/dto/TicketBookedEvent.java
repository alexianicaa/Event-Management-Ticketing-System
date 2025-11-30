package com.eventmanagement.eventservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketBookedEvent implements Serializable {


    private Long ticketId;
    private Long eventId;
    private Long attendeeId;
    private String attendeeEmail;
    private Double price;
    private String status;
    private String qrCode;
    private LocalDateTime bookingDate;
    private String eventType;
    private static final long serialVersionUID = 1L;
}