package org.example.factory;

import org.example.model.Event;
import org.example.model.Workshop;

class WorkshopFactory extends EventFactory {
    @Override
    public Event createEvent() {
        System.out.println("WorkshopFactory: Creating a new Workshop");
        Workshop workshop = new Workshop();
        workshop.setMaxParticipants(30); // default value
        return workshop;
    }

    public Workshop createWorkshopWithInstructor(String instructor, int maxParticipants) {
        Workshop workshop = (Workshop) createEvent();
        workshop.setInstructor(instructor);
        workshop.setMaxParticipants(maxParticipants);
        return workshop;
    }
}