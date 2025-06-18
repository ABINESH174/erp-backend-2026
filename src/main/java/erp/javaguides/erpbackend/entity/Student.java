package erp.javaguides.erpbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import erp.javaguides.erpbackend.enums.*;
import jakarta.persistence.*;
// import jakarta.validation.constraints.Size;
import lombok.*;
// import jakarta.validation.constraints.NotNull;

import java.util.List;

// import com.fasterxml.jackson.annotation.JsonBackReference;
// import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Student")
public class Student {
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long studentId;

    @Id
    @Column(length = 20, unique = true)
    private String registerNo;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(length = 20)
    private String dateOfBirth;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 10)
    private Gender gender;

    @Column(length = 12,unique = true)
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

    private String fatherName;

    @Column(length = 50)
    private String fatherOccupation;

    @Column(length = 15)
    private String fatherMobileNumber;

    private String motherName;

    @Column(length = 50)
    private String motherOccupation;

    @Column(length = 15)
    private String motherMobileNumber;

    private String guardianName;

    @Column(length = 50)
    private String guardianOccupation;

    @Column(length = 15)
    private String guardianMobileNumber;

    @Column(length = 50)
    private String parentStatus;

    @Column(length = 20)
    private String income;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 50)
    private MaritalStatus maritalStatus;

    @Column(length = 150)
    private String communityCertificatePath;

    @Column(length = 150)
    private String profilePhotoPath;

    @Column(length = 15)
    private String mobileNumber;

    private String emailId;

    private String residentialAddress;

    private String communicationAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Hosteller hosteller;

    @Enumerated(value = EnumType.STRING)
    private HostelType hostelType;

    @Column(length = 150)
    private String bankName;

    @Column(length = 100)
    private String ifscCode;

    @Column(length = 50)
    private String bankBranchName;

    @Column(length = 50)
    private String bankAccountNumber;

    @Column(length = 150)
    private String passbookPath;

    @Column(length = 10)
    private String sslcPercent;

    @Column(length = 10)
    private String hscFirstYearPercent;

    @Column(length = 10)
    private String hscSecondYearPercent;
    @Column(length = 10)
    private String diplomaPercent;
    @Column(length = 150)
    private String sslcFilePath;
    @Column(length = 150)
    private String hscFirstYearFilePath;
    @Column(length = 150)
    private String hscSecondYearFilePath;
    @Column(length = 150)
    private String diplomaFilePath;

    @Column(length = 50)
    private String emisNumber;

    @Enumerated(value = EnumType.STRING)
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

    @Enumerated(value = EnumType.STRING)
    private CourseType courseType;

    @Column(length = 10)
    private String regulation;

    @Column(length = 10)
    private String cgpa;

    @Column(nullable = false)
    private Boolean isGovtSchool;


    private StudentStatus studentStatus;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="faculty_id")
    @JsonBackReference  // to avoid infinite recursion
    private Faculty faculty;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Bonafide> bonafides;

    public void addBonafide(Bonafide bonafide){
        bonafide.setStudent(this);
        this.bonafides.add(bonafide);
    }


    public Student(String registerNo, String firstName, String lastName, String dateOfBirth, Gender gender, String aadharNumber, String bloodGroup, String nationality, String religion, String community, String caste, String fathersName, String fathersOccupation, String fathersMobileNumber, String mothersName, String mothersOccupation, String mothersMobileNumber, String guardiansName, String guardiansOccupation, String guardiansMobileNumber, String parentsStatus, String income, MaritalStatus maritalStatus, Object o, Object o1, String mobileNumber, String emailid, String residentialAddress, String communicationAddress, Hosteller hosteller, HostelType hostelType, String bankName, String ifscCode, String branchName, String accountNumber, Object o2, String sslc, String hsc1Year, String hsc2Year, String diploma, Object o3, Object o4, Object o5, Object o6, String emisNumber, FirstGraduate firstGraduate, String specialCategory, Object o7, Object o8, String programme, String discipline, String admissionNumber, String batch, String semester, String abcId, String umisId, String dateOfAdmission, String courseJoinedDate, CourseType courseType, String regulation, String cgpa,Boolean isGovtSchool, StudentStatus studentStatus) {

        this.registerNo = registerNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.aadharNumber = aadharNumber;
        this.bloodGroup = bloodGroup;
        this.nationality = nationality;
        this.Religion = religion;
        this.community = community;
        this.caste = caste;
        this.fatherName = fathersName;
        this.fatherOccupation = fathersOccupation;
        this.fatherMobileNumber = fathersMobileNumber;
        this.motherName = mothersName;
        this.motherOccupation = mothersOccupation;
        this.motherMobileNumber = mothersMobileNumber;
        this.guardianName = guardiansName;
        this.guardianOccupation = guardiansOccupation;
        this.guardianMobileNumber = guardiansMobileNumber;
        this.parentStatus = parentsStatus;
        this.income = income;
        this.maritalStatus = maritalStatus;
        this.mobileNumber = mobileNumber;
        this.emailId = emailid;
        this.residentialAddress = residentialAddress;
        this.communicationAddress = communicationAddress;
        this.hosteller = hosteller;
        this.hostelType = hostelType;
        this.bankName = bankName;
        this.ifscCode = ifscCode;
        this.bankBranchName = branchName;
        this.bankAccountNumber = accountNumber;
        this.sslcPercent = sslc;
        this.hscFirstYearPercent = hsc1Year;
        this.hscSecondYearPercent = hsc2Year;
        this.diplomaPercent = diploma;
        this.emisNumber = emisNumber;
        this.firstGraduate = firstGraduate;
        this.specialCategory = specialCategory;
        this.programme = programme;
        this.discipline = discipline;
        this.admissionNumber = admissionNumber;
        this.batch = batch;
        this.semester = semester;
        this.abcId = abcId;
        this.umisId = umisId;
        this.dateOfAdmission = dateOfAdmission;
        this.courseJoinedDate = courseJoinedDate;
        this.courseType = courseType;
        this.regulation = regulation;
        this.cgpa = cgpa;
        this.isGovtSchool=isGovtSchool;
        this.studentStatus = studentStatus;

    }
}