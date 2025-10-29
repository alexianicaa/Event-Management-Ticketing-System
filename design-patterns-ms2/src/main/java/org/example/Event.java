package org.example;

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

class Concert extends Event {
    private String artist;
    private String genre;

    @Override
    public double getPrice() {
        return basePrice;
    }

    @Override
    public String getEventType() {
        return "Concert";
    }

    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
}

class Workshop extends Event {
    private String instructor;
    private int maxParticipants;

    @Override
    public double getPrice() {
        return basePrice;
    }

    @Override
    public String getEventType() {
        return "Workshop";
    }

    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }
    public int getMaxParticipants() { return maxParticipants; }
    public void setMaxParticipants(int maxParticipants) { this.maxParticipants = maxParticipants; }
}

class Conference extends Event {
    private String[] speakers;
    private String[] tracks;

    @Override
    public double getPrice() {
        return basePrice;
    }

    @Override
    public String getEventType() {
        return "Conference";
    }

    public String[] getSpeakers() { return speakers; }
    public void setSpeakers(String[] speakers) { this.speakers = speakers; }
    public String[] getTracks() { return tracks; }
    public void setTracks(String[] tracks) { this.tracks = tracks; }
}