package com.robenhood.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;

public class Model {
    private Portfolio currentPortfolio;
    private ArrayList<String> potentialPortfolios;
    private OrderManager orderManager;
    private FileManager fileManager;

    public Model() {
        potentialPortfolios = new ArrayList<>();
        orderManager = new OrderManager();
        fileManager = new FileManager();
    }

    public void addOrder(String type, Crypto crypto, boolean buy, double price, OffsetDateTime expireTime, double amount) {
        orderManager.addOrder(type, crypto, buy, price, expireTime, amount);
    }

    public void update() {
        currentPortfolio.update();
    }

    public void createPortfolio(String name) {
        potentialPortfolios.add(name);
    }

    public void deletePortfolio(String name) {
        potentialPortfolios.remove(name);
    }

    public void saveCurrentPortfolio() {
        fileManager.savePortfolio(currentPortfolio);
    }

    public void setCurrentPortfolio(String name) {
        currentPortfolio = fileManager.loadPortfolio(name);
    }
}
