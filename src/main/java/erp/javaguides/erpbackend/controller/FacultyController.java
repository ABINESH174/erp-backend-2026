package erp.javaguides.erpbackend.controller;

import erp.javaguides.erpbackend.dto.FacultyDto;
import erp.javaguides.erpbackend.entity.Faculty;
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
    @GetMapping("{email}")
    public ResponseEntity<Faculty> getFacultyByEmail(@PathVariable String email)throws IOException {
        // Call the service method to retrieve the student by
        Faculty faculty = facultyService.getFacultyByEmail(email);
        return ResponseEntity.ok(faculty);
    }
    @GetMapping("/getall")
    public ResponseEntity<List<FacultyDto>>getAllFaculties(){
        List<FacultyDto> faculties=facultyService.getAllFaculties();
        return ResponseEntity.ok(faculties);
    }

}
