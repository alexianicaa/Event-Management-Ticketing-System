package org.example.strategy;

public class DigitalWalletPayment implements PaymentStrategy {
    private String walletId;
    private String provider; // "ApplePay", "GooglePay", etc.
    private String deviceId;

    public DigitalWalletPayment(String walletId, String provider, String deviceId) {
        this.walletId = walletId;
        this.provider = provider;
        this.deviceId = deviceId;
    }

    @Override
    public boolean validatePayment() {
        System.out.println("Validating " + provider + " payment...");

        if (walletId == null || walletId.isEmpty()) {
            System.out.println("Invalid wallet ID");
            return false;
        }

        if (deviceId == null || deviceId.isEmpty()) {
            System.out.println("Invalid device ID");
            return false;
        }

        System.out.println(provider + " validation successful");
        return true;
    }

    @Override
    public boolean processPayment(double amount) {
        if (!validatePayment()) {
            return false;
        }

        System.out.println("Processing " + provider + " payment...");
        System.out.println("Amount: $" + amount);
        System.out.println("Wallet ID: " + walletId);

        // Simulate payment
        try {
            Thread.sleep(500);
            System.out.println(provider + " payment processed successfully!");
            return true;
        } catch (InterruptedException e) {
            System.err.println("Payment processing interrupted");
            return false;
        }
    }

    @Override
    public String getPaymentDetails() {
        return provider + " - Wallet ID: " + walletId.substring(0, 8) + "...";
    }
}
