package org.example.model;

import java.time.LocalDateTime;

public abstract class Event {
    protected String eventId;
    protected String title;
    protected String description;
    protected LocalDateTime dateTime;
    protected String location;
    protected double basePrice;
    protected int availableTickets;

    public Event() {
        this.eventId = generateEventId();
    }

    public abstract double getPrice();
    public abstract String getEventType();

    public String getEventDetails() {
        return String.format("Event: %s\nType: %s\nDate: %s\nLocation: %s\nPrice: $%.2f\nAvailable Tickets: %d",
                title, getEventType(), dateTime, location, getPrice(), availableTickets);
    }

    private String generateEventId() {
        return "EVENT-" + System.currentTimeMillis();
    }

    public String getEventId() { return eventId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public double getBasePrice() { return basePrice; }
    public void setBasePrice(double basePrice) { this.basePrice = basePrice; }
    public int getAvailableTickets() { return availableTickets; }
    public void setAvailableTickets(int availableTickets) { this.availableTickets = availableTickets; }
}

