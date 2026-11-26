package ua.restaurant.model;

import ua.common.BaseEntity;
import ua.restaurant.util.RestaurantUtils;

import java.util.Objects;

/** Restaurant customer. Natural identity: normalized phone number. */
public class Customer extends BaseEntity {

    private final String phone;
    private final String name;
    private final String email;

    /** Private constructor: instances are created through {@link #of(String, String, String)}. */
    private Customer(String phone, String name, String email) {
        super();
        this.phone = RestaurantUtils.validatePhone(phone);
        this.name = RestaurantUtils.capitalizeName(name);
        this.email = RestaurantUtils.validateEmail(email);
    }

    public static Customer of(String phone, String name, String email) {
        return new Customer(phone, name, email);
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Customer customer = (Customer) object;
        return phone.equals(customer.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
