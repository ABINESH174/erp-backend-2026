package erp.javaguides.erpbackend.controller;

import erp.javaguides.erpbackend.dto.FacultyDto;
import erp.javaguides.erpbackend.service.FacultyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/faculty")
@AllArgsConstructor
public class FacultyController {
    private final FacultyService facultyService;

    @PostMapping("/post")
    public ResponseEntity<FacultyDto> createFaculty(@RequestBody FacultyDto facultyDto) {
        try {
            FacultyDto savedFaculty = facultyService.createFaculty(facultyDto);
            return new ResponseEntity<>(savedFaculty, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/hod/{email}")
    public ResponseEntity<FacultyDto> getHodByEmail(@PathVariable String email)throws IOException {
        // Call the service method to retrieve the student by
        FacultyDto facultyDto = facultyService.getFacultyByEmail(email);
        return ResponseEntity.ok(facultyDto);
    }
    @GetMapping("/hod/student/{email}")
    public ResponseEntity<FacultyDto> getHodWithStudentEmail(@PathVariable String email)throws IOException {
        // Call the service method to retrieve the student by
        FacultyDto facultyDto = facultyService.getFacultyWithStudent(email);
        return ResponseEntity.ok(facultyDto);
    }
    @GetMapping("/hod/faculty/{email}")
    public ResponseEntity<FacultyDto> getHodWithFaculty(@PathVariable String email)throws IOException {
        // Call the service method to retrieve the student by
        FacultyDto facultyDto = facultyService.getFaculty(email);
        return ResponseEntity.ok(facultyDto);
    }

    @GetMapping("{email}")
    public ResponseEntity<FacultyDto> getFacultyByEmail(@PathVariable String email)throws IOException {
        // Call the service method to retrieve the student by
        FacultyDto facultyDto = facultyService.getFacultyByEmail(email);
        return ResponseEntity.ok(facultyDto);
    }
    @GetMapping("/filter")
    public ResponseEntity<FacultyDto> getFacultyByEmailAndClass(@RequestParam String email,
                                                                @RequestParam String className,
                                                                @RequestParam String batchYear )throws IOException {
        // Call the service method to retrieve the student by
        FacultyDto facultyDto = facultyService.getFacultyByEmail(email,className,batchYear);
        return ResponseEntity.ok(facultyDto);
    }
    @GetMapping("/getall")
    public ResponseEntity<List<FacultyDto>>getAllFaculties(){
        List<FacultyDto> faculties=facultyService.getAllFaculties();
        return ResponseEntity.ok(faculties);
    }
    @PutMapping("/update/{email}")
    public ResponseEntity<FacultyDto> updateFaculty(@PathVariable String email, @RequestBody FacultyDto facultyDto) {
        try {
            FacultyDto updatedFaculty = facultyService.updateFaculty(email, facultyDto);
            return new ResponseEntity<>(updatedFaculty, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
