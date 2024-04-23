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
    public ResponseEntity<AcademicsDto> createAcademics(@RequestBody AcademicsDto academicsDto){
        AcademicsDto savedAcademics=academicsService.createAcademics(academicsDto);
        return new ResponseEntity<>(savedAcademics, HttpStatus.CREATED);
    }

    //Build Get  Employee REST API
    @GetMapping("{Email_Id}")
    public ResponseEntity<AcademicsDto> getAcademicsById(@PathVariable("Email_Id") String Email_Id){
        AcademicsDto academicsDto=academicsService.getAcademicsById(Email_Id);
        return ResponseEntity.ok(academicsDto);
    }

    //Build GetAllEmployees REST API
    @GetMapping
    public ResponseEntity<List<AcademicsDto>>getAllAcademics(){
        List<AcademicsDto> academics=academicsService.getAllAcademics();
        return ResponseEntity.ok(academics);
    }

    //Build Update Student REST API
    @PutMapping("{Email_Id}")
    public ResponseEntity<AcademicsDto>updateAcademics(@PathVariable("Email_Id") String Email_Id,@RequestBody AcademicsDto updatedAcademics){
        AcademicsDto academicsDto=academicsService.updateAcademics(Email_Id,updatedAcademics);
        return ResponseEntity.ok(academicsDto);
    }

    //Build Delete Student REST API
    @DeleteMapping("{Email_Id}")
    public ResponseEntity<String> deleteStudent(@PathVariable("Email_Id") String Email_Id){
        academicsService.deleteAcademics(Email_Id);
        return ResponseEntity.ok("Student deleted successfully");
    }
}
