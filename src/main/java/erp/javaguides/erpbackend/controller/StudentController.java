package erp.javaguides.erpbackend.controller;

import erp.javaguides.erpbackend.dto.StudentDto;
import erp.javaguides.erpbackend.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/personal-form")
@AllArgsConstructor
@RestController
@RequestMapping("/api/student")
public class StudentController {
    private StudentService studentService;
    //Build Add Student REST API
    @PostMapping
    public ResponseEntity<String> createStudent(@ModelAttribute StudentDto studentDto)throws Exception {
        String savedStudent = studentService.createStudent(studentDto);
        return ResponseEntity.status(HttpStatus.OK).body(savedStudent);
    }

    //Build Get Student REST API
    @GetMapping("{register_No}")
    public ResponseEntity<StudentDto> getStudentByRegisterNo(@PathVariable String register_No)throws IOException {
        // Call the service method to retrieve the student by
        StudentDto studentDto = studentService.getStudentByRegisterNo(register_No);

        return ResponseEntity.ok(studentDto);
    }

    //Build GetAllEmployees REST API
    @GetMapping
    public ResponseEntity<List<StudentDto>>getAllStudents(){
        List<StudentDto> students=studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }


}
