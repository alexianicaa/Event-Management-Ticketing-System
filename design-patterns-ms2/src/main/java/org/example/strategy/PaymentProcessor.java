package org.example.strategy;

public class PaymentProcessor {
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
