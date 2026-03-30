package tickethub_service.booking.entity.enums;

import lombok.Getter;

@Getter
public enum PromoType {
    PERCENTAGE("percentage"),
    FIXED("fixed");

    private final String value;

    PromoType(String value) {
        this.value = value;
    }
}
