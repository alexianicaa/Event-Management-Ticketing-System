package org.example.model;

import org.example.observer.TicketSubject;

import java.time.LocalDateTime;
import java.util.UUID;

public class Ticket {

    private String ticketId;
    private String eventId;
    private String eventTitle;
    private String attendeeId;
    private String attendeeEmail;
    private String attendeePhone;
    private double price;
    private LocalDateTime bookingDate;
    private TicketStatus status;
    private String qrCode;

    // Observer pattern integration
    private TicketSubject subject;

    public Ticket(String eventId, String eventTitle, String attendeeId,
                  String attendeeEmail, String attendeePhone, double price) {
        this.ticketId = generateTicketId();
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.attendeeId = attendeeId;
        this.attendeeEmail = attendeeEmail;
        this.attendeePhone = attendeePhone;
        this.price = price;
        this.bookingDate = LocalDateTime.now();
        this.status = TicketStatus.BOOKED;
        this.qrCode = generateQRCode();

        // Initialize observer subject
        this.subject = new TicketSubject();

        System.out.println("Ticket created: " + ticketId);
    }


    private String generateTicketId() {
        return "TICKET-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public String generateQRCode() {
        // Simulate QR code generation
        String qrData = ticketId + "-" + eventId + "-" + attendeeId;
        this.qrCode = "QR:" + UUID.nameUUIDFromBytes(qrData.getBytes()).toString();
        System.out.println("QR Code generated: " + qrCode);
        return qrCode;
    }

    public void updateStatus(TicketStatus newStatus) {
        TicketStatus oldStatus = this.status;
        this.status = newStatus;

        System.out.println("Ticket " + ticketId + " status changed: " + oldStatus + " -> " + newStatus);

        // Notify observers based on status change
        String message = "";
        switch (newStatus) {
            case CONFIRMED:
                message = "TICKET_CONFIRMED";
                break;
            case CANCELLED:
                message = "TICKET_CANCELLED";
                break;
            case REFUNDED:
                message = "TICKET_REFUNDED";
                break;
            case USED:
                message = "TICKET_USED";
                break;
            default:
                message = "TICKET_STATUS_UPDATED";
        }

        // Notify all observers
        subject.notifyObservers(this, message);
    }

    public String getTicketDetails() {
        return String.format(
                " TICKET \n" +
                        "Ticket ID: %s\n" +
                        "Event: %s\n" +
                        "Attendee: %s\n" +
                        "Price: $%.2f\n" +
                        "Booking Date: %s\n" +
                        "Status: %s\n" +
                        "QR Code: %s\n\n",
                ticketId, eventTitle, attendeeEmail, price,
                bookingDate, status, qrCode
        );
    }

    // Observer pattern methods
    public TicketSubject getSubject() {
        return subject;
    }

    public String getTicketId() { return ticketId; }
    public String getEventId() { return eventId; }
    public String getEventTitle() { return eventTitle; }
    public String getAttendeeId() { return attendeeId; }
    public String getAttendeeEmail() { return attendeeEmail; }
    public String getAttendeePhone() { return attendeePhone; }
    public double getPrice() { return price; }
    public LocalDateTime getBookingDate() { return bookingDate; }
    public TicketStatus getStatus() { return status; }
    public String getQrCode() { return qrCode; }

    public void setAttendeeEmail(String attendeeEmail) { this.attendeeEmail = attendeeEmail; }
    public void setAttendeePhone(String attendeePhone) { this.attendeePhone = attendeePhone; }
}

