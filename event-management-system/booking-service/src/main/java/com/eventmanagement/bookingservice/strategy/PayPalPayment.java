package com.eventmanagement.bookingservice.strategy;

public class PayPalPayment implements PaymentStrategy {

    private String email;
    private String token;

    public PayPalPayment(String email, String token) {
        this.email = email;
        this.token = token;
    }

    @Override
    public boolean validatePayment() {
        System.out.println("Validating PayPal account...");

        if (email == null || !email.contains("@")) {
            System.out.println("PayPal: Invalid email address");
            return false;
        }

        if (token == null || token.isEmpty()) {
            System.out.println("PayPal: Missing authentication token");
            return false;
        }

        System.out.println("PayPal: Validation successful");
        return true;
    }

    @Override
    public boolean processPayment(double amount) {
        if (!validatePayment()) {
            return false;
        }

        System.out.println("Processing PayPal payment...");
        System.out.println("PayPal: Amount: $" + amount);
        System.out.println("PayPal: Account: " + email);

        // Simulate PayPal API call
        try {
            System.out.println("Redirecting to PayPal...");
            Thread.sleep(1500);
            System.out.println("PayPal: Payment authorized successfully!");
            return true;
        } catch (InterruptedException e) {
            System.err.println("PayPal: Payment processing interrupted");
            return false;
        }
    }

    @Override
    public String getPaymentDetails() {
        return "PayPal Account: " + email;
    }

    @Override
    public String getPaymentMethodName() {
        return "PAYPAL";
    }
}