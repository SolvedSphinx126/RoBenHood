package tests;

import com.robenhood.model.Model;

public class TestModel {
    static Model model = new Model();
    public static void main(String[] args) {
        model = new Model();

        model.createNewPortfolio("FirstP");
        // Print the newly created portfolio's json
        System.out.println("The data for FirstP, newly created:");
        System.out.println(model.getCurrentPortfolioJSON().toString());

        model.createNewPortfolio("SecondP");
        // Print the second created portfolio's json
        System.out.println("The data for SecondP, created after FirstP:");
        System.out.println(model.getCurrentPortfolioJSON().toString());

        // Try to switch back to the first p
        if (model.setCurrentPortfolio("FirstP")) {
            System.out.println("FOUND FirstP");
        }
        System.out.println("The data for FirstP after being loaded from the file:");
        System.out.println(model.getCurrentPortfolioJSON().toString());

        System.out.println("Potential portfolios: ");
        for (String str : model.getPotentialPortfolios()) {
            System.out.print("    " + str + "\n");
        }
    }
}