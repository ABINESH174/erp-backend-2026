package erp.javaguides.erpbackend.controller;

import erp.javaguides.erpbackend.dto.StudentDto;
import erp.javaguides.erpbackend.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/student")
public class StudentController {
    private StudentService studentService;
    @PostMapping
    public ResponseEntity<String> createStudent(@ModelAttribute StudentDto studentDto)throws Exception {
        String savedStudent = studentService.createStudent(studentDto);
        return ResponseEntity.status(HttpStatus.OK).body(savedStudent);
    }
    @PostMapping("/{registerNo}/update-files")
    public ResponseEntity<String> updateStudentFiles(@PathVariable String registerNo, @RequestBody StudentDto updatedFilesDto) {
        try {
            String message = studentService.updateStudentFiles(registerNo, updatedFilesDto);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating files: " + e.getMessage());
        }
    }

    @GetMapping("{register_No}")
    public ResponseEntity<StudentDto> getStudentByRegisterNo(@PathVariable String register_No) {
        StudentDto studentDto = studentService.getStudentByRegisterNo(register_No);

        return ResponseEntity.ok(studentDto);
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>>getAllStudents(){
        List<StudentDto> students=studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @PutMapping("update/{registerNo}")
    public ResponseEntity<String> updateStudent(
            @PathVariable String registerNo,
            @ModelAttribute StudentDto studentDto) throws Exception {
        String updatedStudent = studentService.updateStudent(registerNo, studentDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedStudent);
    }

}
