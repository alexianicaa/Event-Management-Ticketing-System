package org.example.observer;

import org.example.model.Ticket;

import java.util.ArrayList;
import java.util.List; /**
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
            System.out.println("Observer attached: " + observer.getClass().getSimpleName());
        }
    }

    public void detach(TicketObserver observer) {
        if (observers.remove(observer)) {
            System.out.println("Observer detached: " + observer.getClass().getSimpleName());
        }
    }

    public void notifyObservers(Ticket ticket, String message) {
        System.out.println("\n!Notifying Observers!");
        System.out.println("Event: " + message);
        System.out.println("Total observers to notify: " + observers.size());

        for (TicketObserver observer : observers) {
            observer.update(ticket, message);
        }

        System.out.println("All Observers Notified\n");
    }

    public int getObserverCount() {
        return observers.size();
    }
}
