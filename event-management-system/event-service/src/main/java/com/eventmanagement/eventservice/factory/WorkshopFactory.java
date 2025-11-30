package com.eventmanagement.eventservice.factory;

import com.eventmanagement.eventservice.model.Event;
import com.eventmanagement.eventservice.model.Workshop;

public class WorkshopFactory extends EventFactory {

    @Override
    public Event createEvent() {
        System.out.println("Creating Workshop event");
        return Workshop.builder().build();
    }
}