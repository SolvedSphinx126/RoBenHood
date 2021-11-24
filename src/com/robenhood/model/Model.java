package com.robenhood.model;

import com.robenhood.data.FileManager;
import com.robenhood.data.JSON;
import com.robenhood.model.orders.Order;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Model {
    private Portfolio currentPortfolio;
    private ArrayList<String> potentialPortfolios;

    public Model() {
        potentialPortfolios = new ArrayList<>();
        potentialPortfolios.addAll(Arrays.asList(FileManager.getPortfolioPaths()));
    }

    private void updatePortfolioList() {
        // I'm not sure if addAll will make duplicates so clear the list first
        potentialPortfolios.clear();
        potentialPortfolios.addAll(Arrays.asList(FileManager.getPortfolioPaths()));
    }

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

    public void createNewPortfolio(String name) {
        saveCurrentPortfolio();
        Portfolio p = new Portfolio(name);
        currentPortfolio = p;
        // Save the new portfolio to add the name to the list
        FileManager.saveStringToFile(p.toJSON().toString(), name);
    }

    public void saveCurrentPortfolio() {
        // If null nothing to save
        if (currentPortfolio != null) {
            FileManager.saveStringToFile(currentPortfolio.toJSON().toString(), currentPortfolio.getName());
        }
    }

    public boolean deletePortfolio(String name) {
        // Will not delete the current portfolio
        if (currentPortfolio != null && !currentPortfolio.getName().equals(name)) {
            return FileManager.deleteFile(name);
        }
        return false;
    }

    public void addOrder(String type, Crypto crypto, boolean buy, double price, OffsetDateTime expireTime, double amount) {
        if (currentPortfolio == null) {
            return;
        }
        currentPortfolio.addOrder(type, crypto, buy, price, expireTime, amount);
    }

    public void cancelOrder(OffsetDateTime createTime) {
        if (currentPortfolio == null) {
            return;
        }
        currentPortfolio.cancelOrder(createTime);
    }

    public void update() {
        if (currentPortfolio == null) {
            return;
        }
        currentPortfolio.update();
        updatePortfolioList();
    }

    public ArrayList<Asset> getCurrentPortfolioAssets() {
        if (currentPortfolio == null) {
            return null;
        }
        return currentPortfolio.getAssets();
    }

    public ArrayList<Transaction> getCurrentPortfolioCompletedTransactions() {
        if (currentPortfolio == null) {
            return null;
        }
        return currentPortfolio.getCompletedTransactions();
    }

    public ArrayList<Transaction> getCurrentPortfolioExpiredTransactions() {
        if (currentPortfolio == null) {
            return null;
        }
        return currentPortfolio.getExpiredTransactions();
    }

    public ArrayList<Order> getCurrentPortfolioOrders() {
        if (currentPortfolio == null) {
            return null;
        }
        return currentPortfolio.getOrders();
    }

    public double getCurrentPortfolioTotalValue() {
        if (currentPortfolio == null) {
            return 0;
        }
        return currentPortfolio.getTotalValue();
    }

    public double getCurrentPortfolioBalance() {
        if (currentPortfolio == null) {
            return 0;
        }
        return currentPortfolio.getBalance();
    }

    public void incrementCurrentPortfolioBalance(double value) {
        if (currentPortfolio == null) {
            return;
        }
        currentPortfolio.incrementBalance(value);
    }

    public String getCurrentPortfolioName() {
        if (currentPortfolio == null) {
            return "";
        }
        return currentPortfolio.getName();
    }

    public String getCurrentPortfolioString() {
        if (currentPortfolio == null) {
            return "";
        }
        return currentPortfolio.toString();
    }

    public ArrayList<String> getPotentialPortfolios() {
        updatePortfolioList();
        return potentialPortfolios;
    }

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

    // For testing mostly
    public JSON getCurrentPortfolioJSON() {
        if (currentPortfolio == null) {
            return null;
        }
        return currentPortfolio.toJSON();
    }

    // Testing mostly
    public void setCurrentPortfolioC(Portfolio p) {
        currentPortfolio = p;
    }

    // For testing
    public void setCurrentPortfolioJSON(JSON json) {
        currentPortfolio = new Portfolio(json);
    }
}
