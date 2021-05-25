package com.finalproject.frosch.utils.convertor;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DateConvector {
    private static final String pattern = "dd.MM.yyyy HH:mm";

    public static String msToDateString(long ms){
        SimpleDateFormat df = new SimpleDateFormat(pattern);
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

    private static String numToMonth(int num){
        switch (num){
            case 1: return "января";
            case 2: return "февраля";
            case 3: return "марта";
            case 4: return "апреля";
            case 5: return "мая";
            case 6: return "июня";
            case 7: return "июля";
            case 8: return "августа";
            case 9: return "сентербря";
            case 10: return "октября";
            case 11: return "ноября";
            case 12: return "декабря";
            default: return "";
        }
    }

    public static String numStringToMonth(String num){
        switch (num){
            case "01": return "Январь";
            case "02": return "Февраль";
            case "03": return "Март";
            case "04": return "Апрель";
            case "05": return "Май";
            case "06": return "Июнь";
            case "07": return "Июль";
            case "08": return "Август";
            case "09": return "Сентябрь";
            case "10": return "Октябрь";
            case "11": return "Ноябрь";
            case "12": return "Декабрь";
            default: return "";
        }
    }

    private static String cutDayOfMonth(String day){
        switch(day){
            case "понедельник": return "пн";
            case "вторник": return "вт";
            case "среда": return "ср";
            case "четверг": return "чт";
            case "пятница": return "пт";
            case "суббота": return "сб";
            case "воскресенье": return "вс";
            default: return "";
        }
    }

    public static String getDateString(String date){
        String[] dateArray = date.split("\\.");
        String newDate = dateArray[0] + " " + numToMonth(Integer.parseInt(dateArray[1]));
        String currentDate = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
        if(!dateArray[2].equals(currentDate)) newDate += " " + dateArray[2];
        if(newDate.charAt(0) == '0') newDate = newDate.substring(1);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date(dateStringToMs(date + " 00:00"));
        String day = sdf.format(d);
        newDate += ", " + cutDayOfMonth(day);
        return newDate;
    }

    public static ArrayList<String> getListOfMonthWithYear(ArrayList<Long> list){
        SimpleDateFormat sdf = new SimpleDateFormat("MM.yyyy");
        ArrayList<String> monthWithYear = new ArrayList<>();
        for(Long dateMs: list){
            String dateString = sdf.format(new Date(dateMs));
            if (!monthWithYear.contains(dateString)) monthWithYear.add(dateString);
        }
        return monthWithYear;
    }
}
