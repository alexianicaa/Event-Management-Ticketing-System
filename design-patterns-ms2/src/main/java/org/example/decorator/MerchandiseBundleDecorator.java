package org.example.decorator;

import org.example.model.Event;

public class MerchandiseBundleDecorator extends EventDecorator {

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
