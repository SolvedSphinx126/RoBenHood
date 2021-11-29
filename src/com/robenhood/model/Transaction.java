package com.robenhood.model;

import com.robenhood.data.JSON;
import com.robenhood.data.JSONObject;
import java.time.OffsetDateTime;

/**
 * Class to handle a transaction object
 * @author Jeremiah Rhoton
 */
public class Transaction implements JSONObject {
    private OffsetDateTime time;
    private Crypto crypto;
    private double price;
    private double amount;
    private boolean buy;
    private boolean expired;
    private String type;

    /**
     * Constructs a new transaction given no save data
     * @param time The transaction's execution time
     * @param crypto The transaction's crypto
     * @param price The transaction's price
     * @param amount The amount of crypto in the transaction
     * @param buy Whether the transaction is a buy or sell
     * @param expired Whether the transaction is expired
     * @param type The type of transaction
     */
    public Transaction(OffsetDateTime time, Crypto crypto, double price, double amount, boolean buy, boolean expired, String type) {
        this.time = time;
        this.crypto = crypto;
        this.price = price;
        this.amount = amount;
        this.buy = buy;
        this.expired = expired;
        this.type = type;
    }

    /**
     * Creates a new transaction given JSON save data
     * @param json The JSON object containing the transactions save data
     */
    public Transaction(JSON json) {
        time = OffsetDateTime.parse((String) json.get("time"));
        crypto = new Crypto((JSON) json.get("crypto"));
        price = (double) json.get("price");
        amount = (double) json.get("amount");
        buy = (boolean) json.get("buy");
        expired = (boolean) json.get("expired");
        type = (String) json.get("type");
    }

    /**
     * Compares two transaction objects for equality
     * @param o The object to be compared
     * @return Truth of the equals
     */
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

    /**
     * Returns the transaction's execution time
     * @return The transaction's execution time
     */
    public OffsetDateTime getTime() {
        return time;
    }

    /**
     * Returns whether the transaction is expired
     * @return Boolean representing if the transaction is expired
     */
    public boolean getExpired() {
        return expired;
    }

    /**
     * Returns if the transaction was a buy or sell
     * @return Boolean representing if the transaction was a buy or sell
     */
    public boolean getBuy() {
        return buy;
    }

    /**
     * Returns the value of the transaction
     * @return The value of the transaction
     */
    public double getValue() {
        return price * amount;
    }

    /**
     * Returns the crypto of the transaction
     * @return The crypto of the transaction
     */
    public Crypto getCrypto() {
        return crypto;
    }

    /**
     * Returns the price of the transaction
     * @return The price of the transaction
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the amount of cryptos in the transaction
     * @return The amount of cryptos in the transaction
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Returns a string containing the transaction's data
     * @return The string containing the transaction's data
     */
    // For debugging purposes mostly
    @Override
    public String toString() {
        String str = "";
        str += "Type: " + type;
        str += ", Time: " + time;
        str += ", Crypto: " + crypto.getName();
        str += ", Price: " + price;
        str += ", Amount: " + amount;
        str += ", Buy: " + buy;
        str += ", Expired: " + expired;
        return str;
    }

    /**
     * Returns a JSON object containing the transaction's save data
     * @return The JSON object containing the transaction's save data
     */
    @Override
    public JSON toJSON() {
        JSON json = new JSON();

        json.put("time", time.toString());
        json.put("crypto", crypto);
        json.put("price", price);
        json.put("amount", amount);
        json.put("buy", buy);
        json.put("expired", expired);
        json.put("type", type);

        return json;
    }
}
