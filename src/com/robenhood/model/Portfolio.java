package com.robenhood.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;

public class Portfolio {
    private ArrayList<Asset> assets;
    private ArrayList<Transaction> transactions;
    private String name;
    private double balance, totalValue;
    private OrderManager orderManager;
    private OffsetDateTime logOnTime, logOffTime, timeSinceLastUpdate;

    public Portfolio(String name) {
        this.name = name;
        assets = new ArrayList<>();
        timeSinceLastUpdate = OffsetDateTime.now();
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
        for (Asset asset : assets) {
            orderManager.updateOrders(asset.getCrypto(), timeSinceLastUpdate, OffsetDateTime.now());
        }

        orderManager.executeOrders();
        transactions = orderManager.getTransactions();

        applyTransactions();

        totalValue = 0;
        for (Asset asset : assets) {
            totalValue += asset.getValue();
        }
        timeSinceLastUpdate = OffsetDateTime.now();
    }

    private void applyTransactions() {
        boolean hasCrypto;
        Asset currAsset = null;
        for (Transaction trans : transactions) {
            hasCrypto = false;
            // Check to see if the asset list contains the crypto for which we are making a transaction
            for (Asset asset : assets) {
                if ((asset.getCrypto().equals(trans.getCrypto()))) {
                    hasCrypto = true;
                    break;
                }
            }
            // Add the new asset if it was not found already
            if (!hasCrypto) {
                assets.add(new Asset(trans.getCrypto(), 0));
            }

            for (Asset asset : assets) {
                if ((asset.getCrypto().equals(trans.getCrypto()))) {
                    currAsset = asset;
                }
            }
            if (trans.getBuy() && balance >= trans.getValue()) {
                balance -= trans.getValue();
                currAsset.incrementAmount(trans.getAmount());
            } else if (currAsset.getAmount() >= trans.getAmount()) {
                currAsset.incrementAmount(-trans.getAmount());
                balance += trans.getValue();
            }

            // If the order did not execute because of insufficient balance it will still
            // be removed.
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

    public void logIn() {
        logOnTime = OffsetDateTime.now();
    }

    public void logOut() {
        logOffTime = OffsetDateTime.now();
    }
}
