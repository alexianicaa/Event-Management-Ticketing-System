package com.eventmanagement.bookingservice.repository;

import com.eventmanagement.bookingservice.model.Ticket;
import com.eventmanagement.bookingservice.model.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByAttendeeId(Long attendeeId);

    List<Ticket> findByEventId(Long eventId);

    List<Ticket> findByStatus(TicketStatus status);

    List<Ticket> findByAttendeeIdAndEventId(Long attendeeId, Long eventId);

    Long countByEventId(Long eventId);

    Long countByEventIdAndStatus(Long eventId, TicketStatus status);
}