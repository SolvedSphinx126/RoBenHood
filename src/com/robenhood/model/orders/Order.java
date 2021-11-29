package com.robenhood.model.orders;

import com.robenhood.data.JSON;
import com.robenhood.data.JSONObject;
import com.robenhood.model.Crypto;
import com.robenhood.model.Transaction;
import java.time.OffsetDateTime;
import java.util.HashMap;

/**
 * A class to handle generic orders
 * @author Jeremiah Rhoton
 */
public abstract class Order implements JSONObject {
    protected boolean buy;
    protected double price;
    protected OffsetDateTime createTime;
    protected OffsetDateTime expireTime;
    protected Crypto crypto;
    protected Transaction transaction;
    protected String type;
    protected double amount;

    /**
     * Method to create a transaction based off of history data
     * @param data The history data to execute off of
     */
    public abstract void makeOrder(HashMap<OffsetDateTime, Double> data);

    /**
     * Checks order objects for equality
     * @param o The object to compare
     * @return The equality of the objects
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Order)) {
            return false;
        }

        return createTime.equals(((Order) o).createTime);
    }

    /**
     * Returns the transaction created from the order
     * @return The transaction created from the order
     */
    public Transaction executeOrder() {
        return transaction;
    }

    /**
     * Returns the crypto of the order
     * @return The crypto of the order
     */
    public Crypto getCrypto() {
        return crypto;
    }

    /**
     * Returns the orders create time
     * @return The orders create time
     */
    public OffsetDateTime getCreateTime() {
        return createTime;
    }

    /**
     * Cancels an order
     */
    public void cancel(OffsetDateTime createTime) {
        expireTime = createTime;
    }

    /**
     * Returns the type of the order
     * @return The type of the order
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the price of the order
     * @param price The price of the order
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the expiration time of the order
     * @param expireTime The new expiration time of the order
     */
    public void setExpireTime(OffsetDateTime expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * Returns the price of the order's crypto
     * @param time The time for which to check the price
     * @return The price of the order's crypto
     */
    public double getPrice(OffsetDateTime time) {
        return crypto.getValue(time);
    }

    /**
     * Returns a JSON object containing the order's save data
     * @return The JSON object containing the order's save data
     */
    @Override
    public JSON toJSON(){
        JSON json = new JSON();

        json.put("buy", buy);
        json.put("price", price);
        json.put("createTime", createTime.toString());
        json.put("expireTime", expireTime.toString());
        json.put("crypto", crypto);
        json.put("transaction", transaction);
        json.put("type", type);
        json.put("amount", amount);
        return json;
    }

    /**
     * Returns a string containing the orders data
     * @return The string containing the orders data
     */
    @Override
    public String toString() {
        String str = "";

        str += "Type: " + type;
        str += ", Amount: " + amount;
        str += ", Crypto: " + crypto;
        str += ",\n    ";
        str += "Buy: " + buy;
        str += ", Price: " + price;
        str += ",\n    ";
        str += "CreateTime: " + createTime;
        str += ", ExpireTime: " + expireTime;
        str += ",\n    ";
        if (transaction == null) {
            str += "Transaction: ";
        } else {
            str += "Transaction: " + transaction.toString();
        }

        return str;
    }
}
