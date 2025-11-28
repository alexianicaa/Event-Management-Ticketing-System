package com.eventmanagement.bookingservice.model;

import com.eventmanagement.bookingservice.observer.TicketSubject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Column(name = "attendee_id", nullable = false)
    private Long attendeeId;

    @Column(name = "attendee_email", nullable = false)
    private String attendeeEmail;

    @Column(nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status = TicketStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Column(name = "qr_code", unique = true)
    private String qrCode;

    @CreationTimestamp
    @Column(name = "booking_date", updatable = false)
    private LocalDateTime bookingDate;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Transient field - not persisted to database
    @Transient
    private TicketSubject subject = new TicketSubject();


    //Update ticket status and notify observers
    public void updateStatus(TicketStatus newStatus) {
        TicketStatus oldStatus = this.status;
        this.status = newStatus;

        System.out.println("Ticket: Status changed: " + oldStatus + " → " + newStatus);

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

        if (subject != null) {
            subject.notifyObservers(this, message);
        }
    }

    public TicketSubject getSubject() {
        if (subject == null) {
            subject = new TicketSubject();
        }
        return subject;
    }
}