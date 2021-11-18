package tests;

import com.robenhood.data.FileManager;
import com.robenhood.data.JSON;

import java.io.IOException;

public class TestFileManager {

    public static void main(String[] args) {

        for (String path: FileManager.getPortfolioPaths()) {

            try {
                JSON json = new JSON(FileManager.loadStringFromFile(path));
                System.out.println(json.toString());
            } catch (IOException e) {
                System.out.println("Couldn't find \"" + path + "\"");
            }
        }
    }

}
