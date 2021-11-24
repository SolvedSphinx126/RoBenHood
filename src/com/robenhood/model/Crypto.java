package com.robenhood.model;

import com.robenhood.data.JSON;
import com.robenhood.data.JSONObject;

import java.time.OffsetDateTime;

public class Crypto implements JSONObject {
    private String name;
    private String symbol;

    public Crypto(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public Crypto(JSON json) {
        name = (String) json.get("name");
        symbol = (String) json.get("symbol");
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

    @Override
    public JSON toJSON() {
        JSON json = new JSON();

        json.put("name", name);
        json.put("symbol", symbol);

        return json;
    }

    public String toString() {
        String str = "";

        str += "Name: " + name;
        str += ", Symbol: " + symbol;
        return str;
    }
}
