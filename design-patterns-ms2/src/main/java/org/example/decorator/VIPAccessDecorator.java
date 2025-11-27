package org.example.decorator;

import org.example.model.Event;

public class VIPAccessDecorator extends EventDecorator {
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
