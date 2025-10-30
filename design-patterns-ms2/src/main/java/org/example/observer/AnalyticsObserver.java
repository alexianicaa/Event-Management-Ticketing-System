package org.example.observer;

import org.example.model.Ticket;

public class AnalyticsObserver implements TicketObserver {

    private String analyticsService;

    public AnalyticsObserver() {
        this.analyticsService = "Event Analytics Dashboard";
    }

    @Override
    public void update(Ticket ticket, String message) {
        System.out.println("\n[AnalyticsObserver]");
        System.out.println("Service: " + analyticsService);

        trackEvent(ticket, message);
    }

    private void trackEvent(Ticket ticket, String message) {
        System.out.println("Tracking event: " + message);

        switch (message) {
            case "TICKET_CONFIRMED":
                System.out.println("Analytics: Incrementing ticket sales counter");
                System.out.println("Event ID: " + ticket.getEventId());
                System.out.println("Revenue: $" + ticket.getPrice());
                updateSalesMetrics(ticket);
                break;
            case "TICKET_CANCELLED":
                System.out.println("Analytics: Incrementing cancellation counter");
                System.out.println("Event ID: " + ticket.getEventId());
                updateCancellationMetrics(ticket);
                break;
            case "TICKET_REFUNDED":
                System.out.println("Analytics: Updating refund statistics");
                System.out.println("Amount refunded: $" + ticket.getPrice());
                updateRefundMetrics(ticket);
                break;
        }

        System.out.println("Analytics updated successfully!");
    }

    private void updateSalesMetrics(Ticket ticket) {
        // Simulate database update
        System.out.println("Updating sales metrics in database...");
        System.out.println("Total sales increased by 1");
        System.out.println("Revenue increased by $" + ticket.getPrice());
    }

    private void updateCancellationMetrics(Ticket ticket) {
        // Simulate database update
        System.out.println("Updating cancellation metrics in database...");
        System.out.println("Cancellation rate recalculated");
    }

    private void updateRefundMetrics(Ticket ticket) {
        // Simulate database update
        System.out.println("Updating refund metrics in database...");
        System.out.println("Total refunds increased by $" + ticket.getPrice());
    }
}
