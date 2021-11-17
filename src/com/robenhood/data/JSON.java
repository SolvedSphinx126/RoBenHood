/**
 *  @author Ben Morrison
 *  Class to interpret JSON and mimic it.
 *
 *  */

package com.robenhood.data;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSON extends HashMap<String, Object> {

    public JSON() {}

    public JSON(String json) {
        JSON jsonObj = readJSON(trimString(json));
        for (String key: jsonObj.keySet()) {
            this.put(key, jsonObj.get(key));
        }
    }

    public String toString() {
        StringBuilder ret = new StringBuilder("{\n");

        int count = 0;  // This count is purely to check if it's the last, and avoiding placing a comma at the end
        for (String key: this.keySet()) {

            Object obj = this.get(key);
            String value;

            if (obj instanceof String) {
                value = "\"" + obj + "\"";
            } else {
                value = obj.toString();
            }
            // This looks like 10x worse as a StringBuilder thing, but the IntelliJ highlighting annoys me so much
            ret.append("    \"").append(key).append("\": ").append(value.replace("\n", "\n    "));

            if (count != this.size() - 1)  // Avoid placing comma on last value
                ret.append(",");

            ret.append("\n");
            count++;
        }

        return ret + "}";

    }

    private static JSON readJSON(String json) {
        json = json.trim();

        JSON ret;  // This is the JSON object that will be filled and returned.
        if (json.startsWith("{") && json.endsWith("}")) {
            ret = readJSON(json.substring(1, json.length() - 1));
            return ret;
        }

        // Detection and replacement of commas in strings.
        Matcher matcher = Pattern.compile("\".*?\"").matcher(json);  // This detects all strings
        while (matcher.find()) {
            String s = matcher.group();

            if (s.indexOf(",") > 0) {
                json = json.replace(s, s.replace(",", ""));  // Replaces the commas within strings.
            }
//            else if (s.indexOf(":") > 0) {  // Uncomment this if colons within Strings break stuff
//                json = json.replace(s, s.replace(":", ""));
//            }
        }

        ret = new JSON();

        int lastComma = -1;
        ArrayList<String> elements = new ArrayList<>();

        int i = 0, depth = 0;  // i is the current index, depth tells you how many dicts/lists the parser is deep.

        while (i < json.length()) {  // Loops through every character

            switch (json.charAt(i)) {

                case '{':
                case '[':
                    depth++;
                    break;

                case '}':
                case ']':
                    depth--;
                    break;

                case ',':
                    if (depth == 0) {  // This runs for each key/value pair
                        elements.add(json.substring(lastComma + 1, i));  // Adds to the elements ArrayList for parsing
                        lastComma = i;
                    }
                    break;
            }

            i++;
        }

        elements.add(json.substring(lastComma + 1));  // Adds the last key/value pair to the list of elements

        int count = 0;
        for (String s: elements) {

            if (s.contains("{")) {  // Recusively handles dicts

                String key = s.substring(0, s.indexOf(":"));

                if (key.startsWith("\"") && key.endsWith("\""))
                    key = key.substring(1, key.length() - 1);

                JSON value = readJSON(s.substring(s.indexOf("{") + 1, s.lastIndexOf("}")));

                ret.put(key, value);

            } else if (s.contains("[")) {  // Recursively handle lists

                // Pull out the key string
                String key = s.substring(0, s.indexOf(":"));
                if (key.startsWith("\"") && key.endsWith("\""))
                    key = key.substring(1, key.length() - 1);

                JSON value = readJSON(s.substring(s.indexOf("[") + 1, s.lastIndexOf("]")));

                // This converts JSON lists to an ArrayList
                ArrayList<Object> list = new ArrayList<>();
                for (String subKey: value.keySet()) {
                    list.add(value.get(subKey));
                }

                ret.put(key, list);

            } else if (s.contains(":")) {  // Handles primitives/strings

                String key = s.substring(0, s.indexOf(":"));

                if (key.startsWith("\"") && key.endsWith("\""))
                    key = key.substring(1, key.length() - 1);

                String value = s.substring(s.indexOf(":") + 1).trim();

                if (value.startsWith("\"") && value.endsWith("\""))
                    value = value.substring(1, value.length() - 1);

                ret.put(key, convert(value));

            } else {

                if (s.startsWith("\"") && s.endsWith("\""))
                    s = s.substring(1, s.length() - 2);

                ret.put("" + count, convert(s));

                count++;

            }
        }

        return ret;
    }

    private static String trimString(String json) {
        StringBuilder ret = new StringBuilder();
        for (String s: json.split("\n"))
            ret.append(s.trim());
        return ret.toString();
    }

    private static boolean isBigInteger(String string) {
        try {
            new BigInteger(string, 10);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isDouble(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static Object convert(String s) {
        // This converts the string to the correct data type
        if (isInteger(s)) {
            return Integer.parseInt(s);
        } else if (isDouble(s) && !isBigInteger(s)) {
            return Double.parseDouble(s);
        } else {
            return s;
        }
    }

}
