package tickethub_service.booking.entity.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING("pending"),
    CONFIRMED("confirmed"),
    CANCELLED("cancelled"),
    COMPLETED("completed");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }
}
