package com.wolfinfinity.sqlitedata;

public class Utils {

    public static boolean isStringNull(String str) {
        return str == null || str.length() == 0 || str.equals("[]") || str.equals("{}");
    }

    public static boolean isEmailValidate(String str) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return str.matches(emailPattern);
    }
}
