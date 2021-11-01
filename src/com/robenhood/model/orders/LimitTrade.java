package com.robenhood.model.orders;

import com.robenhood.model.Crypto;
import com.robenhood.model.Transaction;

import java.time.OffsetDateTime;

public class LimitTrade extends Order {

    public LimitTrade(boolean buy, double price, OffsetDateTime expireTime, Crypto crypto) {
        type = "Limit Trade";
        this.buy = buy;
        this.expireTime = expireTime;
        this.crypto = crypto;
    }

    @Override
    public void makeOrder() {

    }

    @Override
    public Transaction executeOrder() {
        return null;
    }
}
