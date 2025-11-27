package com.eventmanagement.bookingservice.dto;

import com.eventmanagement.bookingservice.model.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//Payment details for different payment methods
public class PaymentRequest {

    private PaymentMethod paymentMethod;

    // Credit Card fields
    private String cardNumber;
    private String cvv;
    private String expiryDate;
    private String cardHolderName;

    // PayPal fields
    private String email;
    private String token;

    // Digital Wallet fields
    private String walletId;
    private String provider; // "ApplePay", "GooglePay"
    private String deviceId;
}