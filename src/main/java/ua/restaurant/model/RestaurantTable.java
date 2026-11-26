package ua.restaurant.model;

import ua.common.BaseEntity;
import ua.restaurant.util.RestaurantUtils;

import java.util.Objects;

/** Restaurant table. Natural identity: table number. */
public class RestaurantTable extends BaseEntity {

    private final int tableNumber;
    private final int capacity;
    private final String zone;

    public RestaurantTable(int tableNumber, int capacity, String zone) {
        super();
        this.tableNumber = RestaurantUtils.requireInRange(tableNumber, 1, 200, "Table number");
        this.capacity = RestaurantUtils.requireInRange(capacity, 1, 20, "Table capacity");
        this.zone = RestaurantUtils.validateZone(zone);
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getZone() {
        return zone;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        RestaurantTable that = (RestaurantTable) object;
        return tableNumber == that.tableNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableNumber);
    }

    @Override
    public String toString() {
        return "RestaurantTable{" +
                "tableNumber=" + tableNumber +
                ", capacity=" + capacity +
                ", zone='" + zone + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
