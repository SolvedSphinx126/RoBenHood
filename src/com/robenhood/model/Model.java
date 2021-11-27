/**
 * @author Jeremiah Rhoton
 */

package com.robenhood.model;

import com.robenhood.data.FileManager;
import com.robenhood.data.JSON;
import com.robenhood.model.orders.Order;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A model that handles switching portfolio objects
 */
public class Model {
    private Portfolio currentPortfolio;
    private ArrayList<String> potentialPortfolios;

    /**
     * Creates a new model
     */
    public Model() {
        potentialPortfolios = new ArrayList<>();
        potentialPortfolios.addAll(Arrays.asList(FileManager.getPortfolioPaths()));
    }

    /**
     * Updates the model's portfolio list from file data
     */
    private void updatePortfolioList() {
        // I'm not sure if addAll will make duplicates so clear the list first
        potentialPortfolios.clear();
        potentialPortfolios.addAll(Arrays.asList(FileManager.getPortfolioPaths()));
    }

    /**
     * Sets the current portfolio to one matching the given name
     * @param name The name of the portfolio to switch to
     * @return Success of the operation
     */
    public boolean setCurrentPortfolio(String name) {
        saveCurrentPortfolio();
        try {
            Portfolio p = new Portfolio(new JSON(FileManager.loadStringFromFile(name)));
            currentPortfolio = p;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Creates a new portfolio given a name and switches to it
     * @param name The name of the new portfolio
     */
    public void createNewPortfolio(String name) {
        saveCurrentPortfolio();
        Portfolio p = new Portfolio(name);
        currentPortfolio = p;
        // Save the new portfolio to add the name to the list
        FileManager.saveStringToFile(p.toJSON().toString(), name);
    }

    /**
     * Saves the current portfolio to a file
     */
    public void saveCurrentPortfolio() {
        // If null nothing to save
        if (currentPortfolio != null) {
            FileManager.saveStringToFile(currentPortfolio.toJSON().toString(), currentPortfolio.getName());
        }
    }

    /**
     * Deletes the file for a portfolio given a name if the name is not the current portfolio
     * @param name The name of the portfolio to delete
     * @return Success of the operation
     */
    public boolean deletePortfolio(String name) {
        // Will not delete the current portfolio
        if (currentPortfolio != null && !currentPortfolio.getName().equals(name)) {
            return FileManager.deleteFile(name);
        }
        return false;
    }

    /**
     * Creates a new order and adds it to the current portfolio
     * @param type The type of order
     * @param crypto The crypto for the order
     * @param buy Whether the order is a buy or a sell
     * @param price The price at which to buy or sell
     * @param expireTime The time after which the order will expire
     * @param amount The amount of crypto to buy or sell
     */
    public void addOrder(String type, Crypto crypto, boolean buy, double price, OffsetDateTime expireTime, double amount) {
        if (currentPortfolio == null) {
            return;
        }
        currentPortfolio.addOrder(type, crypto, buy, price, expireTime, amount);
    }

    /**
     * Cancel an order given it's create time
     * @param createTime The create time of the order to cancel
     */
    public void cancelOrder(OffsetDateTime createTime) {
        if (currentPortfolio == null) {
            return;
        }
        currentPortfolio.cancelOrder(createTime);
    }

    /**
     * Update the current model
     */
    public void update() {
        if (currentPortfolio == null) {
            return;
        }
        currentPortfolio.update();
        updatePortfolioList();
    }

    /**
     * Returns the list of assets for the current portfolio
     * @return the list of assets for the current portfolio
     */
    public ArrayList<Asset> getCurrentPortfolioAssets() {
        if (currentPortfolio == null) {
            return null;
        }
        return currentPortfolio.getAssets();
    }

    /**
     * Returns the list of completed transactions for the current portfolio
     * @return the list of completed transactions for the current portfolio
     */
    public ArrayList<Transaction> getCurrentPortfolioCompletedTransactions() {
        if (currentPortfolio == null) {
            return null;
        }
        return currentPortfolio.getCompletedTransactions();
    }

    /**
     * Returns the list of expired transactions for the current portfolio
     * @return the list of expired transactions for the current portfolio
     */
    public ArrayList<Transaction> getCurrentPortfolioExpiredTransactions() {
        if (currentPortfolio == null) {
            return null;
        }
        return currentPortfolio.getExpiredTransactions();
    }

    /**
     * Returns the list of standing orders for the current portfolio
     * @return the list of standing orders for the current portfolio
     */
    public ArrayList<Order> getCurrentPortfolioOrders() {
        if (currentPortfolio == null) {
            return null;
        }
        return currentPortfolio.getOrders();
    }

    /**
     * Returns the total value of the current portfolio, totalling all of it's assets
     * @return The total value of the current portfolio
     */
    public double getCurrentPortfolioTotalValue() {
        if (currentPortfolio == null) {
            return 0;
        }
        return currentPortfolio.getTotalValue();
    }

    /**
     * Returns the current portfolio's balance
     * @return The current portfolio's balance
     */
    public double getCurrentPortfolioBalance() {
        if (currentPortfolio == null) {
            return 0;
        }
        return currentPortfolio.getBalance();
    }

    /**
     * Increments the current portfolio's balance by the given amount
     * @param value The amount to increase the current portfolio's balance by
     */
    public void incrementCurrentPortfolioBalance(double value) {
        if (currentPortfolio == null) {
            return;
        }
        currentPortfolio.incrementBalance(value);
    }

    /**
     * Returns the name of the current portfolio
     * @return The name of the current portfolio
     */
    public String getCurrentPortfolioName() {
        if (currentPortfolio == null) {
            return "";
        }
        return currentPortfolio.getName();
    }

    /**
     * Returns the current portfolio's data as a string
     * @return The current portfolio's data as a string
     */
    public String getCurrentPortfolioString() {
        if (currentPortfolio == null) {
            return "";
        }
        return currentPortfolio.toString();
    }

    /**
     * Returns the list of potential portfolios from file data
     * @return The list of potential portfolios
     */
    public ArrayList<String> getPotentialPortfolios() {
        updatePortfolioList();
        return potentialPortfolios;
    }

    /**
     * Sets the current portfolio's name to the given name
     * @param name The new name for the current portfolio
     * @return Success of the operation
     */
    // This is sloppy I know
    public boolean setCurrentPortfolioName(String name) {
        if (currentPortfolio == null) {
            return false;
        }
        String oldName = currentPortfolio.getName();
        currentPortfolio.setName(name);
        // Have to do it this complicated way and not just currentPortfolio.toJSON(). Don't really know why
        currentPortfolio = new Portfolio(new JSON(currentPortfolio.toJSON().toString()));
        // Delete the file with the old name
        boolean val = FileManager.deleteFile(oldName);
        // This is just extra, should have been saved upon creation
        saveCurrentPortfolio();
        return val;
    }

    /**
     * Returns the current portfolio's data as a JSON object
     * @return JSON object containing the current portfolio's data
     */
    // For testing mostly
    public JSON getCurrentPortfolioJSON() {
        if (currentPortfolio == null) {
            return null;
        }
        return currentPortfolio.toJSON();
    }

    /**
     * Sets the current portfolio to a given portfolio object
     * @param p The portfolio object to be the current portfolio
     */
    // Testing mostly
    public void setCurrentPortfolioC(Portfolio p) {
        currentPortfolio = p;
    }

    /**
     * Sets the current portfolio object given a JSON object
     * @param json The JSON object of a portfolio to be set to the current portfolio
     */
    // For testing
    public void setCurrentPortfolioJSON(JSON json) {
        currentPortfolio = new Portfolio(json);
    }
}
