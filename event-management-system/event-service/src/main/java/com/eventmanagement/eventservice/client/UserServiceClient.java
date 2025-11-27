package com.eventmanagement.eventservice.client;

import com.eventmanagement.eventservice.dto.UserValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign Client to communicate with User Service
 * Validates that the user creating an event has ORGANIZER role
 */
@FeignClient(name = "user-service", url = "${user-service.url}")
public interface UserServiceClient {

    @GetMapping("/api/users/{id}/validate-role/ORGANIZER")
    UserValidationResponse validateOrganizer(@PathVariable("id") Long userId);
}