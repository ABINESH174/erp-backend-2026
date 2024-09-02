package erp.javaguides.erpbackend.service;

import erp.javaguides.erpbackend.dto.StudentWithFilesDto;

import java.util.List;

public interface StudentService {
    String createStudent(StudentWithFilesDto studentWithFilesDto) throws Exception;
    void createFolderIfNotExist(String folderPath);
    StudentWithFilesDto getStudentByRegisterNo(String register_No) ;
    List<StudentWithFilesDto> getAllStudents();
    List<StudentWithFilesDto> getAllStudentsByDiscipline(String discipline);
   /* StudentDto updateStudent(String register_No, StudentDto updatedStudent);
    void deleteStudent(String register_No);*/
}
