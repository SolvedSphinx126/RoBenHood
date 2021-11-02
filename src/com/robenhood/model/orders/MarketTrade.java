package com.robenhood.model.orders;

import com.robenhood.model.Crypto;
import com.robenhood.model.Transaction;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

public class MarketTrade extends Order {

    public MarketTrade(boolean buy, Crypto crypto, double amount) {
        type = "Market Trade";
        this.buy = buy;
        this.crypto = crypto;
        this.amount = amount;
        createTime = OffsetDateTime.now();
        this.price = getCurrentPrice(createTime);
    }

    @Override
    public void makeOrder(HashMap<OffsetDateTime, Double> data) {
        transaction = new Transaction(createTime, crypto, price, amount, buy);
    }

    @Override
    public Transaction executeOrder() {
        return transaction;
    }
}
