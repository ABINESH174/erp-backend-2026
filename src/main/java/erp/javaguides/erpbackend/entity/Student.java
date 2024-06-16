package erp.javaguides.erpbackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Student")
public class Student {
    @Id
    @Column(unique = true)
    private String register_No;
    @Column(nullable=false)
    private String First_Name;
    @Column(nullable = false)
    private String Last_Name;
    @Column(nullable = false)
    private String Date_Of_Birth;
    private String Gender;
    @Column(unique = true)
    private String Aadhar_Number;
    private String Nationality;
    private String Religion;
    private String Community;
    private String Caste;
    private String Fathers_Name;
    private String Fathers_Occupation;
    private String Fathers_Mobile_Number;
    private String Mothers_Name;
    private String Mothers_Occupation;
    private String Mothers_Mobile_Number;
    private String Guardians_Name;
    private String Guardians_Occupation;
    private String Guardians_Mobile_Number;
    private String Parents_Status;
    private String Income;
    private String Marital_Status;
    private String Profile_Photo_Path;
    private String Mobile_Number;
    private String emailid;
    private String Residential_Address;
    private String Communication_Address;
    private String Hosteller;
    private String Hostel_Type;
    private String Bank_Name;
    private String Ifsc_Code;
    private String Branch_Name;
    private String Account_Number;
    private String Sslc;
    private String Hsc_1_Year;
    private String Hsc_2_Year;
    private String Diploma;
    private String Sslc_File_Path;
    private String Hsc_1_Year_File_Path;
    private String Hsc_2_Year_File_Path;
    private String Diploma_File_Path;
    private String Emis_Number;
    private String First_Graduate;
    private String Special_Category;
}

