package com.robenhood.model;
import com.robenhood.data.JSON;

import java.time.Instant;
import java.time.ZoneId;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.HashMap;


public class API {
    static URI uri1;
    private static String apiKey = "";
    static {
        try {
            uri1 = new URI("https://api.nomics.com/v1/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static String getResponse(URI uri) {
        try {
            Thread.sleep(1000);//time is in ms (1000 ms = 1 second)
        } catch (InterruptedException e) {e.printStackTrace();}
        StringBuilder jsonResponse = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(uri.toURL().openStream(), StandardCharsets.UTF_8))) {
            for (String line; (line = reader.readLine()) != null;) {
                jsonResponse.append(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return jsonResponse.toString();
    }

    public static double getCryptoValue(OffsetDateTime time, String symbol) {
        URI tempUrl = uri1.resolve("currencies/ticker?key=" + apiKey + "&ids=" + symbol.toUpperCase());
        String response = getResponse(tempUrl);
        JSON json = new JSON(response);
        JSON result = new JSON(new JSON(json.get("0").toString()).get("0").toString());
        return (double) result.get("price");

    }

    public static HashMap<OffsetDateTime, Double> getHistoryData(OffsetDateTime startTime, OffsetDateTime endTime, String symbol) {
        URI tempUrl = uri1.resolve("exchange-rates/history?key=" + apiKey + "&currency=" + symbol + "&start=" + timeFix(startTime) + "Z&end=" + timeFix(endTime) + "Z");
        String response = getResponse(tempUrl);
        JSON json = new JSON(response);
        if(json.isEmpty())
        {
            return new HashMap<>() {{
                put(OffsetDateTime.now(), getCryptoValue(OffsetDateTime.now(), symbol));
            }};
        }
        JSON fixedJSON = new JSON(json.get("0").toString());

        OffsetDateTime timestamp;
        double rate;
        HashMap<OffsetDateTime, Double> results = new HashMap<>();

        int i = 0;
        JSON element;

        while(fixedJSON.containsKey(Integer.toString(i)))
        {
            element = new JSON(fixedJSON.get(Integer.toString(i)).toString());
            timestamp = OffsetDateTime.ofInstant(Instant.parse(element.get("timestamp").toString()), ZoneId.systemDefault());
            rate = (double) element.get("rate");
            results.put(timestamp, rate);
            i++;

        }
        return results;
    }

    public static String timeFix(OffsetDateTime time)
    {
        String result = time.toString();
        result = result.replaceAll(":", "%3A");
        result = result.substring(0, result.indexOf('.'));
        return result;
    }


}
