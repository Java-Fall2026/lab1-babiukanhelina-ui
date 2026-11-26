package ua.restaurant;

import ua.common.BaseEntity;
import ua.restaurant.model.Customer;
import ua.restaurant.model.Reservation;
import ua.restaurant.model.RestaurantOrder;
import ua.restaurant.model.RestaurantTable;
import ua.restaurant.util.RestaurantUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

/** Demonstrates every required part of Lab 1 for the restaurant domain. */
public final class Main {

    private Main() {
    }

    /**
     * Protected-access demonstration: a subclass can read the protected field
     * declared in BaseEntity. Main itself is not allowed to read it directly.
     */
    private static final class ProtectedAccessProbe extends BaseEntity {
        private ProtectedAccessProbe() {
            super();
        }

        private String readProtectedCreatedAt() {
            return createdAt.toString();
        }
    }

    public static void main(String[] args) {
        System.out.println("====================================================");
        System.out.println("LAB 1 - JAVA BASICS AND ACCESS MODIFIERS");
        System.out.println("Domain: Restaurant table reservations (variant 2)");
        System.out.println("====================================================");

        demonstrateObjectCreation();
        demonstrateNormalization();
        demonstrateValidation();
        demonstrateEquality();
        demonstrateAccessModifiers();
        demonstrateCalculations();
    }

    private static void demonstrateObjectCreation() {
        System.out.println("\n1. OBJECT CREATION: constructors and factory methods");
        Customer customer = Customer.of(
                "+380 (50) 123-45-67", "  anhelina   babiuk  ", "Anhelina@MAIL.COM");
        RestaurantTable table = new RestaurantTable(12, 6, " main_hall ");
        Reservation reservation = Reservation.of(
                customer, table, LocalDate.of(2026, 12, 5),
                LocalTime.of(18, 30), LocalTime.of(20, 0), 4, " pending ");
        RestaurantOrder order = new RestaurantOrder("ORD-501", reservation, 1250.00, 150.00);

        System.out.println("Customer (factory of): " + customer);
        System.out.println("RestaurantTable (public constructor): " + table);
        System.out.println("Reservation (factory of): " + reservation);
        System.out.println("RestaurantOrder (public constructor): " + order);
    }

    private static void demonstrateNormalization() {
        System.out.println("\n2. NORMALIZATION AND FORMATTING");
        Customer customer = Customer.of(
                "+380501234567", "  anhelina   babiuk ", "ANHELINA@MAIL.COM");
        RestaurantTable table = new RestaurantTable(7, 4, " terrace ");

        System.out.println("Phone: +380 (50) 123-45-67 -> " + customer.getPhone());
        System.out.println("Name: '  anhelina   babiuk ' -> " + customer.getName());
        System.out.println("Email: 'ANHELINA@MAIL.COM' -> " + customer.getEmail());
        System.out.println("Zone: ' terrace ' -> " + table.getZone());
        System.out.println("Money format: 1075 -> " + RestaurantUtils.formatMoney(1075));
    }

    private static void demonstrateValidation() {
        System.out.println("\n3. VALIDATION IN CONSTRUCTORS AND SETTERS");
        runInvalidCase("blank phone", () -> Customer.of("   ", "Test User", "test@example.com"));
        runInvalidCase("email without @", () -> Customer.of("+380501234567", "Test User", "not-an-email"));
        Customer minimalEmail = Customer.of("12345", "Test User", "user@domain");
        System.out.println("Profile-valid minimal phone/email -> "
                + minimalEmail.getPhone() + ", " + minimalEmail.getEmail());
        runInvalidCase("invalid table number", () -> new RestaurantTable(0, 4, "VIP"));
        runInvalidCase("invalid zone", () -> new RestaurantTable(3, 4, "GARDEN"));

        Customer customer = Customer.of("+380501234567", "Test User", "test@example.com");
        RestaurantTable table = new RestaurantTable(4, 2, "BAR");
        runInvalidCase("too many guests", () -> Reservation.of(
                customer, table, LocalDate.of(2026, 12, 10),
                LocalTime.of(18, 0), LocalTime.of(19, 0), 3, "PENDING"));
        runInvalidCase("invalid time interval", () -> Reservation.of(
                customer, table, LocalDate.of(2026, 12, 10),
                LocalTime.of(19, 0), LocalTime.of(18, 0), 2, "PENDING"));

        Reservation reservation = Reservation.of(
                customer, table, LocalDate.of(2026, 12, 10),
                LocalTime.of(18, 0), LocalTime.of(19, 30), 2, "PENDING");
        reservation.setStatus(" confirmed ");
        System.out.println("Valid Reservation.setStatus(' confirmed ') -> " + reservation.getStatus());
        runInvalidCase("invalid status setter", () -> reservation.setStatus("UNKNOWN"));

        RestaurantOrder order = new RestaurantOrder("ORD-VALID", reservation, 500.00, 50.00);
        order.setDiscountAmount(75.00);
        System.out.println("Valid RestaurantOrder.setDiscountAmount(75) -> "
                + RestaurantUtils.formatMoney(order.getDiscountAmount()));
        runInvalidCase("discount greater than order total", () -> order.setDiscountAmount(600.00));
    }

    private static void demonstrateEquality() {
        System.out.println("\n4. REFERENCE EQUALITY (==) AND LOGICAL EQUALITY (equals)");
        Customer first = Customer.of("+380501234567", "Anhelina Babiuk", "a@example.com");
        Customer second = Customer.of("+380 (50) 123-45-67", "Another Name", "b@example.com");

        System.out.println("first == second: " + (first == second));
        System.out.println("first.equals(second): " + first.equals(second));
        System.out.println("first.hashCode() == second.hashCode(): "
                + (first.hashCode() == second.hashCode()));

        Set<Customer> uniqueCustomers = new HashSet<>();
        uniqueCustomers.add(first);
        uniqueCustomers.add(second);
        System.out.println("HashSet size for two customers with same phone: " + uniqueCustomers.size());

        RestaurantTable one = new RestaurantTable(12, 4, "VIP");
        RestaurantTable anotherInstanceOfOne = new RestaurantTable(12, 8, "MAIN_HALL");
        System.out.println("table1 == table2: " + (one == anotherInstanceOfOne));
        System.out.println("table1.equals(table2): " + one.equals(anotherInstanceOfOne));

        Reservation firstSlot = Reservation.of(first, one, LocalDate.of(2026, 12, 5),
                LocalTime.of(18, 0), LocalTime.of(19, 0), 2, "PENDING");
        Reservation sameSlot = Reservation.of(second, anotherInstanceOfOne,
                LocalDate.of(2026, 12, 5), LocalTime.of(18, 0), LocalTime.of(19, 0), 3, "CONFIRMED");
        System.out.println("Reservations for the same table and time slot are equal: "
                + firstSlot.equals(sameSlot));

        RestaurantOrder orderA = new RestaurantOrder("ORD-100", firstSlot, 200, 0);
        RestaurantOrder orderB = new RestaurantOrder("ORD-100", firstSlot, 175, 0);
        System.out.println("Orders with the same order ID are equal: " + orderA.equals(orderB));
    }

    private static void demonstrateAccessModifiers() {
        System.out.println("\n5. PROTECTED AND PACKAGE-PRIVATE ACCESS");
        ProtectedAccessProbe probe = new ProtectedAccessProbe();
        System.out.println("protected BaseEntity.createdAt read from a subclass: "
                + probe.readProtectedCreatedAt());
        System.out.println("BaseEntity public getter returns the same value: "
                + probe.getCreatedAt());

        // Main is in ua.restaurant and cannot directly name package-private helper classes.
        // This public facade runs code inside ua.restaurant.util, where those members are visible.
        System.out.println("Package-private members accessed inside ua.restaurant.util: ");
        System.out.println(RestaurantUtils.packagePrivateAccessDemonstration());
    }

    private static void demonstrateCalculations() {
        System.out.println("\n6. COMPUTED METHODS FROM RESTAURANTUTILS");
        Customer customer = Customer.of("+380501234567", "Anhelina Babiuk", "a@example.com");
        RestaurantTable table = new RestaurantTable(12, 6, "MAIN_HALL");
        Reservation reservation = Reservation.of(
                customer, table, LocalDate.of(2026, 12, 5),
                LocalTime.of(18, 30), LocalTime.of(20, 0), 4, "CONFIRMED");
        RestaurantOrder order = new RestaurantOrder("ORD-501", reservation, 1250.00, 175.00);

        System.out.println("durationMinutes(reservation) = "
                + RestaurantUtils.durationMinutes(reservation) + " minutes");
        System.out.println("finalAmount(order) = "
                + RestaurantUtils.formatMoney(RestaurantUtils.finalAmount(order)));
    }

    private static void runInvalidCase(String title, Runnable action) {
        try {
            action.run();
            System.out.println("[ERROR] " + title + ": no exception was thrown");
        } catch (IllegalArgumentException exception) {
            System.out.println("[OK] " + title + " -> " + exception.getMessage());
        }
    }
}
