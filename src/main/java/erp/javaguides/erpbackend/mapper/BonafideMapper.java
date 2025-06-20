package erp.javaguides.erpbackend.mapper;

import erp.javaguides.erpbackend.dto.requestDto.CreateBonafideRequestDto;
import erp.javaguides.erpbackend.dto.responseDto.BonafideResponseDto;
import erp.javaguides.erpbackend.entity.Bonafide;

public class BonafideMapper {

    public static BonafideResponseDto mapToBonafideResponseDto(Bonafide bonafide){
        return new BonafideResponseDto(
                bonafide.getBonafideId(),
                bonafide.getStudent().getFirstName()+bonafide.getStudent().getLastName(),
                bonafide.getStudent().getRegisterNo(),
                bonafide.getPurpose(),
                bonafide.getBonafideStatus(),
                bonafide.getDate(),
                bonafide.getAcademicYear(),

                bonafide.getStudent().getEmailId(),
                bonafide.getStudent().getSemester(),
                bonafide.getStudent().getDiscipline(),
                bonafide.getRejectionMessage(),
                bonafide.getBankNameForEducationalLoan(),
                bonafide.getCompanyName(),
                bonafide.getGeneratedBonafideFilePath(),
                bonafide.getStudent().getMobileNumber(),
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
                bonafideDto.getDate(),
                bonafideDto.getAcademicYear(),
                bonafideDto.getCompanyName(),
                bonafideDto.getBankNameForEducationalLoan()

        );
    }
}
