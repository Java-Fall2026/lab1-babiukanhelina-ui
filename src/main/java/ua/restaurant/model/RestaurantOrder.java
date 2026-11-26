package ua.restaurant.model;

import ua.common.BaseEntity;
import ua.restaurant.util.RestaurantUtils;

import java.util.Objects;

/** Order linked to a reservation. Natural identity: order ID. */
public class RestaurantOrder extends BaseEntity {

    private final String orderId;
    private final Reservation reservation;
    private final double totalAmount;
    private double discountAmount;

    public RestaurantOrder(String orderId, Reservation reservation,
                           double totalAmount, double discountAmount) {
        super();
        this.orderId = RestaurantUtils.requireNotBlank(orderId, "Order ID");
        this.reservation = RestaurantUtils.requireNotNull(reservation, "Reservation");
        this.totalAmount = RestaurantUtils.requireNonNegative(totalAmount, "Total amount");
        setDiscountAmount(discountAmount);
    }

    public String getOrderId() {
        return orderId;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public final void setDiscountAmount(double discountAmount) {
        this.discountAmount = RestaurantUtils.requireInRange(
                discountAmount, 0.0, totalAmount, "Discount amount");
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        RestaurantOrder that = (RestaurantOrder) object;
        return orderId.equals(that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    @Override
    public String toString() {
        return "RestaurantOrder{" +
                "orderId='" + orderId + '\'' +
                ", reservationDate=" + reservation.getReservationDate() +
                ", tableNumber=" + reservation.getTable().getTableNumber() +
                ", totalAmount=" + RestaurantUtils.formatMoney(totalAmount) +
                ", discountAmount=" + RestaurantUtils.formatMoney(discountAmount) +
                ", createdAt=" + createdAt +
                '}';
    }
}
