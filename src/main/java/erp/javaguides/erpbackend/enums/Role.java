package erp.javaguides.erpbackend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    STUDENT,FACULTY,HOD,OB,PRINCIPAL,ADMIN;

    @JsonCreator
    public static Role fromValue(String value) {
        return Role.valueOf(value);
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
