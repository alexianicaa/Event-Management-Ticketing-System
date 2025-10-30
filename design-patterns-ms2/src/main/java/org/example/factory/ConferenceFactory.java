package org.example.factory;

import org.example.model.Conference;
import org.example.model.Event;

class ConferenceFactory extends EventFactory {
    @Override
    public Event createEvent() {
        System.out.println("ConferenceFactory: Creating a new Conference");
        Conference conference = new Conference();
        conference.setSpeakers(new String[]{});
        conference.setTracks(new String[]{"General"});
        return conference;
    }

    public Conference createConferenceWithSpeakers(String[] speakers, String[] tracks) {
        Conference conference = (Conference) createEvent();
        conference.setSpeakers(speakers);
        conference.setTracks(tracks);
        return conference;
    }
}