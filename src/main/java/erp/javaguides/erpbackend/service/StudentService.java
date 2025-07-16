package erp.javaguides.erpbackend.service;

import erp.javaguides.erpbackend.dto.requestDto.StudentDto;
import erp.javaguides.erpbackend.dto.responseDto.StudentResponseDto;
import erp.javaguides.erpbackend.enums.PursuingYear;

import java.util.List;

public interface StudentService {
    String createStudent(StudentDto studentDto) throws Exception;
    void createFolderIfNotExist(String folderPath);
    StudentDto getStudentByRegisterNo(String register_No) ;
    List<StudentDto> getAllStudents();
    List<StudentDto> getAllStudentsByDiscipline(String discipline);
    String updateStudent(String registerNo, StudentDto studentDto) throws Exception;
    List<StudentResponseDto> getAllStudentsBySemesterAndDiscipline(String semester, String discipline);
    List<StudentResponseDto> getAllStudentsByBatchAndDiscipline(String batch, String discipline);

    List<StudentResponseDto> getAllStudentsByDisciplineAndClassSection(String discipline, String classSection);
    List<StudentResponseDto> getAllStudentsByDisciplineAndClassSectionAndBatch(String discipline, String classSection, String batch);
    
    // Display of students data for hod after clicking the card in the ui to make the batch available on the frontend
    List<StudentDto> getAllStudentsByDisciplineAndYear(String discipline, PursuingYear year);
    List<StudentDto> getAllStudentsByDisciplineAndYearAndClassSection(String discipline, PursuingYear year, String classSection);

    //Faculty neccessities

   /* void deleteStudent(String register_No);*/

   //faculty neccessities
}
