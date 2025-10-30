package org.example.strategy;

/**
 * Strategy Pattern Implementation
 * Interface defining the payment strategy
 */
public interface PaymentStrategy {
    boolean processPayment(double amount);
    boolean validatePayment();
    String getPaymentDetails();
}

