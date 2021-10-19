package com.robenhood.data;

public class JSONReader {

    public static JSON readJSON(String json, int indent) {
        json = json.trim();
        System.out.println(indent + ":\n" + json);
        if (json.startsWith("{") && json.endsWith("}"))
            return readJSON(json.substring(1, json.length() - 1), indent + 4);
        JSON ret = new JSON();

        int i = 0, depth = 0;


        while (i < json.length()) {
            switch (json.charAt(i)) {
                case '{':
                    depth++;
                    break;
                case '}':
                    depth--;
                    break;
                case ':':
                    if (depth == 0) {
                        String keyString = json.substring(0, i + 1);
                        keyString = keyString.substring(keyString.indexOf('"'), keyString.lastIndexOf('"'));

                        String valueString = json.substring(json.indexOf('{', i));
                        int subDepth = 1, index = 1;

                    }

//                        ret.put(keyString, valueString, indent + 4);
            }

            i++;
        }

        return ret;
    }
}
