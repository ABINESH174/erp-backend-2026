package erp.javaguides.erpbackend.dto;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentWithFilesDto {
    @Id
    @Column(unique = true)
    private String register_No;
    private String First_Name;
    private String Last_Name;
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
    private String Emis_Number;
    private String First_Graduate;
    private String Special_Category;
    private MultipartFile Profile_Photo;
    private MultipartFile Sslc_File;
    private MultipartFile Hsc_1_Year_File;
    private MultipartFile Hsc_2_Year_File;
    private MultipartFile Diploma_File;
    private byte[] ProfilePhotoContent;
    private byte[] SslcFileContent;
    private byte[] Hsc1YearFileContent;
    private byte[] Hsc2YearFileContent;
    private byte[] DiplomaFileContent;
    // Getters and setters
}