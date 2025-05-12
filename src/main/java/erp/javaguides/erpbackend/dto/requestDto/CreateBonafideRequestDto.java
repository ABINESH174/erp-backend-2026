package erp.javaguides.erpbackend.dto.requestDto;

import org.springframework.web.multipart.MultipartFile;

import erp.javaguides.erpbackend.enums.BonafideStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBonafideRequestDto {
    private String registerNo;

    private String date;
    private String purpose;
    private BonafideStatus bonafideStatus;
    private String welfareId;

    private MultipartFile smartCardFile;
    private MultipartFile studentIdCardFile;
    private MultipartFile provisionalAllotmentFile;
    private MultipartFile aadharCardFile;
    private MultipartFile centralCommunityCertificateFile;
    private MultipartFile collegeFeeReceiptFile;
    private MultipartFile labourWelfareFile;

    // private String smartCard;
    // private String studentIdCard;
    // private String provisionalAllotment;
    // private String aadharCard;
    // private String centralCommunityCertificate;
    // private String collegeFeeReceipt;
    // private byte[] labourWelfareIdContent;
    // private byte[] smartCardContent;
    // private byte[] studentIdCardContent;
    // private byte[] provisionalAllotmentContent;
    // private byte[] aadharCardContent;
    // private byte[] centralCommunityCertificateContent;
    // private byte[] collegeFeeReceiptContent;
}
