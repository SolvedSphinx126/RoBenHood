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
        updatePortfolioList();
    }

    public void saveCurrentPortfolio() {
        // If null nothing to save
        if (currentPortfolio != null) {
            FileManager.saveStringToFile(currentPortfolio.toJSON().toString(), currentPortfolio.getName());
        }
    }

    public boolean deletePortfolio(String name) {
        // Will not delete the current portfolio
        if (!currentPortfolio.getName().equals(name)) {
            boolean val = FileManager.deleteFile(name);
            updatePortfolioList();
            return val;
        }
        return false;
    }

    public void addOrder(String type, Crypto crypto, boolean buy, double price, OffsetDateTime expireTime, double amount) {
        currentPortfolio.addOrder(type, crypto, buy, price, expireTime, amount);
    }

    public void cancelOrder(OffsetDateTime createTime) {
        currentPortfolio.cancelOrder(createTime);
    }

    public void update() {
        currentPortfolio.update();
    }

    public ArrayList<Asset> getCurrentPortfolioAssets() {
        return currentPortfolio.getAssets();
    }

    public ArrayList<Transaction> getCurrentPortfolioCompletedTransactions() {
        return currentPortfolio.getCompletedTransactions();
    }

    public ArrayList<Transaction> getCurrentPortfolioExpiredTransactions() {
        return currentPortfolio.getExpiredTransactions();
    }

    public ArrayList<Order> getCurrentPortfolioOrders() {
        return currentPortfolio.getOrders();
    }

    public double getCurrentPortfolioTotalValue() {
        return currentPortfolio.getTotalValue();
    }

    public double getCurrentPortfolioBalance() {
        return currentPortfolio.getBalance();
    }

    public void incrementCurrentPortfolioBalance(double value) {
        currentPortfolio.incrementBalance(value);
    }

    public String getCurrentPortfolioName() {
        return currentPortfolio.getName();
    }

    public String getCurrentPortfolioString() {
        return currentPortfolio.toString();
    }

    public ArrayList<String> getPotentialPortfolios() {
        return potentialPortfolios;
    }

    public void setCurrentPortfolioName(String name) {
        // TODO make filemanager delete the old one or add a method for changing the name
        currentPortfolio.setName(name);
    }

    // For testing only
    public JSON getCurrentPortfolioJSON() {
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
