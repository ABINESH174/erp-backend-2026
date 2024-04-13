package erp.javaguides.erpbackend.service;

import erp.javaguides.erpbackend.dto.StudentDto;

import java.util.List;

public interface StudentService {
    StudentDto createStudent(StudentDto studentDto);
    StudentDto getStudentById(Long Id);
    List<StudentDto> getAllStudents();
    StudentDto updateStudent(Long id, StudentDto updatedStudent);
    void deleteStudent(Long id);
}
