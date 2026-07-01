package org.example.payment;

import org.example.model.PaymentResult;

public class CryptoWalletPayment extends PaymentMethod{
    private final String key;
    private double balance;

    public CryptoWalletPayment(String key, double balance) {
        super("GiftCard");
        this.key = key;
        this.balance = balance;
    }

    @Override
    public PaymentResult processPayment(double amount){
        if (amount > balance) return new PaymentResult(false, "Not enough funds");
        if (key.length() < 12 || key.length() > 24) return new PaymentResult(false, "Invalid wallet key");
        balance -= amount;
        return new PaymentResult(true, "Paid " + amount + " using crypto wallet");
    }
}
