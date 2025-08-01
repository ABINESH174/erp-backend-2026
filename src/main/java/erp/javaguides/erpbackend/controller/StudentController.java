package erp.javaguides.erpbackend.controller;

import erp.javaguides.erpbackend.dto.requestDto.StudentDto;
import erp.javaguides.erpbackend.dto.responseDto.StudentResponseDto;
import erp.javaguides.erpbackend.enums.PursuingYear;
import erp.javaguides.erpbackend.response.ApiResponse;
import erp.javaguides.erpbackend.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
        try{
            String createdStudent = studentService.createStudent(studentDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating student: " + e.getMessage());
        }
    }

    @GetMapping("{register_No}")
    public ResponseEntity<StudentDto> getStudentByRegisterNo(@PathVariable String register_No) {
        try{
            StudentDto studentDto = studentService.getStudentByRegisterNo(register_No);
            if (studentDto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(studentDto);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
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

            // Hod neccessities
            @GetMapping("/get/discipline/year")
            public ResponseEntity<ApiResponse> getAllStudentsByDisciplineAndYear(@RequestParam String discipline, @RequestParam PursuingYear year) {
                try {
                    List<StudentDto> students = studentService.getAllStudentsByDisciplineAndYear(discipline, year);
                    if(students.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No students found",null));
                    }
                    return ResponseEntity.ok(new ApiResponse("Success in retrieving Students",students));
                } catch(Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error retrieving students", null));
                }
            }

            @GetMapping("/get/discipline/year/section")
            public ResponseEntity<ApiResponse> getAllStudentsByDisciplineAndYearAndClassSection(
                    @RequestParam String discipline, 
                    @RequestParam PursuingYear year, 
                    @RequestParam String classSection
                    ) {
                try {
                    List<StudentDto> students = studentService.getAllStudentsByDisciplineAndYearAndClassSection(discipline, year, classSection);
                    if (students.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No students found", null));
                    }
                    return ResponseEntity.ok(new ApiResponse("Students retrieved successfully", students));
                } catch(Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error retrieving students",null));
                }
            }
    
    //Faculty neccessities
    

}
