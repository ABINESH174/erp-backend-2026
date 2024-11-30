package erp.javaguides.erpbackend.dto;


import erp.javaguides.erpbackend.enums.*;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import erp.javaguides.erpbackend.enums.Gender;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
    @Id
    @Column(unique = true)
    private String registerNo;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private Gender gender;
    @Column(unique = true)
    private String aadharNumber;
    private String bloodGroup;
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
    private MaritalStatus maritalStatus;
    private String CommunityCertificate;
    private String profilePhoto;
    private byte[] communityCertificateContent;
    private byte[] profilePhotoContent;
    private String mobileNumber;
    private String emailid;
    private String residentialAddress;
    private String communicationAddress;
    private Hosteller hosteller;
    private HostelType hostelType;
    private String bankName;
    private String ifscCode;
    private String branchName;
    private String accountNumber;
    private String Passbook;
    private byte[] passbookcontent;
    private String sslc;
    private String hsc1Year;
    private String hsc2Year;
    private String diploma;
    private String sslcFile;
    private String hsc1YearFile;
    private String hsc2YearFile;
    private String diplomaFile;
    private byte[] sslcFileContent;
    private byte[] hsc1YearFileContent;
    private byte[] hsc2YearFileContent;
    private byte[] diplomaFileContent;
    private String emisNumber;
    private FirstGraduate firstGraduate;
    private String specialCategory;
    private String firstGraduateFile;
    private String specialCategoryFile;
    private byte[] firstGraduateFileContent;
    private byte[] specialCategoryFileContent;
    private String programme;
    private String discipline;
    private String admissionNumber;
    private String batch;
    private String semester;
    private String abcId;
    private String umisId;
    private String dateOfAdmission;
    private String courseJoinedDate;
    private CourseType courseType;
    private String regulation;
    private String cgpa;
    private StudentStatus studentStatus;

}