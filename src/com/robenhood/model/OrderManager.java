package com.robenhood.model;

import com.robenhood.model.orders.LimitTrade;
import com.robenhood.model.orders.MarketTrade;
import com.robenhood.model.orders.Order;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class OrderManager {
    private ArrayList<Order> orders;
    private ArrayList<Transaction> transactions;

    public OrderManager() {
        orders = new ArrayList<>();
        transactions = new ArrayList<>();
    }

    public void addOrder(String type, Crypto crypto, boolean buy, double price, OffsetDateTime expireTime, double amount) {
        if (type.equals("Limit Trade")) {
            orders.add(new LimitTrade(buy, price, expireTime, crypto, amount));
        } else if (type.equals("Market Trade")) {
            orders.add(new MarketTrade(buy, crypto, amount));
        }
    }

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

    // Updates all orders of a specific Crypto
    public void updateOrders(Crypto crypto, OffsetDateTime startTime, OffsetDateTime endTime) {
        HashMap<OffsetDateTime, Double> data = API.getHistoryData(startTime, endTime, crypto.getSymbol());
        for (Order order : orders) {
            if (order.getCrypto().equals(crypto)) {
                order.makeOrder(data);
            }
        }
    }

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

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
}
