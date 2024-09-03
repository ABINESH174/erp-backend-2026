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
    private String registerNo;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String gender;
    @Column(unique = true)
    private String aadharNumber;
    private String nationality;
    private String religion;
    private String community;
    private String caste;
    private String fathersName;
    private String fathersOccupation;
    private String fathersMobileNumber;
    private String mothersName;
    private String mothersOccupation;
    private String mothersMobileNumber;
    private String guardiansName;
    private String guardiansOccupation;
    private String guardiansMobileNumber;
    private String parentsStatus;
    private String income;
    private String maritalStatus;
    private String mobileNumber;
    private String emailid;
    private String residentialAddress;
    private String communicationAddress;
    private String hosteller;
    private String hostelType;
    private String bankName;
    private String ifscCode;
    private String branchName;
    private String accountNumber;
    private String sslc;
    private String hsc1Year;
    private String hsc2Year;
    private String diploma;
    private String emisNumber;
    private String firstGraduate;
    private String specialCategory;
    //private String profilePhoto;
    private String Passbook;
    private String sslcFile;
    private String hsc1YearFile;
    private String hsc2YearFile;
    private String diplomaFile;
    private byte[] profilePhotoContent;
    private byte[] passbookcontent;
    private byte[] sslcFileContent;
    private byte[] hsc1YearFileContent;
    private byte[] hsc2YearFileContent;
    private byte[] diplomaFileContent;
    private String programme;
    private String discipline;
    private String admissionNumber;
    private String academicYear;
    private String semester;
    private String abcId;
    private String umisId;
    private String dateOfAdmission;
    private String courseJoinedDate;
    private String courseType;
    private String regulation;
    private String fastTrack;
    private String cgpa;
    private String studentStatus;
    // Getters and setters
}