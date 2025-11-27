package com.eventmanagement.userservice.controller;

import com.eventmanagement.userservice.dto.UserResponse;
import com.eventmanagement.userservice.model.UserRole;
import com.eventmanagement.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Get all users (ORGANIZER only)
     * GET /api/users
     */
    @GetMapping
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * Get user by ID
     * GET /api/users/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get user by username
     * GET /api/users/username/{username}
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
        try {
            return ResponseEntity.ok(userService.getUserByUsername(username));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get users by role
     * GET /api/users/role/{role}
     */
    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserResponse>> getUsersByRole(@PathVariable UserRole role) {
        return ResponseEntity.ok(userService.getUsersByRole(role));
    }

    /**
     * Validate user exists
     * GET /api/users/{id}/validate
     */
    @GetMapping("/{id}/validate")
    public ResponseEntity<Map<String, Boolean>> validateUser(@PathVariable Long id) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", userService.validateUser(id));
        return ResponseEntity.ok(response);
    }

    /**
     * Validate user has specific role
     * GET /api/users/{id}/validate-role/{role}
     */
    @GetMapping("/{id}/validate-role/{role}")
    public ResponseEntity<Map<String, Boolean>> validateUserRole(
            @PathVariable Long id,
            @PathVariable UserRole role) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("hasRole", userService.validateUserRole(id, role));
        return ResponseEntity.ok(response);
    }

    /**
     * Deactivate user (ORGANIZER only)
     * PUT /api/users/{id}/deactivate
     */
    @PutMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<Map<String, String>> deactivateUser(@PathVariable Long id) {
        try {
            userService.deactivateUser(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "User deactivated successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Activate user (ORGANIZER only)
     * PUT /api/users/{id}/activate
     */
    @PutMapping("/{id}/activate")
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<Map<String, String>> activateUser(@PathVariable Long id) {
        try {
            userService.activateUser(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "User activated successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}