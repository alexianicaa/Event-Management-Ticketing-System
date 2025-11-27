package com.eventmanagement.eventservice.dto;

import com.eventmanagement.eventservice.model.EventStatus;
import com.eventmanagement.eventservice.model.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventResponse {

    private Long id;
    private EventType eventType;
    private String title;
    private String description;
    private LocalDateTime eventDateTime;
    private String location;
    private Double basePrice;
    private Double finalPrice;  // May include decorator prices
    private Integer availableTickets;
    private Long organizerId;
    private EventStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Decorator features
    private Boolean hasVipAccess;
    private Boolean hasMerchandise;

    // Concert-specific
    private String artist;
    private String genre;

    // Workshop-specific
    private String instructor;
    private Integer maxParticipants;

    // Conference-specific
    private List<String> speakers;
    private List<String> tracks;
}