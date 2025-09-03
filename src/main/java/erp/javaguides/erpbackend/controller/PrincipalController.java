package erp.javaguides.erpbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import erp.javaguides.erpbackend.dto.responseDto.BonafideResponseDto;
import erp.javaguides.erpbackend.entity.Principal;
import erp.javaguides.erpbackend.dto.ApiResponse;
import erp.javaguides.erpbackend.service.PrincipalService;


@RestController
@RequestMapping("/api/principal")
public class PrincipalController {
    
    @Autowired
    private PrincipalService principalService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createPrincipal(@RequestBody Principal principal){
        try {
            Principal createdPrincipal=principalService.savePrincipal(principal);
            return ResponseEntity.ok(new ApiResponse("Principal created successfully",createdPrincipal));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error creating principal",null));
        }
    }

    @GetMapping("/getPrincipalById/{principalId}")
    public ResponseEntity<ApiResponse> getPrincipalById(@PathVariable Long principalId){
        try {
            Principal principal = principalService.getPrincipalById(principalId);
            return ResponseEntity.ok(new ApiResponse("Principal retrieved successfully", principal));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error creating Principal", null));
        }
    }

    @GetMapping("/getPrincipalByEmail/{email}")
    public ResponseEntity<ApiResponse> getPrincipalByEmail(@PathVariable String email){
        try {
            Principal principal = principalService.getPrincipalByEmail(email);
            return ResponseEntity.ok(new ApiResponse("Principal retrieved successfully", principal));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error creating Principal", null));
        }
    }

    @GetMapping("/officeBearersApprovedBonafides")
    public ResponseEntity<ApiResponse> getOfficeBearersApprovedBonafides() {
        try {
            List<BonafideResponseDto> bonafideResponseDtos = principalService.getOfficeBearersApprovedBonafides();
            if (bonafideResponseDtos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("No Bonafides found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Bonafides retrieved successfully", bonafideResponseDtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error retrieving Bonafides", null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllprincipals() {
        try {
            List<Principal> allPrincipal = principalService.getAllPrincipals();
            if (allPrincipal.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No Principal Found", null));
            }
            return ResponseEntity.ok(new ApiResponse("All Principals Retrived Successfully", allPrincipal));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error Fetching Principal", null));
        }
    }

}
