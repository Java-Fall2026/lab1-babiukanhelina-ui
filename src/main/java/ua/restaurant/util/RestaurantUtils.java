package ua.restaurant.util;

import ua.restaurant.model.Reservation;
import ua.restaurant.model.RestaurantOrder;

import java.time.Duration;
import java.util.Set;
import java.util.regex.Pattern;

/** Public API for restaurant-domain validation, normalization and calculations. */
public final class RestaurantUtils {

    // Package-private constants: available to classes in ua.restaurant.util only.
    static final Set<String> TABLE_ZONES = Set.of("MAIN_HALL", "TERRACE", "VIP", "BAR");
    static final Set<String> RESERVATION_STATUSES =
            Set.of("PENDING", "CONFIRMED", "SEATED", "COMPLETED", "CANCELLED");

    private static final Pattern EMAIL_AT_PATTERN = Pattern.compile("@");

    private RestaurantUtils() {
        // Utility class.
    }

    public static <T> T requireNotNull(T value, String fieldName) {
        return ValidationHelper.requireNotNull(value, fieldName);
    }

    public static String requireNotBlank(String value, String fieldName) {
        return ValidationHelper.requireNotBlank(value, fieldName);
    }

    public static int requireInRange(int value, int min, int max, String fieldName) {
        return ValidationHelper.requireInRange(value, min, max, fieldName);
    }

    public static double requireInRange(double value, double min, double max, String fieldName) {
        return ValidationHelper.requireInRange(value, min, max, fieldName);
    }

    public static double requireNonNegative(double value, String fieldName) {
        return ValidationHelper.requireNonNegative(value, fieldName);
    }

    public static String normalize(String value, String fieldName) {
        return FormatHelper.normalizeText(ValidationHelper.requireNotBlank(value, fieldName));
    }

    public static String normalizeUpper(String value, String fieldName) {
        return FormatHelper.normalizeUpper(ValidationHelper.requireNotBlank(value, fieldName));
    }

    public static String normalizeEmail(String value, String fieldName) {
        return FormatHelper.normalizeEmail(ValidationHelper.requireNotBlank(value, fieldName));
    }

    /** The domain profile requires a non-blank phone; presentation separators are normalized. */
    public static String validatePhone(String value) {
        String phone = FormatHelper.normalizePhone(
                ValidationHelper.requireNotBlank(value, "Phone"));
        return ValidationHelper.requireNotBlank(phone, "Phone after normalization");
    }

    public static String capitalizeName(String value) {
        return FormatHelper.capitalizeWords(
                ValidationHelper.requireNotBlank(value, "Name"));
    }

    /** The domain profile requires a non-blank email containing the @ character. */
    public static String validateEmail(String value) {
        String email = FormatHelper.normalizeEmail(
                ValidationHelper.requireNotBlank(value, "Email"));
        if (!EMAIL_AT_PATTERN.matcher(email).find()) {
            throw new IllegalArgumentException(
                    "Email expected non-blank text containing '@', got: '"
                            + value + "'");
        }
        return email;
    }

    public static String validateZone(String value) {
        String zone = normalizeUpper(value, "Zone");
        if (!TABLE_ZONES.contains(zone)) {
            throw new IllegalArgumentException(
                    "Zone expected one of " + TABLE_ZONES + ", got: '" + value + "'");
        }
        return zone;
    }

    public static String validateReservationStatus(String value) {
        String status = normalizeUpper(value, "Reservation status");
        if (!RESERVATION_STATUSES.contains(status)) {
            throw new IllegalArgumentException(
                    "Reservation status expected one of " + RESERVATION_STATUSES
                            + ", got: '" + value + "'");
        }
        return status;
    }

    public static String formatMoney(double amount) {
        ValidationHelper.requireFinite(amount, "Amount");
        return FormatHelper.formatMoney(amount);
    }

    /** Calculates the reservation duration in whole minutes. */
    public static long durationMinutes(Reservation reservation) {
        Reservation checked = ValidationHelper.requireNotNull(reservation, "Reservation");
        return Duration.between(checked.getStartTime(), checked.getEndTime()).toMinutes();
    }

    /** Calculates the order amount after the discount has been applied. */
    public static double finalAmount(RestaurantOrder order) {
        RestaurantOrder checked = ValidationHelper.requireNotNull(order, "Restaurant order");
        return checked.getTotalAmount() - checked.getDiscountAmount();
    }

    /**
     * A visible demonstration called from Main. This method can access package-private
     * ValidationHelper, FormatHelper and package-private constants because it belongs
     * to the same package. Main itself cannot access those members directly.
     */
    public static String packagePrivateAccessDemonstration() {
        String checked = ValidationHelper.requireNotBlank("  package scope  ", "Demo value");
        String normalized = FormatHelper.normalizeUpper(checked);
        return "ValidationHelper + FormatHelper + TABLE_ZONES -> " + normalized
                + "; known zones=" + TABLE_ZONES;
    }
}
