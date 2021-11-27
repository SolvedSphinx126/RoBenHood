/**
 * @author Jeremiah Rhoton
 */

package com.robenhood.model;

import com.robenhood.data.JSON;
import com.robenhood.data.JSONObject;
import com.robenhood.model.orders.Order;
import java.time.OffsetDateTime;
import java.util.ArrayList;

/**
 * A class to handle an individual portfolio object
 */
public class Portfolio implements JSONObject {
    private ArrayList<Asset> assets;
    private ArrayList<Transaction> transactions;
    private ArrayList<Transaction> completedTransactions;
    private ArrayList<Transaction> expiredTransactions;

    private String name;
    private double balance, totalValue;
    private OrderManager orderManager;
    private OffsetDateTime timeSinceLastUpdate;

    /**
     * Constructs a new portfolio given no save data
     * @param name The name of the new portfolio
     */
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

    /**
     * Constructs a new portfolio given JSON save data
     * @param json The JSON object containing the save data
     */
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

    /**
     * Updates the portfolio object and it's orders
     */
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

    /**
     * Private method to apply newly created transactions to the assets
     */
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

    /**
     * Adds a new order to the portfolio
     * @param type The type of order
     * @param crypto The crypto for the order
     * @param buy Whether the order is a buy or a sell
     * @param price The price at which to buy or sell
     * @param expireTime The time after which the order will expire
     * @param amount The amount of crypto to buy or sell
     */
    public void addOrder(String type, Crypto crypto, boolean buy, double price, OffsetDateTime expireTime, double amount) {
        orderManager.addOrder(type, crypto, buy, price, expireTime, amount);
    }

    /**
     * Cancel an order given it's create time
     * @param createTime The create time of the order to be canceled
     */
    public void cancelOrder(OffsetDateTime createTime) {
        orderManager.cancelOrder(createTime);
    }

    /**
     * Returns the list of working orders
     * @return The list of working orders
     */
    public ArrayList<Order> getOrders() {
        return orderManager.getOrders();
    }

    /**
     * Returns the balance of the portfolio
     * @return The balance of the portfolio
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Returns the portfolio's total value
     * @return The portfolio's total value
     */
    public double getTotalValue() {
        return totalValue;
    }

    /**
     * Increments the portfolio's balance
     * @param value The value to increment by
     */
    public void incrementBalance(double value) {
        balance += value;
    }

    /**
     * Returns the name of the portfolio
     * @return The name of the portfolio
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the portfolio
     * @param name The new name of the portfolio
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the list of completed transactions
     * @return The list of completed transactions
     */
    // I think these methods break encapsulation
    public ArrayList<Transaction> getCompletedTransactions() {
        return completedTransactions;
    }

    /**
     * Returns the list of expired transactions
     * @return The list of expired transactions
     */
    public ArrayList<Transaction> getExpiredTransactions() {
        return expiredTransactions;
    }

    /**
     * Returns the list of assets
     * @return The list of assets
     */
    public ArrayList<Asset> getAssets() {
        return assets;
    }

    /**
     * Overridden to string function
     * @return String containing the portfolio's data
     */
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

    /**
     * Returns a JSON object containing the portfolio's save data
     * @return The JSON object containing the portfolio's save data
     */
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
