package erp.javaguides.erpbackend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PursuingYear {
    FIRST,SECOND,THIRD,FOURTH;

    @JsonCreator
    public static PursuingYear fromValue(String value) {
        return PursuingYear.valueOf(value);
    }

    @JsonValue
    public String getPursuingYear() {
        return name();
    }
}
