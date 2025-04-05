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
    @Column(length = 20)
    private String registerNo;
    @Column(length = 50,nullable=false)
    private String firstName;
    @Column(length = 50,nullable = false)
    private String lastName;
    @Column(length = 20,nullable = false)
    private String dateOfBirth;
    @Column(length = 10)
    private Gender gender;
    @Column(length = 15,unique = true)
    @NotNull(message = "Aadhar number cannot be null")
    private String aadharNumber;
    @Column(length = 10)
    private String bloodGroup;
    @Column(length = 50)
    private String nationality;
    @Column(length = 50)
    private String Religion;
    @Column(length = 10)
    private String community;
    @Column(length = 50)
    private String caste;
    @Column(length = 50)
    private String fathersName;
    @Column(length = 50)
    private String fathersOccupation;
    @Column(length = 20)
    private String fathersMobileNumber;
    @Column(length = 50)
    private String mothersName;
    @Column(length = 50)
    private String mothersOccupation;
    @Column(length = 20)
    private String mothersMobileNumber;
    @Column(length = 50)
    private String guardiansName;
    @Column(length = 50)
    private String guardiansOccupation;
    @Column(length = 20)
    private String guardiansMobileNumber;
    @Column(length = 50)
    private String parentsStatus;
    @Column(length = 20)
    private String income;
    @Column(length = 50)
    private MaritalStatus maritalStatus;
    @Column(length = 150)
    private String communityCertificatePath;
    @Column(length = 150)
    private String profilePhotoPath;
    @Column(length = 20)
    private String mobileNumber;
    @Column(length = 50)
    private String emailid;
    @Column(length = 150)
    private String residentialAddress;
    @Column(length = 150)
    private String communicationAddress;
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Hosteller hosteller;
    private HostelType hostelType;
    @Column(length = 50)
    private String bankName;
    @Column(length = 50)
    private String ifscCode;
    @Column(length = 50)
    private String branchName;
    @Column(length = 50)
    private String accountNumber;
    @Column(length = 150)
    private String passbookPath;
    @Column(length = 10)
    private String sslc;
    @Column(length = 10)
    private String hsc1Year;
    @Column(length = 10)
    private String hsc2Year;
    @Column(length = 10)
    private String diploma;
    @Column(length = 150)
    private String sslcFilePath;
    @Column(length = 150)
    private String hsc1YearFilePath;
    @Column(length = 150)
    private String hsc2YearFilePath;
    @Column(length = 150)
    private String diplomaFilePath;
    @Column(length = 50)
    private String emisNumber;
    private FirstGraduate firstGraduate;
    @Column(length = 50)
    private String specialCategory;
    @Column(length = 150)
    private String firstGraduateFilePath;
    @Column(length = 150)
    private String specialCategoryFilePath;
    @Column(length = 100)
    private String programme;
    @Column(length = 50)
    private String discipline;
    @Column(length = 50)
    private String admissionNumber;
    @Column(length = 50)
    private String batch;
    @Column(length = 10)
    private String semester;
    @Column(length = 50)
    private String abcId;
    @Column(length = 50)
    private String umisId;
    @Column(length = 20)
    private String dateOfAdmission;
    @Column(length = 20)
    private String courseJoinedDate;
    private CourseType courseType;
    @Column(length = 10)
    private String regulation;
    @Column(length = 10)
    private String cgpa;
    private StudentStatus studentStatus;

}