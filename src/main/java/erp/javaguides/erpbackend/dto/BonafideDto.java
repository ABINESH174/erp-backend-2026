package erp.javaguides.erpbackend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BonafideDto {
    @NotEmpty(message = "Register number cannot be empty")
    @Size(max = 20, message = "Register number cannot exceed 20 characters")
    private String registerNo;
    @NotEmpty(message = "Purpose cannot be empty")
    private String purpose;
    private String status;
    private String labourWelfareId;
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

