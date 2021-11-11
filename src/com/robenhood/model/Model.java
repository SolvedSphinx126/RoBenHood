package com.robenhood.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Model {
    private Portfolio currentPortfolio;
    private ArrayList<String> potentialPortfolios;
    private FileManager fileManager;

    public Model() {
        potentialPortfolios = new ArrayList<>();
        fileManager = new FileManager();
    }

    public void addOrder(String type, Crypto crypto, boolean buy, double price, OffsetDateTime expireTime, double amount) {
        currentPortfolio.addOrder(type, crypto, buy, price, expireTime, amount);
    }

    public void update() {
        currentPortfolio.update();
    }

    public void setCurrentPortfolio(Portfolio p) {
        currentPortfolio = p;
        currentPortfolio.logIn();
    }

    public void createPortfolio(String name) {
        potentialPortfolios.add(name);
    }

    public void deletePortfolio(String name) {
        potentialPortfolios.remove(name);
    }

    /*public void saveCurrentPortfolio() {
        fileManager.savePortfolio(currentPortfolio);
    }

   public void switchCurrentPortfolio(String name) {
        currentPortfolio.logOut();
        setCurrentPortfolio(name);
   }

    private void setCurrentPortfolio(String name) {
        currentPortfolio = fileManager.loadPortfolio(name);
        currentPortfolio.logIn();
    }*/

    public double getCurrentPortfolioValue() {
        return currentPortfolio.getPortfolioValue();
    }

    public double getCurrentPortfolioBalance() {
        return currentPortfolio.getBalance();
    }

    public void incrementCurrentPortfolioBalance(double value) {
        currentPortfolio.incrementBalance(value);
    }
}
