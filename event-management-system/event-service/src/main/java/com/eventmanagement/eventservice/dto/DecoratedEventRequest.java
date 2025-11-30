package com.eventmanagement.eventservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DecoratedEventRequest {
    private Long eventId;
    private Boolean addVipAccess;
    private Boolean addMerchandise;
}