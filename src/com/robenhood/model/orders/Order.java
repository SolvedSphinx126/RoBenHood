package com.robenhood.model.orders;

import com.robenhood.model.Crypto;
import com.robenhood.model.Transaction;
import java.time.OffsetDateTime;

public abstract class Order {
    protected boolean buy;
    protected double price;
    protected OffsetDateTime createTime;
    protected OffsetDateTime expireTime;
    protected Crypto crypto;
    protected Transaction transaction;
    protected String type;

    public abstract void makeOrder();

    public abstract Transaction executeOrder();

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

    public double getCurrentPrice(OffsetDateTime time) {
        return crypto.getCurrentPrice();
    }
}
