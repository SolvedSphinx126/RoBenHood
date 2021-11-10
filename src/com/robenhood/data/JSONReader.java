package com.robenhood.data;

import java.util.ArrayList;

public class JSONReader {

    public static JSON readJSON(String json, int indent) {
        json = json.trim();
        String indentString = "";
        for (int lcv = 0; lcv < indent; lcv++) {
            indentString += " ";
        }

        JSON ret;
        if (json.startsWith("{") && json.endsWith("}")) {
            System.out.println(indentString + "{");
            ret = readJSON(json.substring(1, json.length() - 1), indent + 4);
            System.out.println(indentString + "{");
            return ret;
        }

        ret = new JSON();

        int lastComma = -1;
        ArrayList<String> elements = new ArrayList<String>();

        int i = 0, depth = 0;
        while (i < json.length()) {
            switch (json.charAt(i)) {
                case '{':
                    depth++;
                    break;
                case '}':
                    depth--;
                    break;
                case '[':
                    depth++;
                    break;
                case ']':
                    depth--;
                    break;
                case ':':
//                    if (depth == 0) {
//                        String keyString = json.substring(0, i + 1);
//                        keyString = keyString.substring(keyString.indexOf('"'), keyString.lastIndexOf('"'));
//
//                        String valueString = json.substring(json.indexOf('{', i));
//                        int subDepth = 1, index = 1;
//                    }
                    break;
                case ',':
                    if (depth == 0) {
                        elements.add(json.substring(lastComma + 1, i));
                        lastComma = i;
                    }
                    break;
//                        ret.put(keyString, valueString, indent + 4);
            }
            i++;
        }
        elements.add(json.substring(lastComma + 1));

        for (String s: elements) {
            if (s.indexOf("{") > 0) {

                System.out.println(indentString + s.substring(0, s.indexOf(":") + 1) + " {");
                readJSON(s.substring(s.indexOf("{") + 1, s.lastIndexOf("}")), indent + 4);
                System.out.println(indentString + "},");

            } else if (s.indexOf("[") > 0) {

                System.out.println(indentString + s.substring(0, s.indexOf(":") + 1) + " [");
                readJSON(s.substring(s.indexOf("[") + 1, s.lastIndexOf("]")), indent + 4);
                System.out.println(indentString + "],");

            } else {
                System.out.println(indentString + s.replace("\n", "\\n") + ",");
            }
        }

        return ret;
    }
}
