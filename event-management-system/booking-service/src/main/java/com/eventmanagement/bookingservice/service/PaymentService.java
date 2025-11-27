package com.eventmanagement.bookingservice.service;

import com.eventmanagement.bookingservice.dto.PaymentRequest;
import com.eventmanagement.bookingservice.strategy.*;
import org.springframework.stereotype.Service;

// Service that demonstrates Strategy Pattern for payment processing
@Service
public class PaymentService {
    // Process payment using appropriate strategy based on payment method
    public boolean processPayment(PaymentRequest paymentRequest, double amount) {
        System.out.println("\nPayment Service: Processing payment of $" + amount);

        PaymentProcessor processor = new PaymentProcessor();

        PaymentStrategy strategy = createPaymentStrategy(paymentRequest);
        processor.setPaymentStrategy(strategy);

        return processor.executePayment(amount);
    }

    // Create appropriate payment strategy based on payment method
    private PaymentStrategy createPaymentStrategy(PaymentRequest request) {
        System.out.println("Creating payment strategy: " + request.getPaymentMethod());

        switch (request.getPaymentMethod()) {
            case CREDIT_CARD:
                return new CreditCardPayment(
                        request.getCardNumber(),
                        request.getCvv(),
                        request.getExpiryDate(),
                        request.getCardHolderName()
                );

            case PAYPAL:
                return new PayPalPayment(
                        request.getEmail(),
                        request.getToken()
                );

            case DIGITAL_WALLET:
                return new DigitalWalletPayment(
                        request.getWalletId(),
                        request.getProvider(),
                        request.getDeviceId()
                );

            default:
                throw new IllegalArgumentException("Unsupported payment method: " + request.getPaymentMethod());
        }
    }
}