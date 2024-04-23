package erp.javaguides.erpbackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Student")
public class Student {

    @Column(name="First_Name",nullable=false)
    private String First_Name;
    @Column(name="Last_Name",nullable = false)
    private String Last_Name;
    @Column(name="Date_Of_Birth",nullable = false)
    //@JsonFormat(pattern="yyyy-mm-dd")
    private String Date_Of_Birth;
    @Column(name="Gender",nullable=false)
    private String Gender;
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
    @Id
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
    private String SSLC_File_Path;
    private String HSC_1_Year_File_Path;
    private String HSC_2_Year_File_Path;
    private String Diploma_File_Path;
    private String Emis_Number;
    private String First_Graduate;
    private String Special_Category;
}

