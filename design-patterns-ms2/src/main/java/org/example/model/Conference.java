package org.example.model;

public class Conference extends Event {
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
