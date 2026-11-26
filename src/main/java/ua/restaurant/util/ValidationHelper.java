package ua.restaurant.util;

/**
 * Package-private validation helper. It is intentionally not public:
 * callers outside ua.restaurant.util must use the public RestaurantUtils API.
 */
final class ValidationHelper {

    private ValidationHelper() {
        // Utility class.
    }

    static <T> T requireNotNull(T value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(
                    fieldName + " expected a non-null value, got: null");
        }
        return value;
    }

    static String requireNotBlank(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    fieldName + " expected non-blank text, got: '" + value + "'");
        }
        return value.trim();
    }

    static int requireInRange(int value, int min, int max, String fieldName) {
        if (value < min || value > max) {
            throw new IllegalArgumentException(
                    fieldName + " expected a value from " + min + " to " + max
                            + ", got: " + value);
        }
        return value;
    }

    static double requireInRange(double value, double min, double max, String fieldName) {
        requireFinite(value, fieldName);
        if (value < min || value > max) {
            throw new IllegalArgumentException(
                    fieldName + " expected a value from " + min + " to " + max
                            + ", got: " + value);
        }
        return value;
    }

    static double requireNonNegative(double value, String fieldName) {
        requireFinite(value, fieldName);
        if (value < 0.0) {
            throw new IllegalArgumentException(
                    fieldName + " expected a value greater than or equal to 0, got: " + value);
        }
        return value;
    }

    static void requireFinite(double value, String fieldName) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException(
                    fieldName + " expected a finite number, got: " + value);
        }
    }
}
