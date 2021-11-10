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

    public void updateOrders(HashMap<OffsetDateTime, Double> data) {
        for (Order order : orders) {
            order.makeOrder(data);
        }
    }

    public void executeOrders() {
        for (Order order: orders) {
            if (order.executeOrder() != null) {
                transactions.add(order.executeOrder());
                orders.remove(order);
            }
        }
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
}
