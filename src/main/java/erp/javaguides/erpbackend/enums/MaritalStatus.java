package erp.javaguides.erpbackend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MaritalStatus {
    MARRIED, UNMARRIED;

    @JsonCreator
    public static MaritalStatus fromValue(String value) {
        return MaritalStatus.valueOf(value);
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
