package com.eventmanagement.eventservice.factory;

import com.eventmanagement.eventservice.model.Conference;
import com.eventmanagement.eventservice.model.Event;

public class ConferenceFactory extends EventFactory {

    @Override
    public Event createEvent() {
        System.out.println("Creating Conference event");
        return Conference.builder().build();
    }
}