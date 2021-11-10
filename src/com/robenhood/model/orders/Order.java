package com.robenhood.model.orders;

import com.robenhood.model.Crypto;
import com.robenhood.model.Transaction;
import java.time.OffsetDateTime;
import java.util.HashMap;

public abstract class Order {
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

        return createTime == ((Order) o).createTime;
    }

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

    public double getPrice(OffsetDateTime time) {
        return crypto.getValue(time);
    }
}
