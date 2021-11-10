package com.robenhood.model;

import java.time.OffsetDateTime;

public class Crypto {
    private String name;
    private String symbol;

    public Crypto(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
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
