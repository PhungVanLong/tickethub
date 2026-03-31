package tickethub_service.booking.entity.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    PENDING("pending"),
    PROCESSING("processing"),
    COMPLETED("completed"),
    FAILED("failed"),
    REFUNDED("refunded");

    private final String value;

    PaymentStatus(String value) {
        this.value = value;
    }
}
