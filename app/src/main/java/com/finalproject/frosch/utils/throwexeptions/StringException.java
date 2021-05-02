package com.finalproject.frosch.utils.throwexeptions;

public class StringException {
    public static void equalNull(String s){
        if (s.equals("") || s.matches("^\\s\\+")){
            throw new NullPointerException();
        }
    }
}
