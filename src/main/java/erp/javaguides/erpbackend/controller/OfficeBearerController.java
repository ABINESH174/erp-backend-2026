package erp.javaguides.erpbackend.controller;

import erp.javaguides.erpbackend.entity.OfficeBearer;
import erp.javaguides.erpbackend.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import erp.javaguides.erpbackend.service.OfficeBearerService;

import java.util.List;


@RestController
@RequestMapping("/api/office-bearer")
public class OfficeBearerController {
    
    @Autowired
    private OfficeBearerService officeBearerService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> saveOfficeBearer(@RequestBody OfficeBearer officeBearer) throws Exception {
        try {
            OfficeBearer savedOfficeBearer = officeBearerService.saveOfficeBearer(officeBearer);
            return ResponseEntity.ok(new ApiResponse("Office Bearer created successfully",savedOfficeBearer));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error creating office Bearer",null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllOfficeBearers() {
        try {
            List<OfficeBearer> allOfficeBearers = officeBearerService.getAllOfficeBearers();
            if(allOfficeBearers.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No Office Bearer Found",null));
            }
            return ResponseEntity.ok(new ApiResponse("All Office Bearer Retrived Successfully", allOfficeBearers));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error Fetching Office Bearers",null));
        }
    }
    

}
