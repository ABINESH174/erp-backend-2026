package erp.javaguides.erpbackend.mapper;

import erp.javaguides.erpbackend.dto.StudentDto;
import erp.javaguides.erpbackend.entity.Student;



public class StudentMapper {
    public static StudentDto mapToStudentDto(Student student){
        return new StudentDto(
                student.getFirst_Name(),
                student.getLast_Name(),
                student.getDate_Of_Birth(),
                student.getGender(),
                student.getAadhar_Number(),
                student.getNationality(),
                student.getReligion(),
                student.getCommunity(),
                student.getCaste(),
                student.getFathers_Name(),
                student.getFathers_Occupation(),
                student.getFathers_Mobile_Number(),
                student.getMothers_Name(),
                student.getMothers_Occupation(),
                student.getMothers_Mobile_Number(),
                student.getGuardians_Name(),
                student.getGuardians_Occupation(),
                student.getGuardians_Mobile_Number(),
                student.getParents_Status(),
                student.getIncome(),
                student.getMarital_Status(),
                student.getProfile_Photo_Path(),
                student.getMobile_Number(),
                student.getEmail_Id(),
                student.getResidential_Address(),
                student.getCommunication_Address(),
                student.getHosteller(),
                student.getHostel_Type(),
                student.getBank_Name(),
                student.getIFSC_Code(),
                student.getBranch_Name(),
                student.getAccount_Number(),
                student.getSSLC(),
                student.getHSC_1_Year(),
                student.getHSC_2_Year(),
                student.getDiploma(),
                student.getSSLC_File_Path(),
                student.getHSC_1_Year_File_Path(),
                student.getHSC_2_Year_File_Path(),
                student.getDiploma_File_Path(),
                student.getEmis_Number(),
                student.getFirst_Graduate(),
                student.getSpecial_Category()
        );
    }

    public static Student mapToStudent(StudentDto studentDto){
        return new Student(
                studentDto.getFirst_Name(),
                studentDto.getLast_Name(),
                studentDto.getDate_Of_Birth(),
                studentDto.getGender(),
                studentDto.getAadhar_Number(),
                studentDto.getNationality(),
                studentDto.getReligion(),
                studentDto.getCommunity(),
                studentDto.getCaste(),
                studentDto.getFathers_Name(),
                studentDto.getFathers_Occupation(),
                studentDto.getFathers_Mobile_Number(),
                studentDto.getMothers_Name(),
                studentDto.getMothers_Occupation(),
                studentDto.getMothers_Mobile_Number(),
                studentDto.getGuardians_Name(),
                studentDto.getGuardians_Occupation(),
                studentDto.getGuardians_Mobile_Number(),
                studentDto.getParents_Status(),
                studentDto.getIncome(),
                studentDto.getMarital_Status(),
                studentDto.getProfile_Photo_Path(),
                studentDto.getMobile_Number(),
                studentDto.getEmail_Id(),
                studentDto.getResidential_Address(),
                studentDto.getCommunication_Address(),
                studentDto.getHosteller(),
                studentDto.getHostel_Type(),
                studentDto.getBank_Name(),
                studentDto.getIFSC_Code(),
                studentDto.getBranch_Name(),
                studentDto.getAccount_Number(),
                studentDto.getSSLC(),
                studentDto.getHSC_1_Year(),
                studentDto.getHSC_2_Year(),
                studentDto.getDiploma(),
                studentDto.getSSLC_File_Path(),
                studentDto.getHSC_1_Year_File_Path(),
                studentDto.getHSC_2_Year_File_Path(),
                studentDto.getDiploma_File_Path(),
                studentDto.getEmis_Number(),
                studentDto.getFirst_Graduate(),
                studentDto.getSpecial_Category()

        );
    }
}