package com.wolfinfinity.sqlitedata;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

public class Utils {

    public static boolean isStringNull(String str) {
        return str == null || str.length() == 0 || str.equals("[]") || str.equals("{}");
    }

    public static boolean isEmailValidate(String str) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return str.matches(emailPattern);
    }

    public static String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        return DateFormat.format("dd-MM-yyyy HH:mm", cal).toString();
    }
}
