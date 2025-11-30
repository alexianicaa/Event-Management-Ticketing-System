package com.eventmanagement.eventservice.dto;

import jakarta.validation.constraints.*;
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
public class EventRequest {

    @NotNull(message = "Event type is required")
    private EventType eventType;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
    private String title;

    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    @NotNull(message = "Event date and time is required")
    @Future(message = "Event date must be in the future")
    private LocalDateTime eventDateTime;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Base price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private Double basePrice;

    @NotNull(message = "Available tickets is required")
    @Min(value = 1, message = "At least 1 ticket must be available")
    private Integer availableTickets;

    @NotNull(message = "Organizer ID is required")
    private Long organizerId;

    // Concert-specific fields
    private String artist;
    private String genre;

    // Workshop-specific fields
    private String instructor;
    private Integer maxParticipants;

    // Conference-specific fields
    private List<String> speakers;
    private List<String> tracks;
}