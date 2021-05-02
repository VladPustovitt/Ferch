package com.finalproject.frosch.utils.convertor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateConvector {
    private static final String pattern = "dd.MM.yyyy HH:mm";

    public static String msToDateString(long ms){
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(new Date(ms));
    }

    public static long dateStringToMs(String dateString){
        LocalDateTime localDateTime = LocalDateTime.parse(dateString,
                DateTimeFormatter.ofPattern(pattern));
        return localDateTime
                .atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli();
    }

    public static String addZeros(String s){
        if (s.length() < 2) return "0" + s;
        return s;
    }
}
