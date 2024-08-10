//package erp.javaguides.erpbackend.mapper;
//import erp.javaguides.erpbackend.dto.AcademicsDto;
//import erp.javaguides.erpbackend.entity.Academics;
//
//
//
//public class AcademicsMapper {
//    public static AcademicsDto mapToAcademicsDto(Academics academics){
//
//        return new AcademicsDto(
//                academics.getRegisterNo(),
//                academics.getProgramme(),
//                academics.getDiscipline(),
//                academics.getAdmissionNumber(),
//                academics.getAcademicYear(),
//                academics.getSemester(),
//                academics.getAbcId(),
//                academics.getUmisId(),
//                academics.getDateOfAdmission(),
//                academics.getCourseJoinedDate(),
//                academics.getCourseType(),
//                academics.getRegulation(),
//                academics.getFastTrack(),
//                academics.getCgpa(),
//                academics.getStudentStatus()
//        );
//    }
//
//    public static Academics mapToAcademics(AcademicsDto academicsDto) {
//        return new Academics(
//                academicsDto.getRegisterNo(),
//                academicsDto.getProgramme(),
//                academicsDto.getDiscipline(),
//                academicsDto.getAdmissionNumber(),
//                academicsDto.getAcademicYear(),
//                academicsDto.getSemester(),
//                academicsDto.getAbcId(),
//                academicsDto.getUmisId(),
//                academicsDto.getDateOfAdmission(),
//                academicsDto.getCourseJoinedDate(),
//                academicsDto.getCourseType(),
//                academicsDto.getRegulation(),
//                academicsDto.getFastTrack(),
//                academicsDto.getCgpa(),
//                academicsDto.getStudentStatus()
//        );
//    }
//}
//
//
