package com.rjxy.Util;

public class ToolUtil {
    public static boolean InIntArray(int[] array, int target) {
        for (int i : array) {
            if (i == target) {
                return true;
            }
        }
        return false;
    }

    public static boolean InStringArray(String[] array, String target) {
        for (String i : array) {
            if (target.equals(i)) {
                return true;
            }
        }
        return false;
    }
}
