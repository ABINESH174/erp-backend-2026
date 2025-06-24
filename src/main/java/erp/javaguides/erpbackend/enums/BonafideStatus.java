package erp.javaguides.erpbackend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BonafideStatus {
    PENDING,FACULTY_APPROVED,HOD_APPROVED,OB_APPROVED,PRINCIPAL_APPROVED,REJECTED,OB_REJECTED,FACULTY_REJECTED,HOD_REJECTED,NOTIFIED;

    @JsonCreator
    public static BonafideStatus fromValue(String value){
        return BonafideStatus.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue(){
        return name();
    }

}
