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
    }

    @Override
    public void makeOrder(HashMap<OffsetDateTime, Double> data) {
        if (buy) {
            for (Map.Entry<OffsetDateTime, Double> entry : data.entrySet()) {
                if (entry.getValue() <= price) {
                    transaction = new Transaction(entry.getKey(), crypto, entry.getValue(), amount, true);
                }
            }
        } else {
            for (Map.Entry<OffsetDateTime, Double> entry : data.entrySet()) {
                if (entry.getValue() >= price) {
                    transaction = new Transaction(entry.getKey(), crypto, entry.getValue(), amount, false);
                }
            }
        }
    }

    @Override
    public Transaction executeOrder() {
        return null;
    }
}
