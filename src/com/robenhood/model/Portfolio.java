package com.robenhood.model;

import com.robenhood.data.JSON;
import com.robenhood.data.JSONObject;
import com.robenhood.model.orders.Order;

import java.lang.reflect.Array;
import java.time.OffsetDateTime;
import java.util.ArrayList;

public class Portfolio implements JSONObject {
    private ArrayList<Asset> assets;
    private ArrayList<Transaction> transactions;
    private ArrayList<Transaction> completedTransactions;
    private ArrayList<Transaction> expiredTransactions;

    private String name;
    private double balance, totalValue;
    private OrderManager orderManager;
    private OffsetDateTime timeSinceLastUpdate;

    // For creating a new portfolio
    public Portfolio(String name) {
        assets = new ArrayList<>();
        transactions = new ArrayList<>();
        completedTransactions = new ArrayList<>();
        expiredTransactions = new ArrayList<>();
        orderManager = new OrderManager();

        this.name = name;
        balance = 0;
        totalValue = 0;
        timeSinceLastUpdate = OffsetDateTime.now();
    }

    // For creating a portfolio from save data
    public Portfolio(JSON json) {
        name = json.get("name").toString();
        balance = (double) json.get("balance");
        totalValue = (double) json.get("totalValue");
        timeSinceLastUpdate =  OffsetDateTime.parse((String) json.get("timeSinceLastUpdate"));
        orderManager = new OrderManager((JSON) json.get("orderManager"));

        assets = new ArrayList<>();
        transactions = new ArrayList<>();
        completedTransactions = new ArrayList<>();
        expiredTransactions = new ArrayList<>();

        for (JSON subJSON : (ArrayList<JSON>) json.get("assets")) {
            assets.add(new Asset(subJSON));
        }
        for (JSON subJSON : (ArrayList<JSON>) json.get("completedTransactions")) {
            completedTransactions.add(new Transaction(subJSON));
        }
        for (JSON subJSON : (ArrayList<JSON>) json.get("expiredTransactions")) {
            expiredTransactions.add(new Transaction(subJSON));
        }
    }

    public void update() {
        // We must check to see if there are any orders for which we do not have assets yet
        // if so, add those assets to the list before proceeding
        System.out.println("Time since last update: " + timeSinceLastUpdate.toString() + "\nCurrent time " + OffsetDateTime.now().toString());
        ArrayList<Crypto> lacking = orderManager.getLackingCryptos(assets);
        for (Crypto c : lacking) {
            assets.add(new Asset(c, 0));
        }

        for (Asset asset : assets) {
            orderManager.updateOrders(asset.getCrypto(), timeSinceLastUpdate, OffsetDateTime.now());
        }

        orderManager.executeOrders();
        transactions = orderManager.getTransactions();

        // Extract expired transactions
        for (Transaction trans : transactions) {
            if (trans.getExpired()) {
                expiredTransactions.add(trans);
            }
        }
        // Remove expired transactions
        for (Transaction trans : expiredTransactions) {
            transactions.remove(trans);
        }

        applyTransactions();

        totalValue = 0;
        for (Asset asset : assets) {
            totalValue += asset.getValue();
        }
        timeSinceLastUpdate = OffsetDateTime.now();
    }

    private void applyTransactions() {
        Asset currAsset = null;
        double val = 0;
        // Will never not find the asset because if the list was lacking one
        // then it was added at the beginning of the update method.
        for (Transaction trans : transactions) {
            for (Asset asset : assets) {
                if ((asset.getCrypto().equals(trans.getCrypto()))) {
                    currAsset = asset;
                }
            }
            val = trans.getValue();
            if (trans.getBuy() && balance >= trans.getValue()) {
                balance -= val;
                currAsset.incrementAmount(trans.getAmount());
                currAsset.incrementTotalCost(val);
            } else if (!trans.getBuy() && currAsset.getAmount() >= trans.getAmount()) {
                balance += val;
                currAsset.incrementAmount(-trans.getAmount());
                currAsset.incrementTotalCost(-val);
            }

            // If the order did not execute because of insufficient balance it will still
            // be removed.
            completedTransactions.add(trans);
        }
        for (Transaction trans : completedTransactions) {
            transactions.remove(trans);
        }
        // Transactions should always be empty after the update function. They were either executed or outdated.
    }

    public void addOrder(String type, Crypto crypto, boolean buy, double price, OffsetDateTime expireTime, double amount) {
        orderManager.addOrder(type, crypto, buy, price, expireTime, amount);
    }

    public void cancelOrder(OffsetDateTime createTime) {
        orderManager.cancelOrder(createTime);
    }

    public ArrayList<Order> getOrders() {
        return orderManager.getOrders();
    }

    public double getBalance() {
        return balance;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void incrementBalance(double value) {
        balance += value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // I think these methods break encapsulation
    public ArrayList<Transaction> getCompletedTransactions() {
        return completedTransactions;
    }

    public ArrayList<Transaction> getExpiredTransactions() {
        return expiredTransactions;
    }

    public ArrayList<Asset> getAssets() {
        return assets;
    }

    // For debugging purposes basically
    @Override
    public String toString() {
        String str = "";
        str += "Data For Portfolio: " + name;
        str += "\n   Assets:";
        for (Asset ass : assets) {
            str += "\n      ";
            str += ass.toString();
        }
        str += "\n   Completed Transactions:";
        for (Transaction trans : completedTransactions) {
            str += "\n      ";
            str += trans.toString();
        }

        str += "\n   Expired Transactions:";
        for (Transaction trans : expiredTransactions) {
            str += "\n      ";
            str += trans.toString();
        }

        str += "\n   Balance: " + balance;
        str += "\n   Total Value: " + totalValue;
        str += "\n   Time Since Last Update: " + timeSinceLastUpdate;

        return str;
    }

    @Override
    public JSON toJSON() {
        JSON json = new JSON();

        json.put("name", name);
        json.put("balance", balance);
        json.put("totalValue", totalValue);
        json.put("timeSinceLastUpdate", timeSinceLastUpdate.toString());
        json.put("orderManager", orderManager);

        json.put("assets", assets);
        json.put("completedTransactions", completedTransactions);
        json.put("expiredTransactions", expiredTransactions);

        return json;
    }
}
