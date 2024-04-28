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
    @GetMapping("{emailid}")
    public ResponseEntity<StudentWithFilesDto> getStudentByEmailId(@PathVariable String emailid)throws IOException {
        // Call the service method to retrieve the student by email ID
        StudentWithFilesDto studentWithFilesDto = studentService.getStudentWithFilesDtoByEmailId(emailid);

        if (studentWithFilesDto != null) {
            return ResponseEntity.ok(studentWithFilesDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Build GetAllEmployees REST API
    @GetMapping
    public ResponseEntity<List<StudentDto>>getAllStudents(){
        List<StudentDto> students=studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    //Build Update Student REST API
    @PutMapping("{emailid}")
    public ResponseEntity<StudentDto>updateStudent(@PathVariable("emailid") String Email_Id,@RequestBody StudentDto updatedStudent){
       StudentDto studentDto=studentService.updateStudent(Email_Id,updatedStudent);
       return ResponseEntity.ok(studentDto);
    }

    //Build Delete Student REST API
    @DeleteMapping("{Email_Id}")
    public ResponseEntity<String> deleteStudent(@PathVariable("Email_Id") String Email_Id){
        studentService.deleteStudent(Email_Id);
        return ResponseEntity.ok("Student deleted successfully");
    }
}
