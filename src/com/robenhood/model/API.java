package com.robenhood.model;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Scanner;


public class API {
    static URI uri1;
    static URI uri2;
    private static String api2key = "";
    static {
        try {
            uri1 = new URI("https://api.cryptowat.ch/markets/kraken/");
            uri2 = new URI("https://api.nomics.com/v1/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }




    public static void main(String[] args) {
        //getCryptoValue(OffsetDateTime.now(), "eth");
        getHistoryData(OffsetDateTime.now().minusDays(5), OffsetDateTime.now(), "ETH");

    }



    private static String getResponse(URI uri) {
        StringBuilder jsonResponse = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(uri.toURL().openStream(), StandardCharsets.UTF_8))) {
            for (String line; (line = reader.readLine()) != null;) {
                jsonResponse.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResponse.toString();
    }



    public static double getCryptoValue(OffsetDateTime time, String symbol) {
        URI tempUrl = uri1.resolve(symbol + "usd/price");
        String response = getResponse(tempUrl);
        System.out.println(response);
        return 0;
    }

    public static HashMap<OffsetDateTime, Double> getHistoryData(OffsetDateTime startTime, OffsetDateTime endTime, String symbol) {
        URI tempUrl = uri2.resolve("exchange-rates/history?key=" + api2key + "&currency=" + symbol + "&start=" + timeFix(startTime) + "Z&end=" + timeFix(endTime) + "Z");
        String response = getResponse(tempUrl);
        System.out.println(response);
        return null;
    }

    public static String timeFix(OffsetDateTime time)
    {
        String result = time.toString();
        result = result.replaceAll(":", "%3A");
        result = result.substring(0, result.indexOf('.'));
        return result;
    }
}
