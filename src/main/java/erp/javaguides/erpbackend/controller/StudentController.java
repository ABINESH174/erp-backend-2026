package erp.javaguides.erpbackend.controller;

import erp.javaguides.erpbackend.dto.StudentDto;
import erp.javaguides.erpbackend.dto.StudentWithFilesDto;
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
    public ResponseEntity<String> createStudent(@ModelAttribute StudentWithFilesDto studentWithFilesDto)throws Exception {
        String savedStudent = studentService.createStudentWithFilesDto(studentWithFilesDto);
        return ResponseEntity.status(HttpStatus.OK).body(savedStudent);
    }

    //Build Get Student REST API
    @GetMapping("{register_No}")
    public ResponseEntity<StudentWithFilesDto> getStudentByRegisterNo(@PathVariable String register_No)throws IOException {
        // Call the service method to retrieve the student by
        StudentWithFilesDto studentWithFilesDto = studentService.getStudentWithFilesDtoByRegisterNo(register_No);

        if (studentWithFilesDto != null) {
            return ResponseEntity.ok(studentWithFilesDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Build GetAllEmployees REST API
    @GetMapping
    public ResponseEntity<List<StudentWithFilesDto>>getAllStudents(){
        List<StudentWithFilesDto> students=studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    //Build Update Student REST API
    @PutMapping("{register_No}")
    public ResponseEntity<StudentDto>updateStudent(@PathVariable("register_No") String register_No,@RequestBody StudentDto updatedStudent){
       StudentDto studentDto=studentService.updateStudent(register_No,updatedStudent);
       return ResponseEntity.ok(studentDto);
    }

    //Build Delete Student REST API
    @DeleteMapping("{register_No}")
    public ResponseEntity<String> deleteStudent(@PathVariable("register_No") String register_No){
        studentService.deleteStudent(register_No);
        return ResponseEntity.ok("Student deleted successfully");
    }
}
