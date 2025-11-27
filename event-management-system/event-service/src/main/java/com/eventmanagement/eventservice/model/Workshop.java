package com.eventmanagement.eventservice.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("WORKSHOP")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Workshop extends Event {

    private String instructor;

    private Integer maxParticipants;

    @Override
    public EventType getEventType() {
        return EventType.WORKSHOP;
    }
}