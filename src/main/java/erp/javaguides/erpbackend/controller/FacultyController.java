package erp.javaguides.erpbackend.controller;

import erp.javaguides.erpbackend.dto.requestDto.FacultyDto;
// import erp.javaguides.erpbackend.dto.responseDto.FacultyResponseDto;
import erp.javaguides.erpbackend.service.FacultyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/hod/{email}")
    public ResponseEntity<FacultyDto> getHodByEmail(@PathVariable String email) {
        FacultyDto facultyDto = facultyService.getFacultyByEmail(email);
        return ResponseEntity.ok(facultyDto);
    }
    @GetMapping("/hod/student/{email}")
    public ResponseEntity<FacultyDto> getHodWithStudentEmail(@PathVariable String email) {
        FacultyDto facultyDto = facultyService.getFacultyWithStudent(email);
        return ResponseEntity.ok(facultyDto);
    }
    @GetMapping("/hod/faculty/{email}")
    public ResponseEntity<FacultyDto> getHodWithFaculty(@PathVariable String email) {
        FacultyDto facultyDto = facultyService.getFaculty(email);
        return ResponseEntity.ok(facultyDto);
    }

    @GetMapping("{email}")
    public ResponseEntity<FacultyDto> getFacultyByEmail(@PathVariable String email) {
        FacultyDto facultyDto = facultyService.getFacultyByEmail(email);
        return ResponseEntity.ok(facultyDto);
    }
    @GetMapping("/filter")
    public ResponseEntity<FacultyDto> getFacultyByEmailAndClass(@RequestParam String email,
                                                                @RequestParam String className,
                                                                @RequestParam String batchYear ) {
        FacultyDto facultyDto = facultyService.getFacultyByEmail(email,className,batchYear);
        return ResponseEntity.ok(facultyDto);
    }
    @GetMapping("/get-all")
    public ResponseEntity<List<FacultyDto>>getAllFaculties(){
        List<FacultyDto> faculties=facultyService.getAllFaculties();
        return ResponseEntity.ok(faculties);
    }
    @PutMapping("/add-class/{email}")
    public ResponseEntity<FacultyDto> addClassFaculty(@PathVariable String email, @RequestBody FacultyDto facultyDto) {
        try {
            FacultyDto updatedFaculty = facultyService.addClassFaculty(email, facultyDto);
            return new ResponseEntity<>(updatedFaculty, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/remove-class/{email}")
    public ResponseEntity<FacultyDto> removeClassFaculty(@PathVariable String email, @RequestParam String index ) {
        try {
            FacultyDto updatedFaculty = facultyService.removeClassFaculty(email, index);
            return new ResponseEntity<>(updatedFaculty, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
