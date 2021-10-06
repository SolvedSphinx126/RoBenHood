package com.robenhood.data;

public class JSONReader {
    public JSONReader(String input) {

        String out = "";
        int indent = 0;
        out = input.replaceAll("({)|(})|(,)|([)|(])","{\n");
    }
}
