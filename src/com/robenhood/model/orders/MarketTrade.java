/**
 * @author Jeremiah Rhoton
 */

package com.robenhood.model.orders;

import com.robenhood.data.JSON;
import com.robenhood.model.Crypto;
import com.robenhood.model.Transaction;
import java.time.OffsetDateTime;
import java.util.HashMap;

/**
 * A class to handle market trades
 */
public class MarketTrade extends Order {
    /**
     * Constructs a new market trade given no save data
     * @param buy Whether the trade is a buy or a sell
     * @param crypto The crypto of the trade
     * @param amount The amount of crypto in the trade
     */
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

    /**
     * Constructs a new market trade given JSON save data
     * @param json The JSON save data to construct from
     */
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

    /**
     * Creates a new transaction based off of the market trade
     * @param data The history data to execute off of
     */
    @Override
    public void makeOrder(HashMap<OffsetDateTime, Double> data) {
        transaction = new Transaction(createTime, crypto, price, amount, buy, !createTime.isBefore(expireTime), type);
    }
}
