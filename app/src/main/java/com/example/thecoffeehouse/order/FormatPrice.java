package com.example.thecoffeehouse.order;

import java.text.DecimalFormat;

public class FormatPrice {
    public String formatPrice(int price){
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return String.format ("%s đ", formatter.format (price));
    }
}
