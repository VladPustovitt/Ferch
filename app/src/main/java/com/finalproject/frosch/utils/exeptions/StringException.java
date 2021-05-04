package com.finalproject.frosch.utils.exeptions;

public class StringException extends Exception {
    public StringException(String message) {
        super(message);
    }

    public static void equalNull(String s) throws NullPointerException {
        if (s.equals("") || s.matches("^\\s\\+")){
            throw new NullPointerException();
        }
    }
}
