package erp.javaguides.erpbackend.service.impl;

import erp.javaguides.erpbackend.dto.StudentDto;
import erp.javaguides.erpbackend.entity.Student;
import erp.javaguides.erpbackend.exception.ResourceNotFoundException;
import erp.javaguides.erpbackend.mapper.StudentMapper;
import erp.javaguides.erpbackend.repository.StudentRepository;
import erp.javaguides.erpbackend.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;
    @Override
    public StudentDto createStudent(StudentDto studentDto) {
        Student student = StudentMapper.mapToStudent(studentDto);
        Student savedStudent=studentRepository.save(student);
        return StudentMapper.mapToStudentDto(savedStudent);
    }

    @Override
    public StudentDto getStudentById(Long Id) {
        Student student = studentRepository.findById(Id)
                .orElseThrow(() ->new ResourceNotFoundException("Student is not exist with the given id:" + Id));
        return StudentMapper.mapToStudentDto(student);
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> student=studentRepository.findAll();
        return  student.stream().map((students) -> StudentMapper.mapToStudentDto(students))
                .collect(Collectors.toList());
    }

    @Override
    public StudentDto updateStudent(Long id, StudentDto updatedStudent) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("Student is not exist with the given id:" + id));

        Student updatedStudentObj=studentRepository.save(student);
        return StudentMapper.mapToStudentDto(updatedStudentObj);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("Student is not exist with the given id:" + id));
        studentRepository.deleteById(id);
    }
}
