package com.teamducati.cloneappcfh.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Utils {
    public static String formatMoney(double money) {
        NumberFormat formatter = new DecimalFormat("#,###");
        return formatter.format(money).replace(',', '.') + " đ";
    }
}
