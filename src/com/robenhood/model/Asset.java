package com.robenhood.model;

import java.time.OffsetDateTime;

public class Asset {
    private Crypto coin;
    private double amount;
    private double totalCost;

    public Asset(Crypto coin, double amount) {
        this.coin = coin;
        this.amount = amount;
    }

    public Crypto getCrypto() {
        return coin;
    }

    public void incrementTotalCost(double amount) {
        totalCost += amount;
    }

    public double getAmount() {
        return amount;
    }

    public void incrementAmount(double amount) {
        this.amount += amount;
    }

    public double getValue() {
        return API.getCryptoValue(coin.getSymbol(), OffsetDateTime.now()) * amount;
    }

    public double getAverageValue() {
        return totalCost / amount;
    }
}
