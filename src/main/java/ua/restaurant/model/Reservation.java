package ua.restaurant.model;

import ua.common.BaseEntity;
import ua.restaurant.util.RestaurantUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Reservation of a restaurant table.
 * Identity is the booking slot: table + date + start time + end time.
 */
public class Reservation extends BaseEntity {

    private final Customer customer;
    private final RestaurantTable table;
    private final LocalDate reservationDate;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final int guestCount;
    private String status;

    /** Private constructor: reservations are created using {@link #of(...)}. */
    private Reservation(Customer customer, RestaurantTable table, LocalDate reservationDate,
                        LocalTime startTime, LocalTime endTime, int guestCount, String status) {
        super();
        this.customer = RestaurantUtils.requireNotNull(customer, "Customer");
        this.table = RestaurantUtils.requireNotNull(table, "Restaurant table");
        this.reservationDate = RestaurantUtils.requireNotNull(reservationDate, "Reservation date");
        this.startTime = RestaurantUtils.requireNotNull(startTime, "Start time");
        this.endTime = RestaurantUtils.requireNotNull(endTime, "End time");
        if (!this.endTime.isAfter(this.startTime)) {
            throw new IllegalArgumentException(
                    "End time expected to be later than start time, got: "
                            + this.endTime + " (start=" + this.startTime + ")");
        }
        this.guestCount = RestaurantUtils.requireInRange(
                guestCount, 1, table.getCapacity(), "Guest count");
        setStatus(status);
    }

    public static Reservation of(Customer customer, RestaurantTable table, LocalDate reservationDate,
                                 LocalTime startTime, LocalTime endTime, int guestCount, String status) {
        return new Reservation(customer, table, reservationDate,
                startTime, endTime, guestCount, status);
    }

    public Customer getCustomer() {
        return customer;
    }

    public RestaurantTable getTable() {
        return table;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public int getGuestCount() {
        return guestCount;
    }

    public String getStatus() {
        return status;
    }

    public final void setStatus(String status) {
        this.status = RestaurantUtils.validateReservationStatus(status);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Reservation that = (Reservation) object;
        return table.equals(that.table)
                && reservationDate.equals(that.reservationDate)
                && startTime.equals(that.startTime)
                && endTime.equals(that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(table, reservationDate, startTime, endTime);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "customer=" + customer.getName() +
                ", table=" + table.getTableNumber() +
                ", reservationDate=" + reservationDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", guestCount=" + guestCount +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
