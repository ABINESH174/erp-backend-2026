package erp.javaguides.erpbackend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CourseType {
    REGULAR,LATERAL,REGULAR_TRANSFER;

    @JsonCreator
    public static CourseType fromValue(String value) {
        return CourseType.valueOf(value);
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
