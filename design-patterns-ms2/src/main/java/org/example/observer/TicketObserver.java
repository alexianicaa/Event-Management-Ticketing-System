package org.example.observer;

import org.example.model.Ticket;
import java.util.ArrayList;
import java.util.List;

/**
 * Observer Pattern Implementation
 * Interface for observers that need to be notified of ticket changes
 */
public interface TicketObserver {
    void update(Ticket ticket, String message);
}

