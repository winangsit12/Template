package com.template.helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateHelper {
    public static String dateParse(LocalDate date, String pattern){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, new Locale("id", "ID"));
        return date.format(formatter);
    }
}
