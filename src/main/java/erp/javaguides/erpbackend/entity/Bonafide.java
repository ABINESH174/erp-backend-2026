package erp.javaguides.erpbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import erp.javaguides.erpbackend.enums.BonafideStatus;
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

    @Column(length = 150)
    private String welfareIdFilePath;

    @Column(length = 150)
    private String smartCardFilePath;

    @Column(length = 150)
    private String studentIdCardFilePath;

    @Column(length = 150)
    private String provisionalAllotmentFilePath;

    @Column(length = 150)
    private String aadharCardFilePath;

    @Column(length = 150)
    private String centralCommunityCertificateFilePath;

    @Column(length = 150)
    private String collegeFeeReceiptFilePath;

    @Column(length = 150)
    private String labourWelfareFilePath;

    @ManyToOne(fetch=FetchType.LAZY, optional = false)
    @JoinColumn(name = "registerNo" , referencedColumnName = "registerNo", nullable = false)
    @JsonBackReference
    private Student student;


    public Bonafide(String purpose, BonafideStatus bonafideStatus, String date) {
        this.purpose = purpose;
        this.bonafideStatus = bonafideStatus;
        this.date = date;
    }
}
