package com.template.helper;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyHelper {
    public static String currencyParse(BigDecimal nominal){
        return NumberFormat.getCurrencyInstance(new Locale("id", "ID")).format(nominal);
    }
}
