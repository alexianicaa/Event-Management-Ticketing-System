package org.example;

import java.time.LocalDateTime;

/**
 * Decorator Pattern Implementation
 * Abstract decorator class that wraps Event objects to add features dynamically
 */
public abstract class EventDecorator extends Event {

    protected Event wrappedEvent;

    public EventDecorator(Event event) {
        this.wrappedEvent = event;
        // Inherit properties from wrapped event
        this.eventId = event.getEventId();
        this.title = event.getTitle();
        this.description = event.getDescription();
        this.dateTime = event.getDateTime();
        this.location = event.getLocation();
        this.basePrice = event.getBasePrice();
        this.availableTickets = event.getAvailableTickets();
    }

    @Override
    public abstract double getPrice();

    @Override
    public abstract String getEventDetails();

    @Override
    public String getEventType() {
        return wrappedEvent.getEventType();
    }

    public Event getWrappedEvent() {
        return wrappedEvent;
    }
}

class VIPAccessDecorator extends EventDecorator {
    private static final double VIP_PRICE = 30.0;
    private String vipBenefits;

    public VIPAccessDecorator(Event event) {
        super(event);
        this.vipBenefits = "VIP Lounge Access, Priority Seating, Meet & Greet";
        System.out.println("Added VIP Access to event: " + event.getTitle());
    }

    @Override
    public double getPrice() {
        // Add VIP price to the wrapped event's price
        double basePrice = wrappedEvent.getPrice();
        System.out.println("VIPAccessDecorator: Base price $" + basePrice + " + VIP $" + VIP_PRICE);
        return basePrice + VIP_PRICE;
    }

    @Override
    public String getEventDetails() {
        return wrappedEvent.getEventDetails() +
                "\n--- VIP Access Package ---" +
                "\nVIP Price: $" + VIP_PRICE +
                "\nBenefits: " + vipBenefits;
    }

    public String getVipBenefits() {
        return vipBenefits;
    }

    public void setVipBenefits(String vipBenefits) {
        this.vipBenefits = vipBenefits;
    }
}

class MerchandiseBundleDecorator extends EventDecorator {

    private static final double MERCHANDISE_PRICE = 20.0;
    private String bundleDescription;

    public MerchandiseBundleDecorator(Event event) {
        super(event);
        this.bundleDescription = "Official T-Shirt, Poster, and Collectible Badge";
        System.out.println("Added Merchandise Bundle to event: " + event.getTitle());
    }

    @Override
    public double getPrice() {
        double basePrice = wrappedEvent.getPrice();
        System.out.println("MerchandiseBundleDecorator: Base price $" + basePrice + " + Merchandise $" + MERCHANDISE_PRICE);
        return basePrice + MERCHANDISE_PRICE;
    }

    @Override
    public String getEventDetails() {
        return wrappedEvent.getEventDetails() +
                "\n--- Merchandise Bundle ---" +
                "\nBundle Price: $" + MERCHANDISE_PRICE +
                "\nIncludes: " + bundleDescription;
    }

    public String getBundleDescription() {
        return bundleDescription;
    }

    public void setBundleDescription(String bundleDescription) {
        this.bundleDescription = bundleDescription;
    }
}