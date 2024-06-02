package erp.javaguides.erpbackend.mapper;
import erp.javaguides.erpbackend.dto.AcademicsDto;
import erp.javaguides.erpbackend.entity.Academics;



public class AcademicsMapper {
    public static AcademicsDto mapToAcademicsDto(Academics academics){

        return new AcademicsDto(
                academics.getEmailid(),
                academics.getRegister_No(),
                academics.getProgramme(),
                academics.getDiscipline(),
                academics.getAdmission_Number(),
                academics.getAcademic_Year(),
                academics.getSemester(),
                academics.getABC_Id(),
                academics.getUMIS_Id(),
                academics.getDate_Of_Admission(),
                academics.getCourse_Joined_Date(),
                academics.getCourse_Type(),
                academics.getRegulation(),
                academics.getFast_Track(),
                academics.getCGPA(),
                academics.getStudent_Status()
        );
    }

    public static Academics mapToAcademics(AcademicsDto academicsDto) {
        return new Academics(
                academicsDto.getEmailid(),
                academicsDto.getRegister_No(),
                academicsDto.getProgramme(),
                academicsDto.getDiscipline(),
                academicsDto.getAdmission_Number(),
                academicsDto.getAcademic_Year(),
                academicsDto.getSemester(),
                academicsDto.getABC_Id(),
                academicsDto.getUMIS_Id(),
                academicsDto.getDate_Of_Admission(),
                academicsDto.getCourse_Joined_Date(),
                academicsDto.getCourse_Type(),
                academicsDto.getRegulation(),
                academicsDto.getFast_Track(),
                academicsDto.getCGPA(),
                academicsDto.getStudent_Status()
        );
    }
}


