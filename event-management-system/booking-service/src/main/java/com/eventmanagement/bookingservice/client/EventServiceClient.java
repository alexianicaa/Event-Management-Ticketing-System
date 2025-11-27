package com.eventmanagement.bookingservice.client;

import com.eventmanagement.bookingservice.dto.EventResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

// Feign Client to communicate with Event Service
@FeignClient(name = "event-service", url = "http://localhost:8082")
public interface EventServiceClient {

    @GetMapping("/api/events/{id}")
    EventResponse getEventById(
            @PathVariable("id") Long eventId,
            @RequestParam(required = false) Boolean vip,
            @RequestParam(required = false) Boolean merchandise
    );

    @PutMapping("/api/events/{id}/tickets")
    Map<String, String> updateTickets(
            @PathVariable("id") Long eventId,
            @RequestParam Integer reduce
    );
}