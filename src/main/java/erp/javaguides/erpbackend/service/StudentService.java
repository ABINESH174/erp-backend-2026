package erp.javaguides.erpbackend.service;

import erp.javaguides.erpbackend.dto.StudentDto;
import erp.javaguides.erpbackend.dto.StudentWithFilesDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StudentService {
    String createStudentWithFilesDto(StudentWithFilesDto studentWithFilesDto) throws Exception;
    void createFolderIfNotExist(String folderPath);
    public StudentWithFilesDto getStudentWithFilesDtoByRegisterNo(String register_No) ;
    List<StudentWithFilesDto> getAllStudents();
    StudentDto updateStudent(String register_No, StudentDto updatedStudent);
    void deleteStudent(String register_No);
}
