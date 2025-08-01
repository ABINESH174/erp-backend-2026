package erp.javaguides.erpbackend.utility;

import java.util.List;

import erp.javaguides.erpbackend.enums.Gender;
import org.springframework.stereotype.Service;

import erp.javaguides.erpbackend.enums.PursuingYear;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// A general purpose file that contains methods that are required by the whole application anywhere...
// Made a bean that can be injected anywhere in AppConfig file using the @Service annotation... 
@Service
public class UtilityService {
    public PursuingYear convertSemesterToYear(String semester) {
        return switch(semester){
            case "I","II"-> PursuingYear.FIRST;
            case "III", "IV" -> PursuingYear.SECOND;
            case "V", "VI" -> PursuingYear.THIRD;
            case "VII", "VIII" -> PursuingYear.FOURTH;
            default -> null;
        };
    }

    public List<String> getListOfSemesterFromYear(PursuingYear year) {
        return switch( year ) {
            case PursuingYear.FIRST -> List.of("I","II");
            case PursuingYear.SECOND -> List.of("III","IV");
            case PursuingYear.THIRD -> List.of("V","VI");
            case PursuingYear.FOURTH -> List.of("VII","VIII");
        };
    }

    public static String yearMonthDayToDayMonthYear(String dateWithYearFirst) {
        if(dateWithYearFirst.matches("\\d{2}-\\d{2}-\\d{4}")){
            return dateWithYearFirst;
        }
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateWithYearFirst , inputFormatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateWithDayFirst = date.format(outputFormatter);
        return dateWithDayFirst;
    }

    public String[] genderIdentifier(Gender gender) {
        String[] identifyGender = new String[3];
        switch (gender){
            case Male :
                identifyGender[0] = "Mr. ";
                identifyGender[1] = "He";
                identifyGender[2] = "him";
                break;
            case Female :
                identifyGender[0] = "Ms. ";
                identifyGender[1] = "She";
                identifyGender[2] = "her";
                break;
            case Others :
                identifyGender[0] = "Mx. ";
                identifyGender[1] = "He/She";
                identifyGender[2] = "him/her";
                break;
            default :
                identifyGender[0] = "Mr./Ms. ";
                identifyGender[1] = "He/She";
                identifyGender[2] = "him/her";
        }
        return identifyGender;
    }
    
}
