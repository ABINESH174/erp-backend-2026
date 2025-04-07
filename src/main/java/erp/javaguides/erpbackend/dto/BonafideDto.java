package erp.javaguides.erpbackend.dto;

import erp.javaguides.erpbackend.enums.BonafideStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BonafideDto {

    private Long bonafideId;

    private String registerNo;

    private String purpose;
    private BonafideStatus bonafideStatus;
    private String welfareId;
    private String smartCard;
    private String studentIdCard;
    private String provisionalAllotment;
    private String aadharCard;
    private String centralCommunityCertificate;
    private String collegeFeeReceipt;
    private byte[] labourWelfareIdContent;
    private byte[] smartCardContent;
    private byte[] studentIdCardContent;
    private byte[] provisionalAllotmentContent;
    private byte[] aadharCardContent;
    private byte[] centralCommunityCertificateContent;
    private byte[] collegeFeeReceiptContent;
}

