package com.robenhood.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * The class that manages all the files that contain the info.
 *
 * @author Ben Morrison & Jeremiah Rhoton
 */
public class FileManager {

    public static final String appDataPath = System.getenv("APPDATA") + "\\RoBenHood\\";
    public static final File appData = new File(appDataPath);

    /**
     * This method saves Strings to file.
     * @param string the String being saved to a file.
     * @param portfolioName the name of the file to be saved to.
     * @return boolean that tells if the save was successful
     */
    public static boolean saveStringToFile(String string, String portfolioName) {
        String filePath = appDataPath + portfolioName + ".por"; // Append .por file extension to files
        System.out.println("Saving \"" + filePath + "\"");

        try {
            Files.writeString(Path.of(filePath), string);
        } catch (IOException e) {
            System.out.println("Trying to create \"" + appDataPath + "\".");
            if (appData.mkdirs())
                return saveStringToFile(string, portfolioName);
            return false;
        }

        return true;

    }

    /**
     * This method loads Strings from files by name.
     * @param portfolioName The name of the portfolio to load.
     * @return String that was contained in the portfolio file
     * @throws IOException If the file is not found or can't be read.
     */
    public static String loadStringFromFile(String portfolioName) throws IOException {

        String filePath = appDataPath + portfolioName + ".por"; // Append .por file extension to files
        System.out.println("Loading \"" + filePath + "\"");

        return Files.readString(Path.of(filePath));

    }

    /**
     * This method deletes a portfolio file by name.
     * @param portfolioName The name of the portfolio to delete.
     * @return boolean if the deletion was successful.
     */
    public static boolean deleteFile(String portfolioName) {
        String filePath = appDataPath + portfolioName + ".por"; // Append .por file extension to files
        System.out.println("Deleting \"" + filePath + "\"");

        try {
            Files.delete(Path.of(filePath));
            return true;
        } catch (IOException e) {
            System.out.println("Could not delete file with name " + portfolioName);
            return false;
        }
    }

    /**
     * This method will return an array of all the available Portfolios.
     * @return String[] of Portfolio Names
     */
    public static String[] getPortfolioPaths() {
        File[] files = appData.listFiles();

        if (files == null)
            return new String[]{};

        String[] paths = new String[files.length];

        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.isFile() && file.toString().endsWith(".por"))
                paths[i] = file.toString().replace(appDataPath, "").replace(".por", "");

        }

        return paths;

    }

}
