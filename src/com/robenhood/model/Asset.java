package com.robenhood.model;

import com.robenhood.data.JSON;
import com.robenhood.data.JSONObject;

import java.time.OffsetDateTime;

/**
 * A class to handle an Asset object
 * @author Jeremiah Rhoton
 */
public class Asset implements JSONObject {
    private Crypto coin;
    private double amount;
    private double totalCost;

    /**
     * Constructs a new asset with no sava data
     * @param coin The coin of the asset
     * @param amount The amount of the coin
     */
    public Asset(Crypto coin, double amount) {
        this.coin = coin;
        this.amount = amount;
    }

    /**
     * Constructs a new asset given save data
     * @param json The JSON object to construct from
     */
    public Asset(JSON json) {
        amount = (double) json.get("amount");
        totalCost = (double) json.get("totalCost");
        coin = new Crypto((JSON) json.get("coin"));
    }

    /**
     * Returns the asset's crypto object
     * @return The asset's crypto object
     */
    public Crypto getCrypto() {
        return coin;
    }

    /**
     * Increments the total cost of the asset
     * @param amount The amount to increment by
     */
    public void incrementTotalCost(double amount) {
        totalCost += amount;
    }

    /**
     * Returns the amount of coins in the asset
     * @return The amount of coins
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Increments the amount of coins in the asset
     * @param amount The amount to increment by
     */
    public void incrementAmount(double amount) {
        this.amount += amount;
    }

    /**
     * Returns the current market value of the asset
     * @return the current market value of the asset
     */
    public double getValue() {
        return API.getCryptoValue(OffsetDateTime.now(), coin.getSymbol()) * amount;
    }

    /**
     * Returns the average value of the asset
     * @return the average value of the asset
     */
    public double getAverageValue() {
        return totalCost / amount;
    }

    /**
     * Overridden method to return the data for the asset in string form
     * @return the data for the asset in string form
     */
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

    /**
     * Returns the data of the asset in a new JSON object
     * @return the JSON object containing the asset's data
     */
    @Override
    public JSON toJSON() {
        JSON json = new JSON();

        json.put("coin", coin);
        json.put("amount", amount);
        json.put("totalCost", totalCost);

        return json;
    }
}
