package erp.javaguides.erpbackend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum FirstGraduate {
    YES, NO;

    @JsonCreator
    public static FirstGraduate fromValue(String value) {
        return FirstGraduate.valueOf(value);
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
