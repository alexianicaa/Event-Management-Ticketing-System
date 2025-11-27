package com.eventmanagement.bookingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Event details from Event Service
public class EventResponse {
    private Long id;
    private String title;
    private String eventType;
    private LocalDateTime eventDateTime;
    private String location;
    private Double basePrice;
    private Double finalPrice;
    private Integer availableTickets;
    private Long organizerId;
    private String status;
    private Boolean hasVipAccess;
    private Boolean hasMerchandise;
}