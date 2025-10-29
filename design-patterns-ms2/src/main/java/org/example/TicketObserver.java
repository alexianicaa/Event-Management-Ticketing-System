package org.example;

import org.example.Ticket;
import java.util.ArrayList;
import java.util.List;

/**
 * Observer Pattern Implementation
 * Interface for observers that need to be notified of ticket changes
 */
public interface TicketObserver {
    void update(Ticket ticket, String message);
}

/**
 * Subject class that maintains list of observers and notifies them of changes
 */
class TicketSubject {

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

class EmailNotificationObserver implements TicketObserver {

    private String emailService;

    public EmailNotificationObserver() {
        this.emailService = "SendGrid Email Service";
    }

    @Override
    public void update(Ticket ticket, String message) {
        System.out.println("\n[EmailNotificationObserver]");
        System.out.println("Service: " + emailService);

        String emailContent = generateEmailContent(ticket, message);
        sendEmail(ticket.getAttendeeEmail(), emailContent);
    }

    private String generateEmailContent(Ticket ticket, String message) {
        StringBuilder content = new StringBuilder();
        content.append("Dear Customer,\n\n");

        switch (message) {
            case "TICKET_CONFIRMED":
                content.append("Your ticket has been confirmed!\n");
                content.append("Ticket ID: ").append(ticket.getTicketId()).append("\n");
                content.append("Event: ").append(ticket.getEventTitle()).append("\n");
                content.append("QR Code: ").append(ticket.getQrCode()).append("\n");
                break;
            case "TICKET_CANCELLED":
                content.append("Your ticket has been cancelled.\n");
                content.append("Ticket ID: ").append(ticket.getTicketId()).append("\n");
                content.append("Refund will be processed within 5-7 business days.\n");
                break;
            case "TICKET_REFUNDED":
                content.append("Your refund has been processed.\n");
                content.append("Ticket ID: ").append(ticket.getTicketId()).append("\n");
                content.append("Amount: $").append(ticket.getPrice()).append("\n");
                break;
            default:
                content.append("Ticket status update: ").append(message).append("\n");
        }

        content.append("\nThank you for using our service!");
        return content.toString();
    }

    private void sendEmail(String toEmail, String content) {
        System.out.println("Sending email to: " + toEmail);
        System.out.println("Email content:");
        System.out.println(content);
        System.out.println("Email sent successfully!");
    }
}

class AnalyticsObserver implements TicketObserver {

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