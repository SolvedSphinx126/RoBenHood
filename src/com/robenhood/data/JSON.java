package com.robenhood.data;
import java.util.regex.*;
public class JSON {
    public JSON(String input)
    {

        String out = "";
        int indent = 0;
        out = input.replaceAll("({)|(})|(,)|([)|(])","{\n");
    }
}
