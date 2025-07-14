package erp.javaguides.erpbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import erp.javaguides.erpbackend.dto.requestDto.HodRequestDto;
import erp.javaguides.erpbackend.dto.responseDto.BonafideResponseDto;
import erp.javaguides.erpbackend.dto.responseDto.HodResponseDto;
import erp.javaguides.erpbackend.response.ApiResponse;
import erp.javaguides.erpbackend.service.HodService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/hod")
public class HodController {

    @Autowired
    private HodService hodService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createHod(@RequestBody HodRequestDto hodRequestDto) {
        try {
            HodResponseDto createdHod = hodService.createHod(hodRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("HOD created successfully", createdHod));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error creating HOD", null));
        }
    }

    @GetMapping("/getHodById/{hodId}")
    public ResponseEntity<ApiResponse> getHodById(@PathVariable Long hodId) {
        try {
            HodResponseDto hodResponseDto = hodService.getHodById(hodId);
            return ResponseEntity.ok(new ApiResponse("HOD retrieved successfully", hodResponseDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error retrieving HOD", null));
        }
    }

    @GetMapping("/getHodByEmail/{email}")
    public ResponseEntity<ApiResponse> getHodByEmail(@PathVariable String email) {
        try {
            HodResponseDto hodResponseDto = hodService.getHodByEmail(email);
            return ResponseEntity.ok(new ApiResponse("HOD retrieved successfully", hodResponseDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error retrieving HOD", null));
        }
    }

    @GetMapping("/getHodByDiscipline")
    public ResponseEntity<ApiResponse> getHodByDiscipline(@RequestParam String discipline) {
        try {
            HodResponseDto hod = hodService.getHodByDiscipline(discipline);
            return ResponseEntity.ok(new ApiResponse("HODs retrieved successfully", hod));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error retrieving HODs", null));
        }
    }

    @GetMapping("/getAllHods")
    public ResponseEntity<ApiResponse> getAllHods() {
        try {
            List<HodResponseDto> hods = hodService.getAllHods();
            if (hods.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No HODs found", null));
            }
            return ResponseEntity.ok(new ApiResponse("HODs retrieved successfully", hods));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error retrieving HODs", null));
        }
    }

    @DeleteMapping("/deleteHod/{hodId}")
    public ResponseEntity<ApiResponse> deleteHod(@PathVariable Long hodId) {
        try {
            hodService.deleteHod(hodId);
            return ResponseEntity.ok(new ApiResponse("HOD deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error deleting HOD", null));
        }
    }

    @GetMapping("/getFacultyApprovedBonafidesByHodId/{hodId}")
    public ResponseEntity<ApiResponse> getFacultyApprovedBonafidesByHodId(@PathVariable Long hodId) {
        try {
            List<BonafideResponseDto> bonafides = hodService.getFacultyApprovedBonafidesByHodId(hodId);
            if (bonafides.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No Bonafides found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Bonafides retrieved successfully", bonafides));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error retrieving Bonafides", null));
        }
    }

    
}
