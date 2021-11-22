package com.robenhood.model;

import com.robenhood.data.JSON;
import com.robenhood.data.JSONObject;

import java.time.OffsetDateTime;

public class Transaction implements JSONObject {
    private OffsetDateTime time;
    private Crypto crypto;
    private double price;
    private double amount;
    private boolean buy;
    private boolean expired;
    private String type;

    public Transaction(OffsetDateTime time, Crypto crypto, double price, double amount, boolean buy, boolean expired, String type) {
        this.time = time;
        this.crypto = crypto;
        this.price = price;
        this.amount = amount;
        this.buy = buy;
        this.expired = expired;
        this.type = type;
    }

    public Transaction(JSON json) {
        time = OffsetDateTime.parse((String) json.get("time"));
        crypto = new Crypto((JSON) json.get("crypto"));
        price = (double) json.get("price");
        amount = (double) json.get("amount");
        buy = (boolean) json.get("buy");
        expired = (boolean) json.get("expired");
        type = (String) json.get("type");
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
