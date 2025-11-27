package com.eventmanagement.eventservice.factory;

import com.eventmanagement.eventservice.model.Concert;
import com.eventmanagement.eventservice.model.Event;

public class ConcertFactory extends EventFactory {

    @Override
    public Event createEvent() {
        System.out.println("Creating Concert event");
        return Concert.builder().build();
    }
}