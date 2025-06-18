package erp.javaguides.erpbackend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum HostelType {
    Free, Paid;

    @JsonCreator
    public static HostelType fromValue(String value) {
        return HostelType.valueOf(value);
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
