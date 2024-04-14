package erp.javaguides.erpbackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private Long Id;
    private String First_Name;
    private String Last_Name;
    @JsonFormat(pattern="yyyy-mm-dd")
    private String Date_Of_Birth;
    private String Gender;
    private String Aadhar_Number;
    private String Nationality;
    private String Religion;
    private String Caste;
    private String Fathers_Name;
    private String Fathers_Occupation;
    private String Fathers_Mobile_Number;
    private String Mothers_Name;
    private String Mothers_Occupation;
    private String Mothers_Mobile_Number;
    private String Community;
    private String Guardians_Name;
    private String Guardians_Occupation;
    private String Guardians_Mobile_Number;
    private String Parents_Status;
    private String Income;
    private String Marital_Status;
    private String mobile_Number;
    private String Email_Id;
    private String Residential_Address;
    private String Communication_Address;
    private String Hosteller;
    private String Hostel_Type;
    private String Bank_Name;
    private String IFSC_Code;
    private String Branch_Name;
    private String Account_Number;
    private String SSLC;
    private String HSC_1_Year;
    private String HSC_2_Year;
    private String Diploma;
    private String Emis_Number;
    private String First_Graduate;
    private String Special_Category;
}

