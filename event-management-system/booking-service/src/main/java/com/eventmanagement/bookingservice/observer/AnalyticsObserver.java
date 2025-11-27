package com.eventmanagement.bookingservice.observer;

import com.eventmanagement.bookingservice.model.Ticket;

public class AnalyticsObserver implements TicketObserver {

    private String analyticsService;

    public AnalyticsObserver() {
        this.analyticsService = "Event Analytics Dashboard";
    }

    @Override
    public void update(Ticket ticket, String message) {
        System.out.println("\nAnalytics: Tracking event...");
        System.out.println("Analytics: Service: " + analyticsService);

        trackEvent(ticket, message);
    }

    private void trackEvent(Ticket ticket, String message) {
        System.out.println("Analytics: Event Type: " + message);

        switch (message) {
            case "TICKET_CONFIRMED":
                System.out.println("Analytics: Incrementing ticket sales counter");
                System.out.println("Analytics: Event ID: " + ticket.getEventId());
                System.out.println("Analytics: Revenue: $" + ticket.getPrice());
                updateSalesMetrics(ticket);
                break;
            case "TICKET_CANCELLED":
                System.out.println("Analytics: Incrementing cancellation counter");
                System.out.println("Analytics: Event ID: " + ticket.getEventId());
                updateCancellationMetrics(ticket);
                break;
            case "TICKET_REFUNDED":
                System.out.println("Analytics: Updating refund statistics");
                System.out.println("Analytics: Amount refunded: $" + ticket.getPrice());
                updateRefundMetrics(ticket);
                break;
        }

        System.out.println("Analytics: Analytics updated successfully!");
    }

    private void updateSalesMetrics(Ticket ticket) {
        // Simulate database update
        System.out.println("Analytics: Updating sales metrics in database...");
        System.out.println("Analytics: Total sales increased by 1");
        System.out.println("Analytics: Revenue increased by $" + ticket.getPrice());
    }

    private void updateCancellationMetrics(Ticket ticket) {
        // Simulate database update
        System.out.println("Analytics: Updating cancellation metrics...");
        System.out.println("Analytics: Cancellation rate recalculated");
    }

    private void updateRefundMetrics(Ticket ticket) {
        // Simulate database update
        System.out.println("Analytics: Updating refund metrics...");
        System.out.println("Analytics: Total refunds increased by $" + ticket.getPrice());
    }
}