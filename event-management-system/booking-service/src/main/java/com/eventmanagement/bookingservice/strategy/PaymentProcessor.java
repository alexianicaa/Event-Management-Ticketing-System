package com.eventmanagement.bookingservice.strategy;

/**
 * Context class that uses PaymentStrategy
 * Demonstrates Strategy Pattern by allowing payment method to be set/changed at runtime
 */
public class PaymentProcessor {

    private PaymentStrategy paymentStrategy;

    public PaymentProcessor() {
        // No default strategy
    }

    public PaymentProcessor(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
        System.out.println("Payment strategy set to: " + paymentStrategy.getPaymentMethodName());
    }

    public boolean executePayment(double amount) {
        if (paymentStrategy == null) {
            System.err.println("No payment strategy set!");
            return false;
        }

        System.out.println("Selected Payment Method: " + paymentStrategy.getPaymentDetails());

        boolean result = paymentStrategy.processPayment(amount);

        if (result) {
            System.out.println("=== PAYMENT SUCCESSFUL ===\n");
        } else {
            System.out.println("=== PAYMENT FAILED ===\n");
        }

        return result;
    }

    public PaymentStrategy getPaymentStrategy() {
        return paymentStrategy;
    }
}