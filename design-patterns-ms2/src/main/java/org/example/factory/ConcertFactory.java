package org.example.factory;

import org.example.model.Concert;
import org.example.model.Event;

class ConcertFactory extends EventFactory {
    @Override
    public Event createEvent() {
        System.out.println("ConcertFactory: Creating a new Concert");
        Concert concert = new Concert();
        concert.setGenre("General"); // default value
        return concert;
    }

    public Concert createConcertWithArtist(String artist, String genre) {
        Concert concert = (Concert) createEvent();
        concert.setArtist(artist);
        concert.setGenre(genre);
        return concert;
    }
}