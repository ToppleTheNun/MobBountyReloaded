package org.nunnerycode.bukkit.mobbountyreloaded.utils;

import java.nio.charset.Charset;

public final class StringUtils {

    private StringUtils() {
        // do nothing
    }

    public static String convertBytesToCharset(byte[] bytes, Charset charset) {
        return new String(bytes, charset);
    }

    public static byte[] convertStringToCharset(String string, Charset charset) {
        return string.getBytes(charset);
    }

    public static String replaceArgs(String string, String[][] args) {
        String s = string;
        for (String[] arg : args) {
            s = s.replace(arg[0], arg[1]);
        }
        return s;
    }

    public static String colorString(String string) {
        return colorString(string, '&');
    }

    public static String colorString(String string, char c) {
        if (string == null) {
            throw new IllegalArgumentException("String cannot be null");
        }
        return string.replace(c, '\u00A7').replace("\u00A7\u00A7", String.valueOf(c));
    }

}
