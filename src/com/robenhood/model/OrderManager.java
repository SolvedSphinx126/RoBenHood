package com.robenhood.model;

import com.robenhood.data.JSON;
import com.robenhood.data.JSONObject;
import com.robenhood.model.orders.LimitTrade;
import com.robenhood.model.orders.MarketTrade;
import com.robenhood.model.orders.Order;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class to handle a portfolio's orders
 * @author Jeremiah Rhoton
 */
public class OrderManager implements JSONObject {
    private ArrayList<Order> orders;
    private ArrayList<Transaction> transactions;

    /**
     * Constructs a new order manager given no save data
     */
    public OrderManager() {
        orders = new ArrayList<>();
        transactions = new ArrayList<>();
    }

    /**
     * Constructs a new order manager given JSON save data
     * @param json The JSON object containing the order manager's data
     */
    public OrderManager(JSON json) {
        orders = new ArrayList<>();
        transactions = new ArrayList<>();

        for (JSON subJSON : (ArrayList<JSON>) json.get("orders")) {
            if (subJSON.get("type").equals("Limit Trade")) {
                orders.add(new LimitTrade(subJSON));
            } else if (subJSON.get("type").equals("Market Trade")) {
                orders.add(new MarketTrade(subJSON));
            }
        }
        for (JSON subJSON : (ArrayList<JSON>) json.get("transactions")) {
            transactions.add(new Transaction(subJSON));
        }
    }

    /**
     * Adds a new order to the order manager
     * @param type The type of order
     * @param crypto The crypto for the order
     * @param buy Whether the order is a buy or a sell
     * @param price The price at which to buy or sell
     * @param expireTime The time after which the order will expire
     * @param amount The amount of crypto to buy or sell
     */
    public void addOrder(String type, Crypto crypto, boolean buy, double price, OffsetDateTime expireTime, double amount) {
        if (type.equals("Limit Trade")) {
            orders.add(new LimitTrade(buy, price, expireTime, crypto, amount));
        } else if (type.equals("Market Trade")) {
            orders.add(new MarketTrade(buy, crypto, amount));
        }
    }

    /**
     * Cancels an order given the create time of the order to be canceled
     * @param createTime The create time of the order to be canceled
     */
    public void cancelOrder(OffsetDateTime createTime) {
        for (Order o : orders) {
            if (o.getCreateTime().toString().equals(createTime.toString())) {
                o.cancel();
            }
        }
    }

    /**
     * Returns the current order list
     * @return The current order list
     */
    public ArrayList<Order> getOrders() {
        return orders;
    }

    /**
     * Returns an array list of crypto objects that orders need, but the portfolio's list of assets does not contain
     * @param assets The list of assets to be compared
     * @return Array list of crypto objects that must be added to the asset list
     */
    public ArrayList<Crypto> getLackingCryptos(ArrayList<Asset> assets) {
        boolean has = false;
        ArrayList<Crypto> lacking = new ArrayList<>();
        for (Order order : orders) {
            for (Asset asset : assets) {
                if (asset.getCrypto().equals(order.getCrypto())) {
                    has = true;
                    break;
                }
            }
            if (!has) {
                lacking.add(order.getCrypto());
            }
            has = false;
        }
        return lacking;
    }

    /**
     * Updates all orders of a specific crypto
     * @param crypto The crypto of the orders to update
     * @param startTime The time to start updating the orders
     * @param endTime The time to finish updating the orders
     */
    // Updates all orders of a specific Crypto
    public void updateOrders(Crypto crypto, OffsetDateTime startTime, OffsetDateTime endTime) {
        HashMap<OffsetDateTime, Double> data = API.getHistoryData(startTime, endTime, crypto.getSymbol());
        for (Order order : orders) {
            if (order.getCrypto().equals(crypto)) {
                order.makeOrder(data);
            }
        }
    }

    /**
     * Execute all standing orders
     */
    public void executeOrders() {
        ArrayList<Order> executed = new ArrayList<>();
        for (Order order : orders) {
            if (order.executeOrder() != null) {
                transactions.add(order.executeOrder());
                executed.add(order);
            }
        }
        for (Order order : executed) {
            orders.remove(order);
        }
    }

    /**
     * Returns all completed orders in the form of a transaction
     * @return The transaction created by an executed order
     */
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Returns the data of the order manager in a JSON object
     * @return The JSON object containing the order manager's data
     */
    @Override
    public JSON toJSON() {
        JSON json = new JSON();

        json.put("orders", orders);
        json.put("transactions", transactions);

        return json;
    }
}
