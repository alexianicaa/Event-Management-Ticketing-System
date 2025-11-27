package com.eventmanagement.bookingservice.strategy;

public class CreditCardPayment implements PaymentStrategy {

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
        System.out.println("Validating credit card details...");

        if (cardNumber == null || cardNumber.length() != 16) {
            System.out.println("Credit Card: Invalid card number");
            return false;
        }

        if (cvv == null || cvv.length() != 3) {
            System.out.println("Credit Card: Invalid CVV");
            return false;
        }

        if (expiryDate == null || !expiryDate.matches("\\d{2}/\\d{2}")) {
            System.out.println("Credit Card: Invalid expiry date");
            return false;
        }

        System.out.println("Credit Card: Validation successful");
        return true;
    }

    @Override
    public boolean processPayment(double amount) {
        if (!validatePayment()) {
            return false;
        }

        System.out.println("Credit Card: Processing payment...");
        System.out.println("Credit Card: Amount: $" + amount);
        System.out.println("Card: **** **** **** " + cardNumber.substring(12));

        // Simulate payment gateway processing
        try {
            Thread.sleep(1000);
            System.out.println("Credit Card: Payment processed successfully!");
            return true;
        } catch (InterruptedException e) {
            System.err.println("Credit Card: Payment processing interrupted");
            return false;
        }
    }

    @Override
    public String getPaymentDetails() {
        return "Credit Card ending in " + cardNumber.substring(12);
    }

    @Override
    public String getPaymentMethodName() {
        return "CREDIT_CARD";
    }
}