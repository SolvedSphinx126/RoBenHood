package com.robenhood.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager {

    public static final String appDataPath = System.getenv("APPDATA") + "\\RoBenHood\\";
    public static final File appData = new File(appDataPath);

    public static boolean saveStringToFile(String json, String fileName) {
        String filePath = appDataPath + fileName + ".por"; // Append .por file extension to files
        System.out.println("Saving \"" + filePath + "\"");

        try {
            Files.writeString(Path.of(filePath), json);
        } catch (IOException e) {
            System.out.println("Trying to create \"" + appDataPath + "\".");
            if (appData.mkdirs())
                return saveStringToFile(json, fileName);
            return false;
        }

        return true;

    }

    public static String loadStringFromFile(String fileName) throws IOException {

        String filePath = appDataPath + fileName + ".por"; // Append .por file extension to files
        System.out.println("Loading \"" + filePath + "\"");

        return Files.readString(Path.of(filePath));

    }

    public static boolean deleteFile(String fileName) {
        String filePath = appDataPath + fileName + ".por"; // Append .por file extension to files
        System.out.println("Deleting \"" + filePath + "\"");

        try {
            Files.delete(Path.of(filePath));
            return true;
        } catch (IOException e) {
            System.out.println("Could not delete file with name " + fileName);
            return false;
        }
    }

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
