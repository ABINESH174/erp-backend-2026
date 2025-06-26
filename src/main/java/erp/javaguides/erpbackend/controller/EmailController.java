package erp.javaguides.erpbackend.controller;

import erp.javaguides.erpbackend.dto.requestDto.EmailRequestDto;
import erp.javaguides.erpbackend.entity.Bonafide;
import erp.javaguides.erpbackend.exception.ResourceNotFoundException;
import erp.javaguides.erpbackend.repository.BonafideRepository;
import erp.javaguides.erpbackend.response.ApiResponse;
import erp.javaguides.erpbackend.service.BonafideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@CrossOrigin
@RequiredArgsConstructor
public class EmailController {
    private final BonafideService bonafideService;
    private  final BonafideRepository bonafideRepository;

    @PostMapping("/notify-faculty/{registerNo}")
    public ResponseEntity<ApiResponse> notifyFaculty(@PathVariable String registerNo){
        try{
            bonafideService.notifyFacultyOnSubmission(registerNo);
            return ResponseEntity.ok(new ApiResponse("Faculty notified Scuccessfully", null));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(new ApiResponse("Failed to notify Faculty",null));
        }
    }

    @PostMapping("/notify-approver")
    public ResponseEntity<ApiResponse> notifyApprover(@RequestBody EmailRequestDto emailRequestDto){
        try{
            Long bonafideId = emailRequestDto.getBonafideId();
            String registerNo = emailRequestDto.getRegisterNo();
            String status = emailRequestDto.getStatus();
            Bonafide bonafide = bonafideRepository.findById(bonafideId).orElseThrow(()->new ResourceNotFoundException("Bonafide Not Found with id: " + bonafideId));
            bonafideService.notifyNextApprover(bonafide , status , registerNo);
            return ResponseEntity.ok(new ApiResponse("Approver notified successfully",null));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(new ApiResponse("Failed to notify Faculty",null));
        }
    }
}
