package erp.javaguides.erpbackend.controller;

import erp.javaguides.erpbackend.dto.requestDto.FacultyRequestDto;
import erp.javaguides.erpbackend.dto.requestDto.StudentDto;
import erp.javaguides.erpbackend.dto.responseDto.BonafideResponseDto;
import erp.javaguides.erpbackend.dto.responseDto.FacultyResponseDto;
import erp.javaguides.erpbackend.enums.PursuingYear;
import erp.javaguides.erpbackend.exception.ResourceNotFoundException;
import erp.javaguides.erpbackend.dto.ApiResponse;
import erp.javaguides.erpbackend.service.FacultyService;
// import erp.javaguides.erpbackend.service.StudentService;
import jakarta.annotation.security.PermitAll;
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

    // private final StudentService studentService;

    @PermitAll
    @PostMapping("/post")
    public ResponseEntity<ApiResponse> createFaculty(@RequestBody FacultyRequestDto facultyDto) {
        try {
            FacultyResponseDto savedFaculty = facultyService.createFaculty(facultyDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Faculty created successfully", savedFaculty));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error creating faculty: " + e.getMessage(), null));
        }
    }
    // @GetMapping("/hod/{email}")
    // public ResponseEntity<FacultyDto> getHodByEmail(@PathVariable String email) {
    //     FacultyDto facultyDto = facultyService.getFacultyByEmail(email);
    //     return ResponseEntity.ok(facultyDto);
    // }
    // @GetMapping("/hod/student/{email}")
    // public ResponseEntity<FacultyDto> getHodWithStudentEmail(@PathVariable String email) {
    //     FacultyDto facultyDto = facultyService.getFacultyWithStudent(email);
    //     return ResponseEntity.ok(facultyDto);
    // }
    // @GetMapping("/hod/faculty/{email}")
    // public ResponseEntity<FacultyDto> getHodWithFaculty(@PathVariable String email) {
    //     FacultyDto facultyDto = facultyService.getFaculty(email);
    //     return ResponseEntity.ok(facultyDto);
    // }

    @GetMapping("{email}")
    public ResponseEntity<ApiResponse> getFacultyByEmail(@PathVariable String email) {
        try {
            FacultyResponseDto facultyResponseDto = facultyService.getFacultyByEmail(email);
            return ResponseEntity.ok(new ApiResponse("Faculty fetched successfully", facultyResponseDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error fetching faculty", null));
        }
    }
    // @GetMapping("/filter")
    // public ResponseEntity<FacultyDto> getFacultyByEmailAndClass(@RequestParam String email,
    //                                                             @RequestParam String className,
    //                                                             @RequestParam String batchYear ) {
    //     FacultyDto facultyDto = facultyService.getFacultyByEmail(email,className,batchYear);
    //     return ResponseEntity.ok(facultyDto);
    // }
    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse>getAllFaculties(){
        try{
            List<FacultyResponseDto> faculties = facultyService.getAllFaculties();
            if (faculties.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No faculties found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Faculties fetched successfully", faculties));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error fetching faculties", null)); 
        }
    }
    // @PutMapping("/add-class/{email}")
    // public ResponseEntity<ApiResponse> addClassFaculty(@PathVariable String email, @RequestBody FacultyDto facultyDto) {
    //     try {
    //         FacultyDto updatedFaculty = facultyService.addClassFaculty(email, facultyDto);
    //         return ResponseEntity.ok(new ApiResponse("Class added successfully", updatedFaculty));
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error adding class", null));
    //     }
    // }
    // @PutMapping("/remove-class/{email}")
    // public ResponseEntity<ApiResponse> removeClassFaculty(@PathVariable String email, @RequestParam String index ) {
    //     try {
    //         FacultyDto updatedFaculty = facultyService.removeClassFaculty(email, index);
    //         return ResponseEntity.ok(new ApiResponse("Class removed successfully", updatedFaculty));
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error removing class", null));
    //     }
    // }
    
    @GetMapping("/get-faculty/{facultyId}")
    public ResponseEntity<ApiResponse> getFacultyByFacultyId(@PathVariable Long facultyId) {
        try {
            FacultyResponseDto facultyResponseDto = facultyService.getFacultyByFacultyId(facultyId);
            return ResponseEntity.ok(new ApiResponse("Faculty fetched successfully", facultyResponseDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error fetching faculty", null));
        }
    }

    @GetMapping("/get-students/byFacultyId/{facultyId}")
    public ResponseEntity<ApiResponse> getAllStudentsByFacultyId(@PathVariable Long facultyId) {
        try {
            List<StudentDto> students = facultyService.getAllStudentsByFacultyId(facultyId);
            if (students.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No students found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", students));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", null));
        }
    }

    //Bonafide neccessities
    
    @GetMapping("/get-pending-bonafides/{facultyId}")
    public ResponseEntity<ApiResponse> getPendingBonafidesByFacultyId(@PathVariable Long facultyId) {
        try {
            List<BonafideResponseDto> bonafides = facultyService.getPendingBonafidesByFacultyId(facultyId);
            if (bonafides.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No pending bonafides found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Pending bonafides fetched successfully", bonafides));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error fetching pending bonafides", null));
        }
    }

    // hod neccessities

    @GetMapping("/discipline")
    public ResponseEntity<ApiResponse> getAllFacultiesByDiscipline(@RequestParam String discipline) {
        try {
            List<FacultyResponseDto> faculties = facultyService.getAllFacultiesByDiscipline(discipline);
            if (faculties.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No faculties found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Faculties fetched successfully", faculties));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error fetching faculties", null));
        }
    }
    
    @GetMapping("/get-faculty-by-discipline-and-batch")
    public ResponseEntity<ApiResponse> getFacultyByDisciplineAndBatch(@RequestParam String discipline, @RequestParam String handlingBatch) {
        try {
            FacultyResponseDto facultyResponseDto = facultyService.getFacultyByDisciplineAndBatch(discipline, handlingBatch);
            return ResponseEntity.ok(new ApiResponse("Faculty fetched successfully", facultyResponseDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error fetching faculty", null));
        }
    }
    
    @PutMapping("/update-assign/{batch}")
    public ResponseEntity<ApiResponse> assignFacultyWithStudent(@PathVariable String batch, @RequestParam String email, @RequestParam String discipline) throws ResourceNotFoundException{
        try {
            FacultyResponseDto updatedFaculty = facultyService.assignFacultyWithStudents(email, batch, discipline);
            return ResponseEntity.ok(new ApiResponse("Faculty updated successfully", updatedFaculty));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error updating faculty", null));
        }
    }

    @PutMapping("/update-dismiss")
    public ResponseEntity<ApiResponse> dismissFacultyWithStudent(@RequestParam String email){
        try {
            FacultyResponseDto updatedFaculty = facultyService.dismissFacultyWithStudents(email);
            return ResponseEntity.ok(new ApiResponse("Faculty updated successfully", updatedFaculty));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error updating faculty", null));
        }
    }

    @GetMapping("/unassigned-faculties/{discipline}")
    public ResponseEntity<ApiResponse> getAllUnassignedFaculties(@PathVariable String discipline) {
        try {
            List<FacultyResponseDto> faculties = facultyService.getAllUnassignedFaculties(discipline);
            if (faculties.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No unassigned faculties found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Unassigned faculties fetched successfully", faculties));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error fetching unassigned faculties", null));
        }
    }

    @PutMapping("/assign-students")
    public ResponseEntity<ApiResponse> assignFacultyUsingDisciplineYearAndClassSection(
                    @RequestParam String facultyEmail, 
                    @RequestParam String discipline, 
                    @RequestParam PursuingYear year, 
                    @RequestParam String classSection
                    ) {
        try {
            FacultyResponseDto facultyResponseDto = facultyService.assignFacultyUsingDisciplineYearAndClass(facultyEmail, discipline, year, classSection);
            return ResponseEntity.ok(new ApiResponse("Faculty updated with students successfully", facultyResponseDto));
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error updating faculty", null));
        }
    }

}
