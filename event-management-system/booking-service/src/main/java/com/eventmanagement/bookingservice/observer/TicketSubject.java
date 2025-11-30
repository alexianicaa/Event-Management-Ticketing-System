package com.eventmanagement.bookingservice.observer;

import com.eventmanagement.bookingservice.model.Ticket;

import java.util.ArrayList;
import java.util.List;

/**
 * Subject class that maintains list of observers and notifies them of changes
 */
public class TicketSubject {

    private List<TicketObserver> observers;

    public TicketSubject() {
        this.observers = new ArrayList<>();
    }

    public void attach(TicketObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println("Observer: Attached: " + observer.getClass().getSimpleName());
        }
    }

    public void detach(TicketObserver observer) {
        if (observers.remove(observer)) {
            System.out.println("Observer: Detached: " + observer.getClass().getSimpleName());
        }
    }

    public void notifyObservers(Ticket ticket, String message) {
        System.out.println("\n=== NOTIFYING OBSERVERS ===");
        System.out.println("Observer: Event: " + message);
        System.out.println("Observer: Total observers to notify: " + observers.size());

        for (TicketObserver observer : observers) {
            observer.update(ticket, message);
        }

        System.out.println("=== ALL OBSERVERS NOTIFIED ===\n");
    }

    public int getObserverCount() {
        return observers.size();
    }
}