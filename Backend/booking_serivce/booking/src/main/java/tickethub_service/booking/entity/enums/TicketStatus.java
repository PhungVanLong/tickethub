package tickethub_service.booking.entity.enums;

import lombok.Getter;

@Getter
public enum TicketStatus {
    VALID("valid"),
    USED("used"),
    EXPIRED("expired"),
    CANCELLED("cancelled");

    private final String value;

    TicketStatus(String value) {
        this.value = value;
    }
}
