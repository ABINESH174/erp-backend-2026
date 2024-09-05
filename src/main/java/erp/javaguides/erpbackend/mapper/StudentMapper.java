package erp.javaguides.erpbackend.mapper;

import erp.javaguides.erpbackend.dto.StudentWithFilesDto;
import erp.javaguides.erpbackend.entity.Student;



public class StudentMapper {

    public static StudentWithFilesDto mapToStudentWithFilesDto(Student student){
        return new StudentWithFilesDto(
        );
    }

    public static Student mapToStudentWithFilesDto(StudentWithFilesDto studentWithFilesDto){
        return new Student(
                studentWithFilesDto.getRegisterNo(),
                studentWithFilesDto.getFirstName(),
                studentWithFilesDto.getLastName(),
                studentWithFilesDto.getDateOfBirth(),
                studentWithFilesDto.getGender(),
                studentWithFilesDto.getAadharNumber(),
                studentWithFilesDto.getBloodGroup(),
                studentWithFilesDto.getNationality(),
                studentWithFilesDto.getReligion(),
                studentWithFilesDto.getCommunity(),
                studentWithFilesDto.getCaste(),
                studentWithFilesDto.getFathersName(),
                studentWithFilesDto.getFathersOccupation(),
                studentWithFilesDto.getFathersMobileNumber(),
                studentWithFilesDto.getMothersName(),
                studentWithFilesDto.getMothersOccupation(),
                studentWithFilesDto.getMothersMobileNumber(),
                studentWithFilesDto.getGuardiansName(),
                studentWithFilesDto.getGuardiansOccupation(),
                studentWithFilesDto.getGuardiansMobileNumber(),
                studentWithFilesDto.getParentsStatus(),
                studentWithFilesDto.getIncome(),
                studentWithFilesDto.getMaritalStatus(),
                null,
                null,
                studentWithFilesDto.getMobileNumber(),
                studentWithFilesDto.getEmailid(),
                studentWithFilesDto.getResidentialAddress(),
                studentWithFilesDto.getCommunicationAddress(),
                studentWithFilesDto.getHosteller(),
                studentWithFilesDto.getHostelType(),
                studentWithFilesDto.getBankName(),
                studentWithFilesDto.getIfscCode(),
                studentWithFilesDto.getBranchName(),
                studentWithFilesDto.getAccountNumber(),
                null,
                studentWithFilesDto.getSslc(),
                studentWithFilesDto.getHsc1Year(),
                studentWithFilesDto.getHsc2Year(),
                studentWithFilesDto.getDiploma(),
                null,
                null,
                null,
                null,
                studentWithFilesDto.getEmisNumber(),
                studentWithFilesDto.getFirstGraduate(),
                studentWithFilesDto.getSpecialCategory(),
                null,
                null,

                studentWithFilesDto.getProgramme(),
                studentWithFilesDto.getDiscipline(),
                studentWithFilesDto.getAdmissionNumber(),
                studentWithFilesDto.getAcademicYear(),
                studentWithFilesDto.getSemester(),
                studentWithFilesDto.getAbcId(),
                studentWithFilesDto.getUmisId(),
                studentWithFilesDto.getDateOfAdmission(),
                studentWithFilesDto.getCourseJoinedDate(),
                studentWithFilesDto.getCourseType(),
                studentWithFilesDto.getRegulation(),
                studentWithFilesDto.getFastTrack(),
                studentWithFilesDto.getCgpa(),
                studentWithFilesDto.getStudentStatus()
        );
    }
}