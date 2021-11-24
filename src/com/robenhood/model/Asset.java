package com.robenhood.model;

import com.robenhood.data.JSON;
import com.robenhood.data.JSONObject;

import java.time.OffsetDateTime;

public class Asset implements JSONObject {
    private Crypto coin;
    private double amount;
    private double totalCost;

    public Asset(Crypto coin, double amount) {
        this.coin = coin;
        this.amount = amount;
    }

    public Asset(JSON json) {
        amount = (double) json.get("amount");
        totalCost = (double) json.get("totalCost");
        coin = new Crypto((JSON) json.get("coin"));
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
        return API.getCryptoValue(OffsetDateTime.now(), coin.getSymbol()) * amount;
    }

    public double getAverageValue() {
        return totalCost / amount;
    }

    // For debugging mostly
    @Override
    public String toString() {
        String str = "";
        str += "Crypto: " + coin.getName();
        str += ", Amount: " + amount;
        str += ", Total Cost: " + totalCost;
        str += ",\n    ";
        str += "Average Value (totalMoneySpent / totalAmountOfCoin): " + getAverageValue();
        return str;
    }

    @Override
    public JSON toJSON() {
        JSON json = new JSON();

        json.put("coin", coin);
        json.put("amount", amount);
        json.put("totalCost", totalCost);

        return json;
    }
}
