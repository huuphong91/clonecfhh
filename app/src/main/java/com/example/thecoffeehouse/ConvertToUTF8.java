package com.example.thecoffeehouse;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class ConvertToUTF8 {

    public static String onConvert(String str) {
        String temp = Normalizer.normalize (str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile ("\\p{InCOMBINING_DIACRITICAL_MARKS}+");
        return pattern.matcher (temp).replaceAll ("")
                .toLowerCase ()
                .replaceAll ("đ", "d");
    }
}
