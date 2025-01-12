package erp.javaguides.erpbackend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Hosteller {
    Yes,No;

    @JsonCreator
    public static Hosteller fromValue(String value) {
        return Hosteller.valueOf(value);
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
