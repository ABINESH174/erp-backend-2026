package erp.javaguides.erpbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AcademicsDto {
    private String Email_Id;
    private String Register_No;
    private String Programme;
    private String Discipline;
    private String Admission_Number;
    private String Academic_Year;
    private String Semester;
    private String ABC_Id;
    private String UMIS_Id;
    private String Date_Of_Admission;
    private String Course_Joined_Date;
    private String Course_Type;
    private String Regulation;
    private String Fast_Track;
    private String CGPA;
    private String Student_Status;
}
