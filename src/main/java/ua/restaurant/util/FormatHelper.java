package ua.restaurant.util;

import java.util.Locale;

/**
 * Package-private normalization and formatting helper.
 * It is intentionally accessible only from the ua.restaurant.util package.
 */
final class FormatHelper {

    private FormatHelper() {
        // Utility class.
    }

    static String normalizeText(String value) {
        return value.trim().replaceAll("\\s+", " ");
    }

    static String normalizeUpper(String value) {
        return normalizeText(value).toUpperCase(Locale.ROOT);
    }

    static String normalizeEmail(String value) {
        return normalizeText(value).toLowerCase(Locale.ROOT);
    }

    static String normalizePhone(String value) {
        return value.trim().replaceAll("[\\s()\\-]", "");
    }

    static String capitalizeWords(String value) {
        String[] words = normalizeText(value).toLowerCase(Locale.ROOT).split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (!result.isEmpty()) {
                result.append(' ');
            }
            result.append(Character.toUpperCase(word.charAt(0)));
            if (word.length() > 1) {
                result.append(word.substring(1));
            }
        }
        return result.toString();
    }

    static String formatMoney(double amount) {
        return String.format(Locale.US, "%.2f", amount);
    }
}
