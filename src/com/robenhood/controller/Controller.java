package com.robenhood.controller;

import com.robenhood.model.Asset;
import com.robenhood.model.Crypto;
import com.robenhood.model.Model;
import com.robenhood.model.Transaction;
import com.robenhood.model.orders.Order;
import java.time.OffsetDateTime;
import java.util.ArrayList;

public class Controller {
    private Model aModel;
    //private gui aGui;
    public Controller(Model aModel /*aGui here when it's done*/) {
        this.aModel = aModel;
    }

    // For portfolio manipulation
    public void setCurrentPortfolio(String name) {
        aModel.setCurrentPortfolio(name);
    }

    public void createNewPortfolio(String name) {
        aModel.createNewPortfolio(name);
    }

    public void saveCurrentPortfolio() {
        aModel.saveCurrentPortfolio();
    }

    public void deletePortfolio(String name) {
        aModel.deletePortfolio(name);
    }

    public double getCurrentPortfolioTotalValue() {
        return aModel.getCurrentPortfolioTotalValue();
    }

    public double getCurrentPortfolioBalance() {
        return aModel.getCurrentPortfolioBalance();
    }

    public void incrementCurrentPortfolioBalance(double amount) {
        aModel.incrementCurrentPortfolioBalance(amount);
    }

    public String getCurrentPortfolioName() {
        return aModel.getCurrentPortfolioName();
    }

    public ArrayList<String> getPotentialPortfolios() {
        return new ArrayList<>(aModel.getPotentialPortfolios());
    }

    public void update() {
        aModel.update();
    }

    // For order manipulation
    public void addOrder(String type, Crypto crypto, boolean buy, double price, OffsetDateTime expireTime, double amount) {
        aModel.addOrder(type, crypto, buy, price, expireTime, amount);
    }

    public ArrayList<String> getCurrentPortfolioOrders() {
        ArrayList<String> data = new ArrayList<>();
        for (Order o : aModel.getCurrentPortfolioOrders()) {
            data.add(o.toString());
        }
        return data;
    }

    public void cancelOrder(OffsetDateTime createTime) {
        aModel.cancelOrder(createTime);
    }

    // For manipulating trades
    public ArrayList<String> getCurrentPortfolioCompletedTransactions() {
        ArrayList<String> data = new ArrayList<>();
        for (Transaction t : aModel.getCurrentPortfolioCompletedTransactions()) {
            data.add(t.toString());
        }
        return data;
    }

    public ArrayList<String> getCurrentPortfolioExpiredTransactions() {
        ArrayList<String> data = new ArrayList<>();
        for (Transaction t : aModel.getCurrentPortfolioExpiredTransactions()) {
            data.add(t.toString());
        }
        return data;
    }

    // For manipulating crypto
    public ArrayList<String> getCurrentPortfolioAssets() {
        ArrayList<String> data = new ArrayList<>();
        for (Asset a : aModel.getCurrentPortfolioAssets()) {
            data.add(a.toString());
        }
        return data;
    }
}
