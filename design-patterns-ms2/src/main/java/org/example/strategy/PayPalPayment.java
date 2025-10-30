package org.example.strategy;

public class PayPalPayment implements PaymentStrategy {

    private String email;
    private String password;
    private String token;

    public PayPalPayment(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean validatePayment() {
        System.out.println("Validating PayPal payment...");

        if (email == null || !email.contains("@")) {
            System.out.println("Invalid email address");
            return false;
        }

        if (token == null && password == null) {
            System.out.println("No authentication method provided");
            return false;
        }

        System.out.println("PayPal validation successful");
        return true;
    }

    @Override
    public boolean processPayment(double amount) {
        if (!validatePayment()) {
            return false;
        }

        System.out.println("Processing PayPal payment...");
        System.out.println("Amount: $" + amount);
        System.out.println("PayPal Account: " + email);

        // Simulate PayPal API call
        try {
            Thread.sleep(1500);
            System.out.println("PayPal payment authorized successfully!");
            return true;
        } catch (InterruptedException e) {
            System.err.println("Payment processing interrupted");
            return false;
        }
    }

    @Override
    public String getPaymentDetails() {
        return "PayPal Account: " + email;
    }
}
