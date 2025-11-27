package org.example;

import org.example.database.DatabaseConnection;
import org.example.decorator.MerchandiseBundleDecorator;
import org.example.decorator.VIPAccessDecorator;
import org.example.factory.EventFactory;
import org.example.model.Event;
import org.example.model.Ticket;
import org.example.model.TicketStatus;
import org.example.observer.AnalyticsObserver;
import org.example.observer.EmailNotificationObserver;
import org.example.strategy.*;

import java.time.LocalDateTime;

/**
 * Main Demo Application
 * 1. Singleton (DatabaseConnection)
 * 2. Factory Method (Event creation)
 * 3. Decorator (Event features)
 * 4. Strategy (Payment processing)
 * 5. Observer (Notifications)
 */
public class EventManagementDemo {

    public static void main(String[] args) {
        // ===== PATTERN 1: SINGLETON - Database Connection =====
        demonstrateSingletonPattern();

        // ===== PATTERN 2: FACTORY METHOD - Event Creation =====
        Event concertEvent = demonstrateFactoryPattern();

        // ===== PATTERN 3: DECORATOR - Event Features =====
        Event decoratedEvent = demonstrateDecoratorPattern(concertEvent);

        // ===== PATTERN 4 & 5: STRATEGY & OBSERVER - Payment and Notifications =====
        demonstrateStrategyAndObserverPatterns(decoratedEvent);
    }

    /**
     * Demonstrates Singleton Pattern with DatabaseConnection
     */
    private static void demonstrateSingletonPattern() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("PATTERN 1: SINGLETON - Database Connection");
        System.out.println("=".repeat(60));

        // First call - creates new instance
        DatabaseConnection db1 = DatabaseConnection.getInstance();

        // Second call - returns same instance
        DatabaseConnection db2 = DatabaseConnection.getInstance();

        // Third call - still same instance
        DatabaseConnection db3 = DatabaseConnection.getInstance();

        // Verify all references point to same instance
        System.out.println("\nVerifying Singleton behavior:");
        System.out.println("db1 == db2: " + (db1 == db2));
        System.out.println("db2 == db3: " + (db2 == db3));
        System.out.println("db1 == db3: " + (db1 == db3));

        // Simulate database operations
        System.out.println("\nSimulating database operations:");
        db1.executeQuery("SELECT * FROM events WHERE status = 'PUBLISHED'");
        db2.executeUpdate("UPDATE users SET last_login = NOW() WHERE id = 123");
    }

    /**
     * Demonstrates Factory Method Pattern for creating different event types
     */
    private static Event demonstrateFactoryPattern() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("PATTERN 2: FACTORY METHOD - Event Creation");
        System.out.println("=".repeat(60));

        // Create Concert using Factory
        System.out.println("\n--- Creating Concert Event ---");
        EventFactory concertFactory = EventFactory.getFactory("concert");
        Event concert = concertFactory.createAndConfigureEvent(
                "Hip Hop November 2025",
                "An amazing hip hop concert featuring top artists",
                LocalDateTime.of(2025, 11, 15, 20, 0),
                "Madison Square Garden, New York",
                50.0,
                5000
        );

        // Create Workshop using Factory
        System.out.println("\n--- Creating Workshop Event ---");
        EventFactory workshopFactory = EventFactory.getFactory("workshop");
        Event workshop = workshopFactory.createAndConfigureEvent(
                "Java Spring Boot Masterclass",
                "Advanced Spring Boot development workshop",
                LocalDateTime.of(2025, 12, 15, 12, 0),
                "Tech Hub, San Francisco",
                150.0,
                50
        );

        // Create Conference using Factory
        System.out.println("\n--- Creating Conference Event ---");
        EventFactory conferenceFactory = EventFactory.getFactory("conference");
        Event conference = conferenceFactory.createAndConfigureEvent(
                "Blockchain summit",
                "Annual blockchain conference with industry leaders",
                LocalDateTime.of(2026, 1, 10, 9, 0),
                "Convention Center, Seattle",
                300.0,
                1000
        );

        // Display created events
        System.out.println("\n--- Created Events Summary ---");
        System.out.println("1. " + concert.getEventType() + ": " + concert.getTitle() +
                " - $" + concert.getPrice());
        System.out.println("2. " + workshop.getEventType() + ": " + workshop.getTitle() +
                " - $" + workshop.getPrice());
        System.out.println("3. " + conference.getEventType() + ": " + conference.getTitle() +
                " - $" + conference.getPrice());

        return concert;
    }

    /**
     * Demonstrates Decorator Pattern by adding features to events
     */
    private static Event demonstrateDecoratorPattern(Event baseEvent) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("PATTERN 3: DECORATOR - Adding Event Features");
        System.out.println("=".repeat(60));

        System.out.println("\n--- Base Event ---");
        System.out.println("Event: " + baseEvent.getTitle());
        System.out.println("Base Price: $" + baseEvent.getPrice());

        // Add VIP Access
        System.out.println("\n--- Step 1: Adding VIP Access ---");
        Event vipEvent = new VIPAccessDecorator(baseEvent);
        System.out.println("Price after VIP: $" + vipEvent.getPrice());

        // Add Merchandise Bundle
        System.out.println("\n--- Step 2: Adding Merchandise Bundle ---");
        Event vipWithMerch = new MerchandiseBundleDecorator(vipEvent);
        System.out.println("Price after Merchandise: $" + vipWithMerch.getPrice());

        // Display full event details
        System.out.println("\n--- Complete Event Details ---");
        System.out.println(vipWithMerch.getEventDetails());

        return vipWithMerch;
    }

    /**
     * Demonstrates Strategy Pattern (Payment) and Observer Pattern (Notifications)
     */
    private static void demonstrateStrategyAndObserverPatterns(Event event) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("PATTERN 4: STRATEGY - Payment Processing");
        System.out.println("PATTERN 5: OBSERVER - Notification System");
        System.out.println("=".repeat(60));

        double ticketPrice = event.getPrice();

        PaymentProcessor paymentProcessor = new PaymentProcessor();

        // Demonstrate different payment strategies
        System.out.println("\n--- Scenario 1: Credit Card Payment ---");
        PaymentStrategy creditCard = new CreditCardPayment(
                "1234567890123456",
                "123",
                "12/26",
                "John Doe"
        );
        paymentProcessor.setPaymentStrategy(creditCard);
        boolean payment1Success = paymentProcessor.executePayment(ticketPrice);

        System.out.println("\n--- Scenario 2: PayPal Payment ---");
        PaymentStrategy paypal = new PayPalPayment(
                "user@example.com",
                "paypal-token-qwerty"
        );
        paymentProcessor.setPaymentStrategy(paypal);
        boolean payment2Success = paymentProcessor.executePayment(ticketPrice);

        System.out.println("\n--- Scenario 3: Digital Wallet Payment ---");
        PaymentStrategy digitalWallet = new DigitalWalletPayment(
                "wallet-abc-def-123",
                "ApplePay",
                "device-iphone-abc"
        );
        paymentProcessor.setPaymentStrategy(digitalWallet);
        boolean payment3Success = paymentProcessor.executePayment(ticketPrice);

        // Create a ticket after successful payment (using PayPal scenario)
        if (payment2Success) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("Creating Ticket and Demonstrating Observer Pattern");
            System.out.println("=".repeat(60));

            // Create ticket
            Ticket ticket = new Ticket(
                    event.getEventId(),
                    event.getTitle(),
                    "ATT-001",
                    "user@example.com",
                    "+4078888888",
                    ticketPrice
            );

            // Attach observers to the ticket
            System.out.println("\n--- Attaching Observers ---");
            ticket.getSubject().attach(new EmailNotificationObserver());
            ticket.getSubject().attach(new AnalyticsObserver());

            System.out.println("Total observers attached: " +
                    ticket.getSubject().getObserverCount());

            // Change ticket status - this will notify all observers
            System.out.println("\n--- Confirming Ticket (triggers notifications) ---");
            ticket.updateStatus(TicketStatus.CONFIRMED);

            // Display ticket details
            System.out.println("\n" + ticket.getTicketDetails());

            // Simulate ticket cancellation
            System.out.println("\n--- Simulating Ticket Cancellation ---");
            ticket.updateStatus(TicketStatus.CANCELLED);

            // Simulate refund
            System.out.println("\n--- Processing Refund ---");
            ticket.updateStatus(TicketStatus.REFUNDED);
        }
    }
}