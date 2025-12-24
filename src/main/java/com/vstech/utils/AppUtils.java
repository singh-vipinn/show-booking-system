package com.vstech.utils;

public class AppUtils {

    public static int timeToInt(String timeStr) {
        return Integer.parseInt(timeStr.split(":")[0]);
    }
}
