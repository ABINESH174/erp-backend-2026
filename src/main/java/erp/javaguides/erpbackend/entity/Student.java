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
    private String registerNo;
    @Column(nullable=false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String dateOfBirth;
    private String gender;
    @Column(unique = true)
    private String aadharNumber;
    private String nationality;
    private String Religion;
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
    private String profilePhotoPath;
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
    private String passbookPath;
    private String sslc;
    private String hsc1Year;
    private String hsc2Year;
    private String diploma;
    private String sslcFilePath;
    private String hsc1YearFilePath;
    private String hsc2YearFilePath;
    private String diplomaFilePath;
    private String emisNumber;
    private String firstGraduate;
    private String specialCategory;
}

