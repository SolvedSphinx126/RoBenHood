/**
 *  @author Ben Morrison
 *  Class to interpret JSON and mimic it.
 *
 *  */

package com.robenhood.data;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
            StringBuilder value = new StringBuilder();

            if (obj instanceof String) {
                value.append("\"").append(obj).append("\"");
            } else if (obj instanceof List) {  // Handling ArrayLists and stuff in a custom manner

                value.append("[\n");
                List<Object> list = (List) obj;

                for (int i = 0; i < list.size(); i++) {
                    Object subObj = list.get(i);
                    value.append("    ");

                    if (subObj instanceof JSONObject) {
                        value.append(((JSONObject) subObj).toJSON().toString());
                    } else if (subObj instanceof String) {
                        value.append("\"").append(obj).append("\"");
                    } else {
                        value.append(subObj.toString());
                    }

                    if (i < list.size() - 1)  // Avoid putting a comma after the last element
                        value.append(",");
                    value.append("\n");
                }

                value.append("]");

            } else if (obj instanceof JSONObject) {
                value.append(((JSONObject) obj).toJSON().toString());
            } else {
                value = new StringBuilder(obj.toString());
            }
            // This looks like 10x worse as a StringBuilder thing, but the IntelliJ highlighting annoys me so much
            ret.append("    \"").append(key).append("\": ").append(value.toString().replace("\n", "\n    "));

            if (count < this.size() - 1)  // Avoid placing comma on last value
                ret.append(",");

            ret.append("\n");
            count++;
        }

        return ret + "}";

    }

    private static JSON readJSON(String json) {
        json = json.trim();

        JSON ret;  // This is the JSON object that will be filled and returned.
//        if (json.startsWith("{") && json.endsWith("}")) {
//            ret = readJSON(json.substring(1, json.length() - 1));
//            return ret;
//        }

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
            System.out.println("poop:" + s);
            int firstBracket = s.indexOf("{");

            if (s.contains(":") && (firstBracket == -1 || s.indexOf(":") < firstBracket)) {
                String key = s.substring(0, s.indexOf(":")).trim(), value = s.substring(s.indexOf(":") + 1).trim();

                System.out.println("pee: " + key + " -=- " + value);

                if (key.startsWith("\"") && key.endsWith("\""))
                        key = key.substring(1, key.length() - 1);

                if (value.startsWith("{")) {  // Recusively handles dicts

                    ret.put(key, readJSON(value));

                } else if (value.startsWith("[")) {  // Recursively handle lists

                    System.out.println("????");
                    JSON listJSON = readJSON(value.substring(1, value.length() - 1));  // normally 1, -1
                    // This converts JSON lists to an ArrayList
                    ArrayList<Object> list = new ArrayList<>();
                    for (String subKey: listJSON.keySet()) {
                        list.add(listJSON.get(subKey));
                    }

                    ret.put(key, list);

                } else {  // Handles primitives/strings

                    if (value.startsWith("\"") && value.endsWith("\""))
                        value = value.substring(1, value.length() - 1);

                    ret.put(key, convert(value));

                }
            } else {

                if (s.startsWith("\"") && s.endsWith("\""))
                    s = s.substring(1, s.length() - 1);

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

        json = ret.toString();

        if (json.startsWith("{") && json.endsWith("}"))
            json = json.substring(1, json.length() - 1);

        return json;
    }

    private static boolean isBoolean(String s) {
        return ("true".equals(s) || "false".equals(s));
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
        if (isBoolean(s)) {
            return Boolean.getBoolean(s);
        } else if (isInteger(s)) {
            return Integer.parseInt(s);
        } else if (isDouble(s) && !isBigInteger(s)) {
            return Double.parseDouble(s);
        } else {
            return s;
        }
    }

}
