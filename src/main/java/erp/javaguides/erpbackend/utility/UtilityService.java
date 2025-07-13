package erp.javaguides.erpbackend.utility;

import org.springframework.stereotype.Service;

import erp.javaguides.erpbackend.enums.PursuingYear;

@Service
public class UtilityService {
    
    public PursuingYear convertSemesterToYear(String semester) {
        return switch(semester){
            case "I"-> PursuingYear.FIRST;
            case "II"-> PursuingYear.FIRST;
            case "III"-> PursuingYear.SECOND;
            case "IV"-> PursuingYear.SECOND;
            case "V"-> PursuingYear.THIRD;
            case "VI"-> PursuingYear.THIRD;
            case "VII"-> PursuingYear.FOURTH;
            case "VIII"-> PursuingYear.FOURTH;
            default -> null;
        };
    }
}
