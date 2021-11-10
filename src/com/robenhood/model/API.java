package com.robenhood.model;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;


public class API {
    static URI uri;
    public static void main(String[] args) throws URISyntaxException {
        uri = new URI("https://api.cryptowat.ch/markets/kraken/");

        getCryptoValue(OffsetDateTime.now(), "eth");

    }

    static String fetchAPI(URI uri)
    {
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
        URI tempUrl = uri.resolve(symbol + "usd/price");
        String response = fetchAPI(tempUrl);
        System.out.println(response);
        return 0;
    }
}
