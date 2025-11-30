package com.eventmanagement.eventservice.repository;

import com.eventmanagement.eventservice.model.Event;
import com.eventmanagement.eventservice.model.EventStatus;
import com.eventmanagement.eventservice.model.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByOrganizerId(Long organizerId);

    List<Event> findByStatus(EventStatus status);

    List<Event> findByEventDateTimeBetween(LocalDateTime start, LocalDateTime end);

    List<Event> findByLocation(String location);

    List<Event> findByStatusAndEventDateTimeAfter(EventStatus status, LocalDateTime dateTime);

    default List<Event> findPublishedEvents() {
        return findByStatus(EventStatus.PUBLISHED);
    }
}