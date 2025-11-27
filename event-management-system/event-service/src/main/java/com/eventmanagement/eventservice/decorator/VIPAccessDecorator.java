package com.eventmanagement.eventservice.decorator;

import com.eventmanagement.eventservice.model.Event;

public class VIPAccessDecorator extends EventDecorator {

    private static final double VIP_PRICE = 30.0;
    private String vipBenefits;

    public VIPAccessDecorator(Event event) {
        super(event);
        this.featureName = "VIP Access";
        this.additionalPrice = VIP_PRICE;
        this.vipBenefits = "VIP Lounge Access, Priority Seating, Meet & Greet";
        System.out.println("Adding VIP Access (+$" + VIP_PRICE + ") to: " + event.getTitle());
    }

    @Override
    public double getFinalPrice() {
        double basePrice = wrappedEvent.getFinalPrice();
        return basePrice + additionalPrice;
    }

    @Override
    public String getEventDetails() {
        return wrappedEvent.getEventDetails() +
                "\n--- VIP Access Package ---" +
                "\nAdditional Cost: $" + additionalPrice +
                "\nBenefits: " + vipBenefits;
    }

    public String getVipBenefits() {
        return vipBenefits;
    }
}