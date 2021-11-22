package com.robenhood.model.orders;

import com.robenhood.data.JSON;
import com.robenhood.model.Crypto;
import com.robenhood.model.Transaction;
import java.time.OffsetDateTime;
import java.util.HashMap;

public class MarketTrade extends Order {

    public MarketTrade(boolean buy, Crypto crypto, double amount) {
        type = "Market Trade";
        this.buy = buy;
        this.crypto = crypto;
        this.amount = amount;
        createTime = OffsetDateTime.now();
        this.price = getPrice(createTime);
        // If the order for some reason takes a day to process then it will expire
        expireTime = OffsetDateTime.now().plusDays(1);
    }

    public MarketTrade(JSON json) {
        type = (String) json.get("type");
        buy = (boolean) json.get("buy");
        crypto = new Crypto((JSON) json.get("crypto"));
        amount = (double) json.get("amount");
        createTime = OffsetDateTime.parse((String) json.get("createTime"));
        price = (double) json.get("price");
        expireTime = OffsetDateTime.parse((String) json.get("expireTime"));
        transaction = new Transaction((JSON) json.get("transaction"));
    }

    @Override
    public void makeOrder(HashMap<OffsetDateTime, Double> data) {
        transaction = new Transaction(createTime, crypto, price, amount, buy, !createTime.isBefore(expireTime), type);
    }
}
