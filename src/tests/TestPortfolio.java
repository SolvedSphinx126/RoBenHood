package tests;

import com.robenhood.model.Crypto;
import com.robenhood.model.Model;
import com.robenhood.model.Portfolio;
import java.time.OffsetDateTime;

public class TestPortfolio {
    static Model model = new Model();
    static Portfolio portfolio = new Portfolio("First P");
    public static void main(String[] args) throws InterruptedException {
        model.setCurrentPortfolio(portfolio);
        testCase1();
        testCase2();
        testCase3();
        testCase4();
        // So far all test cases have been tested with extreme and random values
        // and are all working as intended.
    }

    public static void testCase4() throws InterruptedException {
        // Attempts to make a limit sell. Change price and amount to check methods.
        System.out.println("\nTEST CASE 4\n");
        System.out.println("Current balance: " + model.getCurrentPortfolioBalance());
        System.out.println("Current value: " + model.getCurrentPortfolioValue());

        Thread.sleep(2000);

        model.addOrder("Limit Trade", new Crypto("Doge Coin", "DOGE"), false, 3, OffsetDateTime.now().plusDays(1), 3);
        System.out.println("Now updating model");
        model.update();
        System.out.println("Current balance: " + model.getCurrentPortfolioBalance());
        System.out.println("Current value: " + model.getCurrentPortfolioValue());
    }

    public static void testCase3() {
        // Attempts to sell a certain amount of stock. Use this to check if able to sell more than have.
        System.out.println("\nTEST CASE 3\n");
        System.out.println("Current balance: " + model.getCurrentPortfolioBalance());
        System.out.println("Current value: " + model.getCurrentPortfolioValue());

        model.addOrder("Market Trade", new Crypto("Doge Coin", "DOGE"), false, 0, null, 3);
        System.out.println("Now updating model");
        model.update();
        System.out.println("Current balance: " + model.getCurrentPortfolioBalance());
        System.out.println("Current value: " + model.getCurrentPortfolioValue());
    }

    public static void testCase2() {
        // Sets balance to 1 and then tries to do a simple market buy. Change the variable to check that
        // it won't work when the buy price is higher than the balance.
        System.out.println("\nTEST CASE 2\n");
        // Zero balance then add amount
        model.incrementCurrentPortfolioBalance(-model.getCurrentPortfolioBalance() + 1);
        model.addOrder("Market Trade", new Crypto("Doge Coin", "DOGE"), true, 0, null, 3);

        System.out.println("Current balance: " + model.getCurrentPortfolioBalance());
        System.out.println("Current value: " + model.getCurrentPortfolioValue());
        System.out.println("Now updating model");
        model.update();
        System.out.println("Current balance: " + model.getCurrentPortfolioBalance());
        System.out.println("Current value: " + model.getCurrentPortfolioValue());

    }

    public static void testCase1() throws InterruptedException {
        // Sets balance to 1,000 then buys stock with a market trade, then buys with a limit trade,
        // attempting it twice to check if it won't work for one history data while still working for another.
        System.out.println("\nTEST CASE 1\n");
        model.incrementCurrentPortfolioBalance(1000);
        model.addOrder("Market Trade", new Crypto("Doge Coin", "DOGE"), true, 0, null, 10);

        System.out.println("Current balance: " + model.getCurrentPortfolioBalance());
        System.out.println("Current value: " + model.getCurrentPortfolioValue());
        System.out.println("Now updating model");
        model.update();
        System.out.println("Current balance: " + model.getCurrentPortfolioBalance());
        System.out.println("Current value: " + model.getCurrentPortfolioValue());

        System.out.println("Added limit trade");
        model.addOrder("Limit Trade", new Crypto("Doge Coin", "DOGE"), true, 2, OffsetDateTime.now().plusDays(1), 10);
        System.out.println("Current balance: " + model.getCurrentPortfolioBalance());
        System.out.println("Current value: " + model.getCurrentPortfolioValue());
        System.out.println("Now updating model");
        model.update();
        System.out.println("Current balance: " + model.getCurrentPortfolioBalance());
        System.out.println("Current value: " + model.getCurrentPortfolioValue());

        Thread.sleep(2000);

        System.out.println("Current balance: " + model.getCurrentPortfolioBalance());
        System.out.println("Current value: " + model.getCurrentPortfolioValue());
        System.out.println("Now updating model");
        model.update();
        System.out.println("Current balance: " + model.getCurrentPortfolioBalance());
        System.out.println("Current value: " + model.getCurrentPortfolioValue());
    }
}
