package com.robenhood.model.orders;

import com.robenhood.data.JSON;
import com.robenhood.data.JSONObject;
import com.robenhood.model.Crypto;
import com.robenhood.model.Transaction;
import java.time.OffsetDateTime;
import java.util.HashMap;

public abstract class Order implements JSONObject {
    protected boolean buy;
    protected double price;
    protected OffsetDateTime createTime;
    protected OffsetDateTime expireTime;
    protected Crypto crypto;
    protected Transaction transaction;
    protected String type;
    protected double amount;

    public abstract void makeOrder(HashMap<OffsetDateTime, Double> data);

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

    public Transaction executeOrder() {
        return transaction;
    }

    public Crypto getCrypto() {
        return crypto;
    }

    public void cancel() {
        expireTime = OffsetDateTime.now();
    }

    public String getType() {
        return type;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setExpireTime(OffsetDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public double getPrice(OffsetDateTime time) {
        return crypto.getValue(time);
    }

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
        System.out.println("\n\n\n\n\n\n\n\n\n");
        return json;
    }
}
