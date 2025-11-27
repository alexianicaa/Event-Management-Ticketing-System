package com.eventmanagement.eventservice.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@DiscriminatorValue("CONFERENCE")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Conference extends Event {

    @ElementCollection
    private List<String> speakers;

    @ElementCollection
    private List<String> tracks;

    @Override
    public EventType getEventType() {
        return EventType.CONFERENCE;
    }
}