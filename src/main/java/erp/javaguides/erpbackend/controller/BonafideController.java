package erp.javaguides.erpbackend.controller;

import erp.javaguides.erpbackend.dto.requestDto.CreateBonafideRequestDto;
import erp.javaguides.erpbackend.dto.responseDto.BonafideResponseDto;
import erp.javaguides.erpbackend.exception.ResourceNotFoundException;
import erp.javaguides.erpbackend.response.ApiResponse;
import erp.javaguides.erpbackend.service.BonafideService;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/api/bonafide")
public class BonafideController {

    @Autowired
    private BonafideService bonafideService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createBonafide(@ModelAttribute CreateBonafideRequestDto requestDto) throws ResourceNotFoundException{
        try {
            BonafideResponseDto createdBonafide = bonafideService.saveBonafide(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Bonafide created successfully",createdBonafide));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Failed to create Bonafide",null));

        }
    }



    @GetMapping("/getBonafideById/{bonafideId}")
    public ResponseEntity<ApiResponse> getBonafideById(@PathVariable Long bonafideId) {
        try {
            BonafideResponseDto bonafideResponseDto = bonafideService.getBonafideById(bonafideId);
            return ResponseEntity.ok(new ApiResponse("Bonafide retrieved successfully", bonafideResponseDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Failed to retrieve Bonafide", null));
        }
    }

    @GetMapping("/getBonafideByIdAndRegisterNo")
    public ResponseEntity<ApiResponse> getBonafideByIdAndRegisterNo(@RequestParam Long bonafideId, @RequestParam String registerNo) {
        try {
            BonafideResponseDto bonafideResponseDto = bonafideService.getBonafideByIdAndRegisterNo(bonafideId, registerNo);
            return ResponseEntity.ok(new ApiResponse("Bonafide retrieved successfully", bonafideResponseDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Failed to retrieve Bonafide", null));
        }
    }

    @GetMapping("/getAllBonafidesByRegisterNo")
    public ResponseEntity<ApiResponse> getAllBonafidesByRegisterNo(@RequestParam String registerNo) {
        try {
            List<BonafideResponseDto> bonafideResponseDtos = bonafideService.getAllBonafidesByRegisterNo(registerNo);
            if (bonafideResponseDtos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No Bonafides found for the given register number", null));
            }
            return ResponseEntity.ok(new ApiResponse("Bonafides retrieved successfully", bonafideResponseDtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Failed to retrieve Bonafides", null));
        }
    }

    @GetMapping("/getAllBonafidesByRegisterNoAndBonafideStatus")
    public ResponseEntity<ApiResponse> getAllBonafidesByRegisterNoAndBonafideStatus(@RequestParam String registerNo, @RequestParam String status) {
        try {
            List<BonafideResponseDto> bonafideResponseDtos = bonafideService.getAllBonafidesByRegisterNoAndBonafideStatus(registerNo, status);
            if (bonafideResponseDtos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No Bonafides found for the given register number and status", null));
            }
            return ResponseEntity.ok(new ApiResponse("Bonafides retrieved successfully", bonafideResponseDtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Failed to retrieve Bonafides", null));
        }
    }

    @GetMapping("/getAllBonafides")
    public ResponseEntity<ApiResponse> getAllBonafides() {
        try {
            List<BonafideResponseDto> bonafideResponseDtos = bonafideService.getAllBonafides();
            if (bonafideResponseDtos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No Bonafides found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Bonafides retrieved successfully", bonafideResponseDtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Failed to retrieve Bonafides", null));
        }
    }
    @PutMapping("/updateBonafideWithBonafideStatus")
    public ResponseEntity<ApiResponse> updateBonafideWithBonafideStatus(@RequestParam Long bonafideId, @RequestParam String registerNo, @RequestParam String status) {
        try {
            BonafideResponseDto updatedBonafide = bonafideService.updateBonafideWithBonafideStatus(bonafideId, registerNo, status);
            return ResponseEntity.ok(new ApiResponse("Bonafide updated successfully", updatedBonafide));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Failed to update Bonafide", null));
        }
    }

    @DeleteMapping("/deleteBonafide")  
    public ResponseEntity<ApiResponse> deleteBonafide(@RequestParam Long bonafideId, @RequestParam String registerNo) {
        try {
            bonafideService.deleteBonafide(bonafideId, registerNo);
            return ResponseEntity.ok(new ApiResponse("Bonafide deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Failed to delete Bonafide", null));
        }
    }

    @GetMapping("/downloadFile")
    public ResponseEntity<Resource> downloadBonafide(@RequestParam String filePath) {
        try {
            filePath = filePath.trim(); // important: remove \n or spaces
            File file = new File(filePath);

            if (!file.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            Path path = file.toPath(); // create Path from File
            Resource resource = new UrlResource(path.toUri());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"")
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace(); // helpful for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // ob neccessities
    @GetMapping("/getHodApproved")
    public ResponseEntity<ApiResponse> getHodApprovedBonafides(){
        try {
            List<BonafideResponseDto> bonafideResponseDtos = bonafideService.getHodApprovedBonafides();
            if (bonafideResponseDtos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No bonafides found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Bonafides found", bonafideResponseDtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("An error occured, failed to retrive bonafides",null));
        }

    }

    
    
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
// }
