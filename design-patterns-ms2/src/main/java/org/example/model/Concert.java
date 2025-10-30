package org.example.model;

public class Concert extends Event {
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
