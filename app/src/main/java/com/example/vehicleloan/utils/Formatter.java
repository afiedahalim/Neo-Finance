package com.example.vehicleloan.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Formatter {
    private static final DecimalFormat df = new DecimalFormat("#,##0.00");
    public static String currency(BigDecimal value) {
        if (value == null) return "RM 0.00";
        return "RM " + df.format(value);
    }
}