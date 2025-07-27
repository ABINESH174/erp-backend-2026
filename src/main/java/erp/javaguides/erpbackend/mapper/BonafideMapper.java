package erp.javaguides.erpbackend.mapper;

import erp.javaguides.erpbackend.dto.requestDto.CreateBonafideRequestDto;
import erp.javaguides.erpbackend.dto.responseDto.BonafideResponseDto;
import erp.javaguides.erpbackend.entity.Bonafide;
import erp.javaguides.erpbackend.utility.UtilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BonafideMapper {

    @Autowired
    private static UtilityService utilityService;

    public static BonafideResponseDto mapToBonafideResponseDto(Bonafide bonafide){
        return new BonafideResponseDto(
                bonafide.getBonafideId(),
                bonafide.getStudent().getFirstName()+bonafide.getStudent().getLastName(),
                bonafide.getStudent().getRegisterNo(),
                bonafide.getPurpose(),
                bonafide.getBonafideStatus(),
                bonafide.getDate(),
                bonafide.getAcademicYear(),
                bonafide.getCompanyName(),
                bonafide.getBankNameForEducationalLoan(),
                bonafide.getGeneratedBonafideFilePath(),
                bonafide.getStudent().getMobileNumber(),
                bonafide.getStudent().getEmailId(),
                bonafide.getStudent().getSemester(),
                bonafide.getStudent().getDiscipline(),
                bonafide.getRejectionMessage(),
                bonafide.getWelfareIdFilePath(),
                bonafide.getSmartCardFilePath(),
                bonafide.getStudentIdCardFilePath(),
                bonafide.getProvisionalAllotmentFilePath(),
                bonafide.getAadharCardFilePath(),
                bonafide.getCentralCommunityCertificateFilePath(),
                bonafide.getCollegeFeeReceiptFilePath()
        );
    }

    public static Bonafide mapToBonafide(CreateBonafideRequestDto bonafideDto){
        return new Bonafide(
                bonafideDto.getPurpose(),
                bonafideDto.getBonafideStatus(),
                UtilityService.yearMonthDayToDayMonthYear(bonafideDto.getDate()),
                bonafideDto.getAcademicYear(),
                bonafideDto.getCompanyName(),
                bonafideDto.getBankNameForEducationalLoan()
        );
    }
}
