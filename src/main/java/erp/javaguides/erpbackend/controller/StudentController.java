package erp.javaguides.erpbackend.controller;

import erp.javaguides.erpbackend.dto.requestDto.StudentDto;
import erp.javaguides.erpbackend.dto.responseDto.StudentResponseDto;
import erp.javaguides.erpbackend.response.ApiResponse;
import erp.javaguides.erpbackend.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/student")
public class StudentController {
    private StudentService studentService;
    @PostMapping
    public ResponseEntity<String> createStudent(@ModelAttribute StudentDto studentDto) throws Exception {
        String savedStudent = studentService.createStudent(studentDto);
        return ResponseEntity.status(HttpStatus.OK).body(savedStudent);
    }

    @GetMapping("{register_No}")
    public ResponseEntity<StudentDto> getStudentByRegisterNo(@PathVariable String register_No) {
        StudentDto studentDto = studentService.getStudentByRegisterNo(register_No);
        return ResponseEntity.ok(studentDto);
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents(){
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

    @GetMapping("/filter/bySemesterAndDiscipline")
    public ResponseEntity<ApiResponse> getAllStudentsBySemesterAndDiscipline(
            @RequestParam String semester,
            @RequestParam String discipline) {
                try{
                    List<StudentResponseDto> students = studentService.getAllStudentsBySemesterAndDiscipline(semester, discipline);
                    if (students.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No students found", null));
                    }
                    return ResponseEntity.ok(new ApiResponse("Success", students));
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", null));
                }
    }

    @GetMapping("/filter/byBatchAndDiscipline")
    public ResponseEntity<ApiResponse> getAllStudentsByBatchAndDiscipline(
            @RequestParam String batch,
            @RequestParam String discipline) {
                try{
                    List<StudentResponseDto> students = studentService.getAllStudentsByBatchAndDiscipline(batch, discipline);
                    if (students.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No students found", null));
                    }
                    return ResponseEntity.ok(new ApiResponse("Success", students));
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", null));
                }
            }
    
    //Faculty neccessities
    

}
