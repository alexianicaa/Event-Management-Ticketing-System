package com.eventmanagement.bookingservice.strategy;

/**
 * STRATEGY PATTERN
 *
 * Interface defining the payment strategy contract.
 * Each payment method implements this interface with its own logic.
 */
public interface PaymentStrategy {

    boolean processPayment(double amount);

    boolean validatePayment();

    String getPaymentDetails();

    String getPaymentMethodName();
}