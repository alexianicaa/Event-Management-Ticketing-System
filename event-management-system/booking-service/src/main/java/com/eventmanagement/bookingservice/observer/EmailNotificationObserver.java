package com.eventmanagement.bookingservice.observer;

import com.eventmanagement.bookingservice.model.Ticket;

public class EmailNotificationObserver implements TicketObserver {

    private String emailService;

    public EmailNotificationObserver() {
        this.emailService = "SendGrid Email Service";
    }

    @Override
    public void update(Ticket ticket, String message) {
        System.out.println("\nEmail Obs: Sending email notification...");
        System.out.println("Email Obs: Service: " + emailService);

        String emailContent = generateEmailContent(ticket, message);
        sendEmail(ticket.getAttendeeEmail(), emailContent);
    }

    private String generateEmailContent(Ticket ticket, String message) {
        StringBuilder content = new StringBuilder();
        content.append("Dear Customer,\n\n");

        switch (message) {
            case "TICKET_CONFIRMED":
                content.append("Your ticket has been confirmed!\n");
                content.append("Ticket ID: ").append(ticket.getId()).append("\n");
                content.append("Event ID: ").append(ticket.getEventId()).append("\n");
                content.append("Price: $").append(ticket.getPrice()).append("\n");
                content.append("QR Code: ").append(ticket.getQrCode()).append("\n");
                break;
            case "TICKET_CANCELLED":
                content.append("Your ticket has been cancelled.\n");
                content.append("Ticket ID: ").append(ticket.getId()).append("\n");
                content.append("Refund will be processed within 5-7 business days.\n");
                break;
            case "TICKET_REFUNDED":
                content.append("Your refund has been processed.\n");
                content.append("Ticket ID: ").append(ticket.getId()).append("\n");
                content.append("Amount: $").append(ticket.getPrice()).append("\n");
                break;
            default:
                content.append("Ticket status update: ").append(message).append("\n");
        }

        content.append("\nThank you for using our service!");
        return content.toString();
    }

    private void sendEmail(String toEmail, String content) {
        System.out.println("Email Obs: Sending to: " + toEmail);
        System.out.println("Email Obs: Email sent successfully!");
    }
}