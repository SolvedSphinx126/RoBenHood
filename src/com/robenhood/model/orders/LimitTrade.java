package com.robenhood.model.orders;

import com.robenhood.data.JSON;
import com.robenhood.model.Crypto;
import com.robenhood.model.Transaction;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

public class LimitTrade extends Order {

    public LimitTrade(boolean buy, double price, OffsetDateTime expireTime, Crypto crypto, double amount) {
        type = "Limit Trade";
        this.buy = buy;
        this.expireTime = expireTime;
        this.crypto = crypto;
        this.price = price;
        this.amount = amount;
        createTime = OffsetDateTime.now();
    }

    public LimitTrade(JSON json) {
        type = (String) json.get("type");
        buy = (boolean) json.get("buy");
        crypto = new Crypto((JSON) json.get("crypto"));
        amount = (double) json.get("amount");
        createTime = OffsetDateTime.parse((String) json.get("createTime"));
        price = (double) json.get("price");
        expireTime = OffsetDateTime.parse((String) json.get("expireTime"));
        if (json.get("transaction").equals("null")) {
            transaction = null;
        } else {
            transaction = new Transaction((JSON) json.get("transaction"));
        }
    }

    @Override
    public void makeOrder(HashMap<OffsetDateTime, Double> data) {
        for (Map.Entry<OffsetDateTime, Double> entry : data.entrySet()) {
            // This means the order is expired
            if (expireTime.isBefore(entry.getKey())) {
                transaction = new Transaction(entry.getKey(), crypto, price, amount, buy, true, type);
            } else if (buy && entry.getValue() <= price) {
                transaction = new Transaction(entry.getKey(), crypto, entry.getValue(), amount, true, false, type);
            } else if (!buy && entry.getValue() >= price) {
                transaction = new Transaction(entry.getKey(), crypto, entry.getValue(), amount, false, false, type);
            }
        }
    }
}
