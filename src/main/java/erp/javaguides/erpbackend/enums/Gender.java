package erp.javaguides.erpbackend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    Yes,No;

    @JsonCreator
    public static Gender fromValue(String value) {
        return Gender.valueOf(value);
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
