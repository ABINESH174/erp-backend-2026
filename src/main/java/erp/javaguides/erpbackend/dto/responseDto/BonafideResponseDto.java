package erp.javaguides.erpbackend.dto.responseDto;

import erp.javaguides.erpbackend.enums.BonafideStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BonafideResponseDto {
    private Long bonafideId;
    private String registerNo;
    private String purpose;
    private BonafideStatus bonafideStatus;
    private String date;
    
    private String welfareIdFilePath;
    private String smartCardFilePath;
    private String studentIdCardFilePath;
    private String provisionalAllotmentFilePath;
    private String aadharCardFilePath;
    private String centralCommunityCertificateFilePath;
    private String collegeFeeReceiptFilePath;
}
