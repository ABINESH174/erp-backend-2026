package erp.javaguides.erpbackend.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicableBonafideResponseDto {
    private Boolean bcMbcDncPostMatricScholarship;
    private Boolean scStScaPostMatricScholarship;
    private Boolean tamilPudhalvanScholarship;
    private Boolean pudhumaiPennScholarship;

    private Boolean applyInternship;
    private Boolean applyEducationSupport;
    private Boolean applyPassport;
    private Boolean applyBusPass;

    private Boolean labourWelfareScholarship;
    private Boolean tailorWelfareScholarship;
    private Boolean farmerWelfareScholarship;






}
