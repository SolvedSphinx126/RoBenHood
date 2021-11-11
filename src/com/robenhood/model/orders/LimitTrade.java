package com.robenhood.model.orders;

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

    @Override
    public void makeOrder(HashMap<OffsetDateTime, Double> data) {
        for (Map.Entry<OffsetDateTime, Double> entry : data.entrySet()) {
            if (buy && entry.getValue() <= price && entry.getKey().isBefore(expireTime)) {
                transaction = new Transaction(entry.getKey(), crypto, entry.getValue(), amount, true);
            } else if (!buy && entry.getValue() >= price && entry.getKey().isBefore(expireTime)) {
                transaction = new Transaction(entry.getKey(), crypto, entry.getValue(), amount, false);
            }
        }
    }
}
