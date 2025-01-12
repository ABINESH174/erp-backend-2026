package erp.javaguides.erpbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Bonafide")
public class Bonafide {
    @Id
    @Column(length = 100)
    private String registerNo;
    @Column(length = 50)
    private String purpose;
    @Column(length = 50)
    private String status;
    @Column(length = 150)
    private String welfareId;
    @Column(length = 150)
    private String smartCard;
    @Column(length = 150)
    private String studentIdCard;
    @Column(length = 150)
    private String provisionalAllotment;
    @Column(length = 150)
    private String aadharCard;
    @Column(length = 150)
    private String centralCommunityCertificate;
    @Column(length = 150)
    private String collegeFeeReceipt;
}
