package erp.javaguides.erpbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import erp.javaguides.erpbackend.enums.BonafideStatus;
import erp.javaguides.erpbackend.enums.BonafideType;
import erp.javaguides.erpbackend.enums.BonafideValidity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
// import jakarta.validation.constraints.NotEmpty;
// import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// import java.sql.Blob;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Bonafide")
public class Bonafide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bonafideId;

    @Column(length = 100)
    private String purpose;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private BonafideStatus bonafideStatus;

    private String date;

    // Bonafides can have two types 'B' and 'S', B-type bonafides are handled for some monetory benefits for students
    // S-type bonafides does not involve any monetory benefits like bus pass, internship...
    // These two are also considered as two of the many purposes dedicated for office bearers
    @Enumerated(value = EnumType.STRING)
    private BonafideType bonafideType;

    @Column(length = 20)
    private String academicYear;

    @Column(length = 100)
    private String companyName;

    @Enumerated(value = EnumType.STRING)
    private BonafideValidity bonafideValidity;

    //    @Column(name = "loan_bank_name")
    @Column(length = 100)
    private String bankNameForEducationalLoan;

    private String rejectionMessage;

    @Column
    private String welfareIdFilePath;

    @Column
    private String smartCardFilePath;

    @Column
    private String studentIdCardFilePath;

    @Column
    private String provisionalAllotmentFilePath;

    @Column
    private String aadharCardFilePath;

    @Column
    private String centralCommunityCertificateFilePath;

    @Column
    private String collegeFeeReceiptFilePath;

    @Column
    private String labourWelfareFilePath;

    @Column
    private String generatedBonafideFilePath;

    @ManyToOne(fetch=FetchType.LAZY, optional = false)
    @JoinColumn(name = "registerNo" , referencedColumnName = "registerNo", nullable = false)
    @JsonBackReference
    private Student student;


    public Bonafide(String purpose, BonafideStatus bonafideStatus, String date, BonafideType bonafideType, String academicYear , String companyName , String bankNameForEducationalLoan) {
        this.purpose = purpose;
        this.bonafideStatus = bonafideStatus;
        this.date = date;
        this.bonafideType = bonafideType;
        this.academicYear = academicYear;
        this.companyName = companyName;
        this.bankNameForEducationalLoan = bankNameForEducationalLoan;
    }
}
