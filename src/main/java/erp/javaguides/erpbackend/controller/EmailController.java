package erp.javaguides.erpbackend.controller;

import erp.javaguides.erpbackend.dto.requestDto.EmailRequestDto;
import erp.javaguides.erpbackend.entity.Bonafide;
import erp.javaguides.erpbackend.dto.ApiResponse;
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

    @PostMapping("/notify-faculty/{registerNo}")
    public ResponseEntity<ApiResponse> notifyFaculty(@PathVariable String registerNo){
        try{
            bonafideService.notifyFacultyOnSubmission(registerNo);
            return ResponseEntity.ok(new ApiResponse("Faculty notified Scuccessfully", null));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new ApiResponse("Failed to notify Faculty" + e.getMessage(),null));
        }
    }

    @PostMapping("/notify-approver")
    public ResponseEntity<ApiResponse> notifyApprover(@RequestBody EmailRequestDto emailRequestDto) {
        try {
            Bonafide bonafide = bonafideService.notifyNextApprover(
                    emailRequestDto.getBonafideId(),
                    emailRequestDto.getStatus(),
                    emailRequestDto.getRegisterNo()
            );


            String name = bonafide.getStudent().getFirstName() + " " + bonafide.getStudent().getLastName();
            String registerNo = bonafide.getStudent().getRegisterNo();
            String department = bonafide.getStudent().getDiscipline();

            String message;
            switch (emailRequestDto.getStatus().toUpperCase()) {
                case "FACULTY_APPROVED":
                    message = "Bonafide approved by Faculty for student " + name + " (" + registerNo + ") from " + department + " department.";
                    break;
                case "HOD_APPROVED":
                    message = "Bonafide approved by HOD for student " + name + " (" + registerNo + ") from " + department + " department.";
                    break;
                case "OB_APPROVED":
                    message = "Bonafide approved by Office Bearer for student " + name + " (" + registerNo + ") from " + department + " department.";
                    break;
                case "PRINCIPAL_APPROVED":
                    message = "Bonafide approved by Principal for student " + name + " (" + registerNo + ") from " + department + " department.";
                    break;
                default:
                    message = "Approver notified for student " + name + " (" + registerNo + ") from " + department + " department.";
            }

            return ResponseEntity.ok(new ApiResponse(message, null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ApiResponse("Failed to notify approver", null));
        }
    }

}
