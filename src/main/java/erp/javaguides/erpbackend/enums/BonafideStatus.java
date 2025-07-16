package erp.javaguides.erpbackend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BonafideStatus {
<<<<<<< HEAD
    PENDING,FACULTY_APPROVED,HOD_APPROVED,OB_APPROVED,PRINCIPAL_APPROVED,REJECTED,OB_REJECTED,NOTIFIED,FACULTY_REJECTED,HOD_REJECTED;
=======
    PENDING,FACULTY_APPROVED,HOD_APPROVED,OB_APPROVED,PRINCIPAL_APPROVED,REJECTED,OB_REJECTED,FACULTY_REJECTED,HOD_REJECTED,NOTIFIED;
>>>>>>> 2bf405fcf4e9e2abbd4cb7a36444496396403921

    @JsonCreator
    public static BonafideStatus fromValue(String value){
        return BonafideStatus.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue(){
        return name();
    }

}
