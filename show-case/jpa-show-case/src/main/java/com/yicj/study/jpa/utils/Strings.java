package com.yicj.study.jpa.utils;

import org.slf4j.helpers.MessageFormatter;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author yicj
 * @since 2024/9/7 7:57
 */
public class Strings {
    public static final String EMPTY = "";
    public static final String SPACE = " ";

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.length() == 0;
    }

    public static boolean isNotNullOrEmpty(String string) {
        return !Strings.isNullOrEmpty(string);
    }

    public static boolean isNullOrWhitespace(String string) {
        if (isNullOrEmpty(string))
            return true;
        int length = string.length();
        if (length > 0) {
            if ("NULL".equalsIgnoreCase(string)) {
                return true;
            }
            for (int i = 0; i < length; i++) {
                if (!Character.isWhitespace(string.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean isNotNullOrWhitespace(String string) {
        return !Strings.isNullOrWhitespace(string);
    }

    public static String format(String pattern, Object... arguments) {
        return MessageFormat.format(pattern, arguments);
    }

    public static String formatSlf4jStyle(String format, Object... params) {
        return MessageFormatter.arrayFormat(format, params).getMessage();
    }

    public static String trimToEmpty(String str) {
        return isNullOrWhitespace(str) ? EMPTY : str.trim();
    }

    public static String trimToNull(String str) {
        return isNullOrWhitespace(str) ? null : str.trim();
    }

    public static boolean equals(String str1, String str2) {
        return (str1 == null && str2 == null) || (str1 != null && str1.equals(str2));
    }

    public static boolean listEqualsIgnoreOrder(Collection<String> list1, Collection<String> list2) {
        if (list1 == null)
            return list2 == null;
        if (list2 == null)
            return list1 == null;
        return new HashSet<>(list1).equals(new HashSet<>(list2));
    }

    public static boolean in(String[] sources, String target) {
        return in(sources, target, false);
    }

    public static boolean in(String[] sources, String target, boolean caseSensitive) {
        return in(Arrays.asList(sources), target, caseSensitive);
    }

    public static boolean in(Collection<String> sources, String target, boolean caseSensitive) {
        if (sources == null || sources.isEmpty() || Strings.isNullOrWhitespace(target))
            return false;
        return sources.stream()
                .anyMatch(item -> caseSensitive ? target.equals(item) : target.equalsIgnoreCase(item));
    }

    public static boolean any(String[] sources, String... targets) {
        if (sources == null || targets == null)
            return false;
        for (String source : sources) {
            for (String target : targets) {
                if (source != null && source.equalsIgnoreCase(target))
                    return true;
            }
        }
        return false;
    }

    public static String valueOf(final Object o) {
        if (o == null)
            return Strings.EMPTY;
        return String.valueOf(o);
    }

    public static String lowerOrNull(final String value) {
        if (value == null)
            return null;
        return value.toLowerCase();
    }

    public static String upperOrNull(final String value) {
        if (value == null)
            return null;
        return value.toUpperCase();
    }

    public static String lowerOrEmpty(final String value) {
        if (value == null)
            return EMPTY;
        return value.toLowerCase();
    }

    public static String upperOrEmpty(final String value) {
        if (value == null)
            return EMPTY;
        return value.toUpperCase();
    }

    public static String decapitalize(String string) {
        if (string == null || string.length() == 0) {
            return string;
        }
        char c[] = string.toCharArray();
        c[0] = Character.toLowerCase(c[0]);
        return new String(c);
    }

    public static class StringList extends ArrayList<String> {
        private static final long serialVersionUID = 6087372542555950940L;
    }
}
