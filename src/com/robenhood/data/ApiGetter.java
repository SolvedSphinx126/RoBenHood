package com.robenhood.data;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.net.URLConnection;


public class ApiGetter {
    public static void main(String[] args) throws MalformedURLException, FileNotFoundException {
        //File cryptowatchApiKey = new File("../../../Resources/cryptowatchApi.key");
        //Scanner scan = new Scanner(cryptowatchApiKey);
        URL url = new URL("https://api.cryptowat.ch/markets/kraken/btceur/price?apikey="/* + scan.nextLine()*/);
        String jsonResponse = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
            for (String line; (line = reader.readLine()) != null;) {
                jsonResponse += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print(jsonResponse);

    }
}
