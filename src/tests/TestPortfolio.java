package tests;

import com.robenhood.model.Crypto;
import com.robenhood.model.Model;
import com.robenhood.model.Portfolio;

public class TestPortfolio {
    public static void main(String[] args) {
        Model model = new Model();
        Portfolio portfolio = new Portfolio("First P");
        model.setCurrentPortfolio(portfolio);
        model.incrementCurrentPortfolioBalance(1000);
        model.addOrder("Market Trade", new Crypto("Doge Coin", "DOGE"), true, 0, null, 10);

        System.out.println("Current balance: " + model.getCurrentPortfolioBalance());
        System.out.println("Current value: " + model.getCurrentPortfolioValue());
        System.out.println("Now updating model");
        model.update();
        System.out.println("Current balance: " + model.getCurrentPortfolioBalance());
        System.out.println("Current value: " + model.getCurrentPortfolioValue());

    }
}
