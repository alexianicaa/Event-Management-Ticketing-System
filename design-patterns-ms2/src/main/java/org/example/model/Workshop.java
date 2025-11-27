package org.example.model;

public class Workshop extends Event {
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
