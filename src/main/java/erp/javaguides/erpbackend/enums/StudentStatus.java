package erp.javaguides.erpbackend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StudentStatus {
    Pursuing,Terminated,Discontinued,PassedOut;

    @JsonCreator
    public static StudentStatus fromValue(String value) {
        return StudentStatus.valueOf(value);
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
