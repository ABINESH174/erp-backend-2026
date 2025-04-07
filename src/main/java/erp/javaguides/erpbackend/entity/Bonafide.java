package erp.javaguides.erpbackend.entity;

import erp.javaguides.erpbackend.enums.BonafideStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

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
    private String registerNo;

    @Column(length = 100)
    private String purpose;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private Student student;


    public Bonafide(Long bonafideId, String registerNo, String purpose, BonafideStatus bonafideStatus, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6) {
    }
}
