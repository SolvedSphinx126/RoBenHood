package com.robenhood.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;

public class Portfolio {
    private ArrayList<Asset> assets;
    private ArrayList<Transaction> transactions;
    private ArrayList<Transaction> completedTransactions;
    private ArrayList<Transaction> expiredTransactions;
    private String name;
    private double balance, totalValue;
    private OrderManager orderManager;
    private OffsetDateTime logOnTime, logOffTime, timeSinceLastUpdate;

    public Portfolio(String name) {
        this.name = name;
        assets = new ArrayList<>();
        transactions = new ArrayList<>();
        completedTransactions = new ArrayList<>();
        expiredTransactions = new ArrayList<>();
        balance = 0;
        totalValue = 0;
        orderManager = new OrderManager();
        logOnTime = OffsetDateTime.now();
        logOffTime = OffsetDateTime.now();
        timeSinceLastUpdate = logOffTime;
    }

    // Only for loading from file. Will need to make clone methods for all the
    // objects that are set to maintain encapsulation.
    public Portfolio(String name, ArrayList<Asset> assets, double balance, OrderManager OM, OffsetDateTime logOffTime) {
        this.name = name;
        this.assets = assets;
        this.balance = balance;
        orderManager = OM;
        this.logOffTime = logOffTime;
        timeSinceLastUpdate = logOffTime;
    }

    public void update() {
        // We must check to see if there are any orders for which we do not have assets yet
        // if so, add those assets to the list before proceeding
        System.out.println("Time since last update: " + logOffTime.toString() + "\nCurrent time " + OffsetDateTime.now().toString());
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
        // Will never not find the asset because if the list was lacking one
        // then it was added at the beginning of the update method.
        for (Transaction trans : transactions) {
            for (Asset asset : assets) {
                if ((asset.getCrypto().equals(trans.getCrypto()))) {
                    currAsset = asset;
                }
            }
            if (trans.getBuy() && balance >= trans.getValue()) {
                balance -= trans.getValue();
                currAsset.incrementAmount(trans.getAmount());
            } else if (!trans.getBuy() && currAsset.getAmount() >= trans.getAmount()) {
                currAsset.incrementAmount(-trans.getAmount());
                balance += trans.getValue();
            }

            // If the order did not execute because of insufficient balance it will still
            // be removed.
            completedTransactions.add(trans);
        }
        for (Transaction trans : completedTransactions) {
            transactions.remove(trans);
        }
    }

    public void addOrder(String type, Crypto crypto, boolean buy, double price, OffsetDateTime expireTime, double amount) {
        orderManager.addOrder(type, crypto, buy, price, expireTime, amount);
    }

    public double getPortfolioValue() {
        double total = 0;
        for (Asset asset : assets) {
            total += asset.getValue();
        }
        return total;
    }

    public double getBalance() {
        return balance;
    }

    public void incrementBalance(double value) {
        balance += value;
    }

    public void logIn() {
        logOnTime = OffsetDateTime.now();
    }

    public void logOut() {
        logOffTime = OffsetDateTime.now();
    }
}
