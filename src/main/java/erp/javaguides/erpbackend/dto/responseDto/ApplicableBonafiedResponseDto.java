package erp.javaguides.erpbackend.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicableBonafiedResponseDto {
    private Boolean bcmbcPostMetricScholorship;
    private Boolean scstPostMetricScholorship;
    private Boolean tamilPudhalvanScholorship;
    private Boolean pudhumaiPennScholorship;

    private Boolean applyInternship;
    private Boolean applyEducationSupport;
    private Boolean applyPassport;
    private Boolean applyBusPass;
    private Boolean labourWelfareScholorship;
    private Boolean tailorWelfareScholorship;






}
