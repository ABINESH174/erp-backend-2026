package erp.javaguides.erpbackend.controller;

import erp.javaguides.erpbackend.dto.BonafideDto;
import erp.javaguides.erpbackend.service.BonafideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/bonafide")
public class BonafideController {

    @Autowired
    private BonafideService bonafideService;

    @PostMapping
    public ResponseEntity<BonafideDto> createBonafide(@RequestBody BonafideDto bonafideDto) throws Exception {
        BonafideDto createdBonafide = bonafideService.createBonafide(bonafideDto);
        return new ResponseEntity<>(createdBonafide, HttpStatus.CREATED);
    }

//    @GetMapping("/{registerNo}")
//    public ResponseEntity<BonafideDto> getBonafideByRegisterNo(@PathVariable String registerNo) {
//        BonafideDto bonafideDto = bonafideService.getBonafideByRegisterNo(registerNo);
//        return new ResponseEntity<>(bonafideDto, HttpStatus.OK);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<BonafideDto>> getAllBonafides() {
//        List<BonafideDto> bonafides = bonafideService.getAllBonafides();
//        return new ResponseEntity<>(bonafides, HttpStatus.OK);
//    }
//
//    @PutMapping("/{registerNo}")
//    public ResponseEntity<BonafideDto> updateBonafide(@PathVariable String registerNo, @RequestBody BonafideDto bonafideDto) {
//        BonafideDto updatedBonafide = bonafideService.updateBonafide(registerNo, bonafideDto);
//        return new ResponseEntity<>(updatedBonafide, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{registerNo}")
//    public ResponseEntity<Void> deleteBonafide(@PathVariable String registerNo) {
//        bonafideService.deleteBonafide(registerNo);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
}
