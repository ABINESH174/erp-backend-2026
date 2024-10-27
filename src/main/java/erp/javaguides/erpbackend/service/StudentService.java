package erp.javaguides.erpbackend.service;

import erp.javaguides.erpbackend.dto.StudentDto;

import java.util.List;

public interface StudentService {
    String createStudent(StudentDto studentDto) throws Exception;
    void createFolderIfNotExist(String folderPath);
    StudentDto getStudentByRegisterNo(String register_No) ;
    List<StudentDto> getAllStudents();
    List<StudentDto> getAllStudentsByDiscipline(String discipline);
    String updateStudent(String registerNo, StudentDto studentDto) throws Exception;
   /* void deleteStudent(String register_No);*/
}
