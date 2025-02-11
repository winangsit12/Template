package com.template.helper;

import java.text.NumberFormat;
import java.util.Locale;

public class PercentHelper {
    public static String percentageParse(double value) {
        NumberFormat percentFormat = NumberFormat.getPercentInstance(new Locale("id", "ID"));
        percentFormat.setMaximumFractionDigits(2);
        return percentFormat.format(value);
    }
}
