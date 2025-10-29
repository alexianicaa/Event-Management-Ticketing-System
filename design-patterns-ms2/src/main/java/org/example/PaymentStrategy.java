package org.example;

/**
 * Strategy Pattern Implementation
 * Interface defining the payment strategy
 */
public interface PaymentStrategy {
    boolean processPayment(double amount);
    boolean validatePayment();
    String getPaymentDetails();
}

class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String cvv;
    private String expiryDate;
    private String cardHolderName;

    public CreditCardPayment(String cardNumber, String cvv, String expiryDate, String cardHolderName) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
        this.cardHolderName = cardHolderName;
    }

    @Override
    public boolean validatePayment() {
        System.out.println("Validating Credit Card payment...");

        if (cardNumber == null || cardNumber.length() != 16) {
            System.out.println("Invalid card number");
            return false;
        }

        if (cvv == null || cvv.length() != 3) {
            System.out.println("Invalid CVV");
            return false;
        }

        if (expiryDate == null || !expiryDate.matches("\\d{2}/\\d{2}")) {
            System.out.println("Invalid expiry date format");
            return false;
        }

        System.out.println("Credit Card validation successful");
        return true;
    }

    @Override
    public boolean processPayment(double amount) {
        if (!validatePayment()) {
            return false;
        }

        System.out.println("Processing Credit Card payment...");
        System.out.println("Amount: $" + amount);
        System.out.println("Card: **** **** **** " + cardNumber.substring(12));

        // Simulate payment processing
        try {
            Thread.sleep(1000);
            System.out.println("Credit Card payment processed successfully!");
            return true;
        } catch (InterruptedException e) {
            System.err.println("Payment processing interrupted");
            return false;
        }
    }

    @Override
    public String getPaymentDetails() {
        return "Credit Card ending in " + cardNumber.substring(12) +
                " (Expires: " + expiryDate + ")";
    }
}

class PayPalPayment implements PaymentStrategy {

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

class DigitalWalletPayment implements PaymentStrategy {
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

class PaymentProcessor {
    private PaymentStrategy paymentStrategy;

    public PaymentProcessor() {
        // no default strategy
    }

    public PaymentProcessor(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
        System.out.println("Payment strategy set to: " + paymentStrategy.getClass().getSimpleName());
    }

    public boolean executePayment(double amount) {
        if (paymentStrategy == null) {
            System.err.println("No payment strategy set!");
            return false;
        }

        System.out.println("\n========== Payment Processing ==========");
        System.out.println("Selected Payment Method: " + paymentStrategy.getPaymentDetails());

        boolean result = paymentStrategy.processPayment(amount);

        if (result) {
            System.out.println("========== Payment Successful ==========\n");
        } else {
            System.out.println("========== Payment Failed ==========\n");
        }

        return result;
    }

    public PaymentStrategy getPaymentStrategy() {
        return paymentStrategy;
    }
}