package com.eventmanagement.eventservice.model;

public enum EventStatus {
    DRAFT,      // Created but not published
    PUBLISHED,  // Visible to attendees
    ONGOING,    // Currently happening
    COMPLETED,  // Finished
    CANCELLED   // Cancelled by organizer
}