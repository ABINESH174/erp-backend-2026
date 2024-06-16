package erp.javaguides.erpbackend.controller;

import erp.javaguides.erpbackend.dto.AcademicsDto;
import erp.javaguides.erpbackend.entity.Academics;
import erp.javaguides.erpbackend.service.AcademicsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/academics")

public class AcademicsController {
    private AcademicsService academicsService;
    //Build Add Student REST API
    @PostMapping
    public ResponseEntity<AcademicsDto> createAcademics(@RequestBody AcademicsDto academicsDto) throws Exception {
        AcademicsDto savedAcademics=academicsService.createAcademics(academicsDto);
        return new ResponseEntity<>(savedAcademics, HttpStatus.CREATED);
    }

    //Build Get  Employee REST API
    @GetMapping("{register_No}")
    public ResponseEntity<AcademicsDto> getAcademicsById(@PathVariable("register_No") String register_No){
        AcademicsDto academicsDto=academicsService.getAcademicsById(register_No);
        return ResponseEntity.ok(academicsDto);
    }

    //Build GetAllEmployees REST API
    @GetMapping
    public ResponseEntity<List<AcademicsDto>>getAllAcademics(){
        List<AcademicsDto> academics=academicsService.getAllAcademics();
        return ResponseEntity.ok(academics);
    }

    //Build Update Student REST API
    @PutMapping("{register_No}")
    public ResponseEntity<AcademicsDto>updateAcademics(@PathVariable("register_No") String register_No,@RequestBody AcademicsDto updatedAcademics){
        AcademicsDto academicsDto=academicsService.updateAcademics(register_No,updatedAcademics);
        return ResponseEntity.ok(academicsDto);
    }

    //Build Delete Student REST API
    @DeleteMapping("{register_No}")
    public ResponseEntity<String> deleteStudent(@PathVariable("register_No") String register_No){
        academicsService.deleteAcademics(register_No);
        return ResponseEntity.ok("Student deleted successfully");
    }
}
