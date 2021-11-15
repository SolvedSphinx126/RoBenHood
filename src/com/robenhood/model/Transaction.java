package com.robenhood.model;

import com.robenhood.model.orders.Order;

import java.time.OffsetDateTime;

public class Transaction {
    private OffsetDateTime time;
    private Crypto crypto;
    private double price;
    private double amount;
    private boolean buy;
    private boolean expired;

    public Transaction(OffsetDateTime time, Crypto crypto, double price, double amount, boolean buy, boolean expired) {
        this.time = time;
        this.crypto = crypto;
        this.price = price;
        this.amount = amount;
        this.buy = buy;
        this.expired = expired;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Transaction)) {
            return false;
        }

        return time.equals(((Transaction) o).time);
    }

    public OffsetDateTime getTime() {
        return time;
    }

    public boolean getExpired() {
        return expired;
    }

    public boolean getBuy() {
        return buy;
    }

    public double getValue() {
        return price * amount;
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
