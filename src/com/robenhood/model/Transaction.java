package com.robenhood.model;

import java.time.OffsetDateTime;

public class Transaction {
    private OffsetDateTime time;
    private Crypto crypto;
    private double price;
    private double amount;
    private boolean buy;

    public Transaction(OffsetDateTime time, Crypto crypto, double price, double amount, boolean buy) {
        this.time = time;
        this.crypto = crypto;
        this.price = price;
        this.amount = amount;
        this.buy = buy;
    }

    public OffsetDateTime getTime() {
        return time;
    }

    public Crypto getCrypto() {
        return crypto;
    }

    public double getPrice() {
        return price;
    }

    public double getAmount() {
        return amount;
    }
}
