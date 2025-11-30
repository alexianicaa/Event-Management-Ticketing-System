package com.eventmanagement.bookingservice.strategy;

/**
 * Concrete Strategy: Digital Wallet Payment (Apple Pay, Google Pay, etc.)
 */
public class DigitalWalletPayment implements PaymentStrategy {

    private String walletId;
    private String provider; // "ApplePay", "GooglePay"
    private String deviceId;

    public DigitalWalletPayment(String walletId, String provider, String deviceId) {
        this.walletId = walletId;
        this.provider = provider;
        this.deviceId = deviceId;
    }

    @Override
    public boolean validatePayment() {
        System.out.println("DigitalWallet[" + provider.toUpperCase() + "] Validating wallet...");

        if (walletId == null || walletId.isEmpty()) {
            System.out.println("DigitalWallet[" + provider.toUpperCase() + "] Invalid wallet ID");
            return false;
        }

        if (deviceId == null || deviceId.isEmpty()) {
            System.out.println("DigitalWallet[" + provider.toUpperCase() + "] Invalid device ID");
            return false;
        }

        System.out.println("DigitalWallet[" + provider.toUpperCase() + "] Validation successful");
        return true;
    }

    @Override
    public boolean processPayment(double amount) {
        if (!validatePayment()) {
            return false;
        }

        System.out.println("DigitalWallet[" + provider.toUpperCase() + "] Processing payment...");
        System.out.println("DigitalWallet[" + provider.toUpperCase() + "] Amount: $" + amount);
        System.out.println("DigitalWallet[" + provider.toUpperCase() + "] Wallet ID: " + walletId.substring(0, 8) + "...");

        // Simulate authentication
        try {
            System.out.println("DigitalWallet[" + provider.toUpperCase() + "] Waiting for authentication...");
            Thread.sleep(800);
            System.out.println("DigitalWallet[" + provider.toUpperCase() + "] Authentication successful!");
            Thread.sleep(500);
            System.out.println("DigitalWallet[" + provider.toUpperCase() + "] Payment processed successfully!");
            return true;
        } catch (InterruptedException e) {
            System.err.println("DigitalWallet[" + provider.toUpperCase() + "] Payment processing interrupted");
            return false;
        }
    }

    @Override
    public String getPaymentDetails() {
        return provider + " - Wallet ID: " + walletId.substring(0, 8) + "...";
    }

    @Override
    public String getPaymentMethodName() {
        return "DIGITAL_WALLET";
    }
}