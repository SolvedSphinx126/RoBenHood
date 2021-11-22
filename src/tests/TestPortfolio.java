package tests;

import com.robenhood.data.JSON;
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
        System.out.println(model.getCurrentPortfolioString());
        testCaseJSON();

        // So far all test cases have been tested with extreme and random values
        // and are all working as intended.
    }

    public static void testCaseJSON() {
        JSON json = new JSON("{\n" +
                "    \"totalValue\": 4.437,\n" +
                "    \"expiredTransactions\": [\n" +
                "    ],\n" +
                "    \"assets\": [\n" +
                "        {\n" +
                "            \"amount\": 17.0,\n" +
                "            \"totalCost\": 3.6099999999999994,\n" +
                "            \"coin\": {\n" +
                "                \"symbol\": \"DOGE\",\n" +
                "                \"name\": \"Doge Coin\"\n" +
                "            }\n" +
                "        }\n" +
                "    ],\n" +
                "    \"balance\": 10.0,\n" +
                "    \"timeSinceLastUpdate\": \"2021-11-17T23:27:10.501377400-06:00\",\n" +
                "    \"completedTransactions\": [\n" +
                "        {\n" +
                "            \"amount\": 10.0,\n" +
                "            \"expired\": false,\n" +
                "            \"price\": 0.261,\n" +
                "            \"buy\": true,\n" +
                "            \"time\": \"2021-11-17T23:27:06.447153400-06:00\",\n" +
                "            \"type\": \"Market Trade\",\n" +
                "            \"crypto\": {\n" +
                "                \"symbol\": \"DOGE\",\n" +
                "                \"name\": \"Doge Coin\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"amount\": 10.0,\n" +
                "            \"expired\": false,\n" +
                "            \"price\": 1.0,\n" +
                "            \"buy\": true,\n" +
                "            \"time\": \"2021-11-17T23:27:06.474801200-06:00\",\n" +
                "            \"type\": \"Limit Trade\",\n" +
                "            \"crypto\": {\n" +
                "                \"symbol\": \"DOGE\",\n" +
                "                \"name\": \"Doge Coin\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"amount\": 3.0,\n" +
                "            \"expired\": false,\n" +
                "            \"price\": 0.261,\n" +
                "            \"buy\": true,\n" +
                "            \"time\": \"2021-11-17T23:27:08.485580900-06:00\",\n" +
                "            \"type\": \"Market Trade\",\n" +
                "            \"crypto\": {\n" +
                "                \"symbol\": \"DOGE\",\n" +
                "                \"name\": \"Doge Coin\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"amount\": 3.0,\n" +
                "            \"expired\": false,\n" +
                "            \"price\": 0.261,\n" +
                "            \"buy\": false,\n" +
                "            \"time\": \"2021-11-17T23:27:08.485580900-06:00\",\n" +
                "            \"type\": \"Market Trade\",\n" +
                "            \"crypto\": {\n" +
                "                \"symbol\": \"DOGE\",\n" +
                "                \"name\": \"Doge Coin\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"amount\": 3.0,\n" +
                "            \"expired\": false,\n" +
                "            \"price\": 3.0,\n" +
                "            \"buy\": false,\n" +
                "            \"time\": \"2021-11-17T23:27:10.486581900-06:00\",\n" +
                "            \"type\": \"Limit Trade\",\n" +
                "            \"crypto\": {\n" +
                "                \"symbol\": \"DOGE\",\n" +
                "                \"name\": \"Doge Coin\"\n" +
                "            }\n" +
                "        }\n" +
                "    ],\n" +
                "    \"name\": \"First P\",\n" +
                "    \"orderManager\": {\n" +
                "        \"orders\": [\n" +
                "        ],\n" +
                "        \"transactions\": [\n" +
                "        ]\n" +
                "    }\n" +
                "}");
        System.out.println("Assets:\n" + json.get("assets").toString());
        System.out.println("\nTEST CASE JSON\n");
        System.out.println("Printing the current portfolio as json");
        System.out.println("\n\n\n\n" + model.getCurrentPortfolioJSON().toString());
        System.out.println("Attempting to construct portfolio data from file");
        model.setCurrentPortfolioJSON(json);
        System.out.println(model.getCurrentPortfolioString());
        System.out.println(model.getCurrentPortfolioJSON().toString());
        System.out.println("{\n" +
                "    \"totalValue\": 4.437,\n" +
                "    \"expiredTransactions\": [\n" +
                "    ],\n" +
                "    \"assets\": [\n" +
                "        {\n" +
                "            \"amount\": 17.0,\n" +
                "            \"totalCost\": 3.6099999999999994,\n" +
                "            \"coin\": {\n" +
                "                \"symbol\": \"DOGE\",\n" +
                "                \"name\": \"Doge Coin\"\n" +
                "            }\n" +
                "        }\n" +
                "    ],\n" +
                "    \"balance\": 10.0,\n" +
                "    \"timeSinceLastUpdate\": \"2021-11-17T23:27:10.501377400-06:00\",\n" +
                "    \"completedTransactions\": [\n" +
                "        {\n" +
                "            \"amount\": 10.0,\n" +
                "            \"expired\": false,\n" +
                "            \"price\": 0.261,\n" +
                "            \"buy\": true,\n" +
                "            \"time\": \"2021-11-17T23:27:06.447153400-06:00\",\n" +
                "            \"type\": \"Market Trade\",\n" +
                "            \"crypto\": {\n" +
                "                \"symbol\": \"DOGE\",\n" +
                "                \"name\": \"Doge Coin\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"amount\": 10.0,\n" +
                "            \"expired\": false,\n" +
                "            \"price\": 1.0,\n" +
                "            \"buy\": true,\n" +
                "            \"time\": \"2021-11-17T23:27:06.474801200-06:00\",\n" +
                "            \"type\": \"Limit Trade\",\n" +
                "            \"crypto\": {\n" +
                "                \"symbol\": \"DOGE\",\n" +
                "                \"name\": \"Doge Coin\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"amount\": 3.0,\n" +
                "            \"expired\": false,\n" +
                "            \"price\": 0.261,\n" +
                "            \"buy\": true,\n" +
                "            \"time\": \"2021-11-17T23:27:08.485580900-06:00\",\n" +
                "            \"type\": \"Market Trade\",\n" +
                "            \"crypto\": {\n" +
                "                \"symbol\": \"DOGE\",\n" +
                "                \"name\": \"Doge Coin\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"amount\": 3.0,\n" +
                "            \"expired\": false,\n" +
                "            \"price\": 0.261,\n" +
                "            \"buy\": false,\n" +
                "            \"time\": \"2021-11-17T23:27:08.485580900-06:00\",\n" +
                "            \"type\": \"Market Trade\",\n" +
                "            \"crypto\": {\n" +
                "                \"symbol\": \"DOGE\",\n" +
                "                \"name\": \"Doge Coin\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"amount\": 3.0,\n" +
                "            \"expired\": false,\n" +
                "            \"price\": 3.0,\n" +
                "            \"buy\": false,\n" +
                "            \"time\": \"2021-11-17T23:27:10.486581900-06:00\",\n" +
                "            \"type\": \"Limit Trade\",\n" +
                "            \"crypto\": {\n" +
                "                \"symbol\": \"DOGE\",\n" +
                "                \"name\": \"Doge Coin\"\n" +
                "            }\n" +
                "        }\n" +
                "    ],\n" +
                "    \"name\": \"First P\",\n" +
                "    \"orderManager\": {\n" +
                "        \"orders\": [\n" +
                "        ],\n" +
                "        \"transactions\": [\n" +
                "        ]\n" +
                "    }\n" +
                "}");
    }

    public static void testCase4() throws InterruptedException {
        // Attempts to make a limit sell. Change price and amount to check methods.
        System.out.println("\nTEST CASE 4\n");
        System.out.println("Current balance: " + model.getCurrentPortfolioBalance());
        System.out.println("Current value: " + model.getCurrentPortfolioTotalValue());

        Thread.sleep(2000);

        model.addOrder("Limit Trade", new Crypto("Doge Coin", "DOGE"), false, 3, OffsetDateTime.now().plusDays(1), 3);
        System.out.println("Now updating model");
        model.update();
        System.out.println("Current balance: " + model.getCurrentPortfolioBalance());
        System.out.println("Current value: " + model.getCurrentPortfolioTotalValue());
    }

    public static void testCase3() {
        // Attempts to sell a certain amount of stock. Use this to check if able to sell more than have.
        System.out.println("\nTEST CASE 3\n");
        System.out.println("Current balance: " + model.getCurrentPortfolioBalance());
        System.out.println("Current value: " + model.getCurrentPortfolioTotalValue());

        model.addOrder("Market Trade", new Crypto("Doge Coin", "DOGE"), false, 0, null, 3);
        System.out.println("Now updating model");
        model.update();
        System.out.println("Current balance: " + model.getCurrentPortfolioBalance());
        System.out.println("Current value: " + model.getCurrentPortfolioTotalValue());
    }

    public static void testCase2() {
        // Sets balance to 1 and then tries to do a simple market buy. Change the variable to check that
        // it won't work when the buy price is higher than the balance.
        System.out.println("\nTEST CASE 2\n");
        // Zero balance then add amount
        model.incrementCurrentPortfolioBalance(-model.getCurrentPortfolioBalance() + 1);
        model.addOrder("Market Trade", new Crypto("Doge Coin", "DOGE"), true, 0, null, 3);

        System.out.println("Current balance: " + model.getCurrentPortfolioBalance());
        System.out.println("Current value: " + model.getCurrentPortfolioTotalValue());
        System.out.println("Now updating model");
        model.update();
        System.out.println("Current balance: " + model.getCurrentPortfolioBalance());
        System.out.println("Current value: " + model.getCurrentPortfolioTotalValue());

    }

    public static void testCase1() throws InterruptedException {
        // Sets balance to 1,000 then buys stock with a market trade, then buys with a limit trade,
        // attempting it twice to check if it won't work for one history data while still working for another.
        System.out.println("\nTEST CASE 1\n");
        model.incrementCurrentPortfolioBalance(1000);
        model.addOrder("Market Trade", new Crypto("Doge Coin", "DOGE"), true, 0, null, 10);

        System.out.println("Current balance: " + model.getCurrentPortfolioBalance());
        System.out.println("Current value: " + model.getCurrentPortfolioTotalValue());
        System.out.println("Now updating model");
        model.update();
        System.out.println("Current balance: " + model.getCurrentPortfolioBalance());
        System.out.println("Current value: " + model.getCurrentPortfolioTotalValue());

        System.out.println("Added limit trade");
        model.addOrder("Limit Trade", new Crypto("Doge Coin", "DOGE"), true, 2, OffsetDateTime.now().plusDays(1), 10);
        System.out.println("Current balance: " + model.getCurrentPortfolioBalance());
        System.out.println("Current value: " + model.getCurrentPortfolioTotalValue());
        System.out.println("Now updating model");
        model.update();
        System.out.println("Current balance: " + model.getCurrentPortfolioBalance());
        System.out.println("Current value: " + model.getCurrentPortfolioTotalValue());

        Thread.sleep(2000);

        System.out.println("Current balance: " + model.getCurrentPortfolioBalance());
        System.out.println("Current value: " + model.getCurrentPortfolioTotalValue());
        System.out.println("Now updating model");
        model.update();
        System.out.println("Current balance: " + model.getCurrentPortfolioBalance());
        System.out.println("Current value: " + model.getCurrentPortfolioTotalValue());
    }
}
