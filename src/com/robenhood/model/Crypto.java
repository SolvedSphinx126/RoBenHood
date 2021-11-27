/**
 * @author Jeremiah Rhoton
 */

package com.robenhood.model;

import com.robenhood.data.JSON;
import com.robenhood.data.JSONObject;
import java.time.OffsetDateTime;

/**
 * A class to handle a Crypto object
 */
public class Crypto implements JSONObject {
    private String name;
    private String symbol;

    /**
     * Constructs a new crypto object given no save data
     * @param name The name of the crypto coin
     * @param symbol The ticker symbol
     */
    public Crypto(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    /**
     * Constructs a new crypto given save data
     * @param json The json object containing the data
     */
    public Crypto(JSON json) {
        name = (String) json.get("name");
        symbol = (String) json.get("symbol");
    }

    /**
     * Overridden equals that compares the symbol of the crypto
     * @param o The object to be compared
     * @return Value of comparison
     */
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

    /**
     * Returns the value of the crypto
     * @param time The time for which to retrieve the value
     * @return The crypto's value
     */
    public double getValue(OffsetDateTime time) {
        return API.getCryptoValue(time, symbol);
    }

    /**
     * Returns the name of the crypto
     * @return The name of the crypto
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the crypto
     * @param name The new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the symbol of the crypto
     * @return The symbol of the crypto
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Sets the symbol of the crypto
     * @param symbol The new symbol
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Turns the crypto object into a JSON object for saving
     * @return New JSON object containing the crypto's data
     */
    @Override
    public JSON toJSON() {
        JSON json = new JSON();

        json.put("name", name);
        json.put("symbol", symbol);

        return json;
    }

    /**
     * Returns the data of the crypto in string format
     * @return The string containing the crypto's data
     */
    public String toString() {
        String str = "";

        str += "Name: " + name;
        str += ", Symbol: " + symbol;
        return str;
    }
}
