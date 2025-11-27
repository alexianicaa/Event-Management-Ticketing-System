package com.eventmanagement.bookingservice.model;

/**
 * Payment method types
 * Used by Strategy pattern to select payment implementation
 */
public enum PaymentMethod {
    CREDIT_CARD,
    PAYPAL,
    DIGITAL_WALLET
}