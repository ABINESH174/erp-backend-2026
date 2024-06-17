package erp.javaguides.erpbackend.controller;

import erp.javaguides.erpbackend.dto.FacultyDto;
import erp.javaguides.erpbackend.service.AcademicsService;
import erp.javaguides.erpbackend.service.FacultyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/faculty")
@AllArgsConstructor
public class FacultyController {
    private final FacultyService facultyService;

    @PostMapping
    public ResponseEntity<FacultyDto> createFaculty(@RequestBody FacultyDto facultyDto) {
        try {
            FacultyDto savedFaculty = facultyService.createFaculty(facultyDto);
            return new ResponseEntity<>(savedFaculty, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
