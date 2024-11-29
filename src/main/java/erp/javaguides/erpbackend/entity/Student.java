package erp.javaguides.erpbackend.entity;

import erp.javaguides.erpbackend.enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Student")
public class Student {
    @Id
    @Column(unique = true)
    @Size(max = 20)
    private String registerNo;
    @Column(nullable=false)
    @Size(max = 50)
    private String firstName;
    @Column(nullable = false)
    @Size(max = 50)
    private String lastName;
    @Column(nullable = false)
    @Size(max = 20)
    private String dateOfBirth;
    @Size(max = 10)
    private Gender gender;
    @Column(unique = true)
    @NotNull(message = "Aadhar number cannot be null")
    @Size(max = 12)
    private String aadharNumber;
    @Size(max = 10)
    private String bloodGroup;
    @Size(max = 50)
    private String nationality;
    @Size(max = 50)
    private String Religion;
    @Size(max = 10)
    private String community;
    @Size(max = 50)
    private String caste;
    @Size(max = 50)
    private String fathersName;
    @Size(max = 50)
    private String fathersOccupation;
    @Size(max = 20)
    private String fathersMobileNumber;
    @Size(max = 50)
    private String mothersName;
    @Size(max = 50)
    private String mothersOccupation;
    @Size(max = 20)
    private String mothersMobileNumber;
    @Size(max = 50)
    private String guardiansName;
    @Size(max = 50)
    private String guardiansOccupation;
    @Size(max = 20)
    private String guardiansMobileNumber;
    @Size(max = 50)
    private String parentsStatus;
    @Size(max = 20)
    private String income;
    @Size(max = 50)
    private MaritalStatus maritalStatus;
    @Size(max = 150)
    private String communityCertificatePath;
    @Size(max = 150)
    private String profilePhotoPath;
    @Size(max = 20)
    private String mobileNumber;
    @Size(max = 50)
    private String emailid;
    @Size(max = 150)
    private String residentialAddress;
    @Size(max = 150)
    private String communicationAddress;
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Hosteller hosteller;
    @Size(max = 10)
    private HostelType hostelType;
    @Size(max = 50)
    private String bankName;
    @Size(max = 50)
    private String ifscCode;
    @Size(max = 50)
    private String branchName;
    @Size(max = 50)
    private String accountNumber;
    @Size(max = 150)
    private String passbookPath;
    @Size(max = 50)
    private String sslc;
    @Size(max = 10)
    private String hsc1Year;
    @Size(max = 10)
    private String hsc2Year;
    @Size(max = 10)
    private String diploma;
    @Size(max = 50)
    private String sslcFilePath;
    @Size(max = 50)
    private String hsc1YearFilePath;
    @Size(max = 50)
    private String hsc2YearFilePath;
    @Size(max = 150)
    private String diplomaFilePath;
    @Size(max = 50,message = "Emis Number must be less than or equal to 50 characters")
    private String emisNumber;
    @Size(max = 10)
    private FirstGraduate firstGraduate;
    @Size(max = 50)
    private String specialCategory;
    @Size(max = 150)
    private String firstGraduateFilePath;
    @Size(max = 150)
    private String specialCategoryFilePath;
    @Size(max = 10)
    private String programme;
    @Size(max = 50)
    private String discipline;
    @Size(max = 50, message = "Admission Number must be less than or equal to 50 characters")
    private String admissionNumber;
    @Size(max = 50, message = "Batch must be less than or equal to 50 characters")
    private String batch;
    @Size(max = 10)
    private String semester;
    @Size(max = 50, message = "Abc Id must be less than or equal to 50 characters")
    private String abcId;
    @Size(max = 50, message = "Umis Id must be less than or equal to 50 characters")
    private String umisId;
    @Size(max = 50, message = "Date of Admission must be less than or equal to 50 characters")
    private String dateOfAdmission;
    @Size(max = 50, message = "Course Joined Date must be less than or equal to 50 characters")
    private String courseJoinedDate;
    @Size(max = 50)
    private CourseType courseType;
    @Size(max = 10, message = "Regulation must be less than or equal to 10 characters")
    private String regulation;
    @Size(max = 10, message = "CGPA must be less than or equal to 10 characters")
    private String cgpa;
    @Size(max = 50)
    private StudentStatus studentStatus;
    @Size(max = 150)
    private String labourWelfareId;
    @Size(max = 150)
    private String smartCard;
    @Size(max = 150)
    private String studentIdCard;
    @Size(max = 150)
    private String provisionalAllotment;
    @Size(max = 150)
    private String aadharCard;
    @Size(max = 150)
    private String centralCommunityCertificate;
    @Size(max = 150)
    private String collegeFeeReceipt;

}