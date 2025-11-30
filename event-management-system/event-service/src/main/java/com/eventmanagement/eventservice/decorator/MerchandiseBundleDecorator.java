package com.eventmanagement.eventservice.decorator;

import com.eventmanagement.eventservice.model.Event;

public class MerchandiseBundleDecorator extends EventDecorator {

    private static final double MERCHANDISE_PRICE = 20.0;
    private String bundleDescription;

    public MerchandiseBundleDecorator(Event event) {
        super(event);
        this.featureName = "Merchandise Bundle";
        this.additionalPrice = MERCHANDISE_PRICE;
        this.bundleDescription = "Official T-Shirt, Poster, and Collectible Badge";
        System.out.println("Adding Merchandise Bundle (+$" + MERCHANDISE_PRICE + ") to: " + event.getTitle());
    }

    @Override
    public double getFinalPrice() {
        double basePrice = wrappedEvent.getFinalPrice();
        return basePrice + additionalPrice;
    }

    @Override
    public String getEventDetails() {
        return wrappedEvent.getEventDetails() +
                "\n--- Merchandise Bundle ---" +
                "\nAdditional Cost: $" + additionalPrice +
                "\nIncludes: " + bundleDescription;
    }

    public String getBundleDescription() {
        return bundleDescription;
    }
}