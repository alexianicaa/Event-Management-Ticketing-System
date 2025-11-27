package com.eventmanagement.bookingservice.observer;

import com.eventmanagement.bookingservice.model.Ticket;

/**
 * OBSERVER PATTERN
 *
 * Interface for observers that need to be notified of ticket changes.
 * Multiple observers (Email, Analytics) implement this interface
 * and get automatically notified when ticket status changes.
 */
public interface TicketObserver {

    /**
     * Called when ticket status changes
     * @param ticket The ticket that changed
     * @param message The notification message
     */
    void update(Ticket ticket, String message);
}