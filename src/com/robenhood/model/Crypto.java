package com.robenhood.model;

import com.robenhood.model.orders.Order;

import java.time.OffsetDateTime;

public class Crypto {
    private String name;
    private String symbol;

    public Crypto(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Crypto)) {
            return false;
        }

        return symbol.equals(((Crypto) o).getSymbol());
    }

    public double getValue(OffsetDateTime time) {
        return API.getCryptoValue(time, symbol);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
