package erp.javaguides.erpbackend.controller;

import erp.javaguides.erpbackend.dto.requestDto.CreateBonafideRequestDto;
import erp.javaguides.erpbackend.dto.responseDto.ApplicableBonafideResponseDto;
import erp.javaguides.erpbackend.dto.responseDto.BonafideResponseDto;
import erp.javaguides.erpbackend.enums.BonafideStatus;
import erp.javaguides.erpbackend.enums.BonafideType;
import erp.javaguides.erpbackend.exception.ResourceNotFoundException;
import erp.javaguides.erpbackend.dto.ApiResponse;
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
            System.out.println(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Bonafide created successfully",createdBonafide));
        } catch (Exception e) {
            e.printStackTrace();
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

    @GetMapping("/get/type")
    public ResponseEntity<ApiResponse> getBonafidesByBonafideType(@RequestParam BonafideType bonafideType) {
        try {
            List<BonafideResponseDto> bonafideResponseDtos = bonafideService.getBonafidesByBonafideType(bonafideType);
            return ResponseEntity.ok(new ApiResponse("Bonafides retrieved successfully",bonafideResponseDtos));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No bonafides found for the given bonafide type", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Failed to retrieve Bonafides", null));
        }
    }

    @GetMapping("/get/status/type")
    public ResponseEntity<ApiResponse> getBonafidesByBonafideStatusAndBonafideType(@RequestParam BonafideStatus bonafideStatus, @RequestParam BonafideType bonafideType) {
        try {
            List<BonafideResponseDto> bonafideResponseDtos = bonafideService.getBonafidesByBonafideStatusAndBonafideType(bonafideStatus, bonafideType);
            return ResponseEntity.ok(new ApiResponse("Bonafides retrieved successfully",bonafideResponseDtos));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No bonafides found for the given bonafide type", null));
        } catch (Exception e) {
            e.printStackTrace();
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
            return ResponseEntity.ok(new ApiResponse("Bonafide " + status + " successfully", updatedBonafide));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Failed to update Bonafide", null));
        }
    }


    @PutMapping("/updateObRejectedBonafide")
    public ResponseEntity<ApiResponse> updateObRejectedBonafide(@RequestParam Long bonafideId, @RequestParam String registerNo, @RequestParam BonafideStatus status, @RequestParam String rejectionMessage) {
        try {
            BonafideResponseDto updatedBonafide = bonafideService.updateObRejectedBonafide(bonafideId, registerNo,  status, rejectionMessage);

            return ResponseEntity.ok(new ApiResponse("Bonafide rejected successfully", updatedBonafide));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Failed to reject Bonafide", null));
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
    @GetMapping("/getCertificate/{bonafideId}")
    public ResponseEntity<byte[]> downloadBonafideCertificate(@PathVariable Long bonafideId, @RequestParam String registerNo){
        try {
            byte[] certificate = bonafideService.generateBonafideCertificate(bonafideId,registerNo);
//            ByteArrayResource byteArrayResource = new ByteArrayResource(certificate);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=bonafide_"+bonafideId+".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(certificate);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }


    @GetMapping("/downloadFile")
    public ResponseEntity<Resource> downloadFileUsingFilePath(@RequestParam String filePath) {
        
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

    @GetMapping("/previewFile")
    public ResponseEntity<Resource> previewFile(@RequestParam String filePath) {
        try {
            // Sanitize file path
            filePath = filePath.trim();// important: remove \n or spaces
            File file = new File(filePath);

            // Check if file exists
            if (!file.exists()) {
                return ResponseEntity.status(404).body(null);
            }

            // Validate file path to prevent directory traversal
            // if (!file.getCanonicalPath().startsWith("/path/to/allowed/directory")) {
            //     return ResponseEntity.status(403).body(null);
            // }

            // Create resource
            Path path = file.toPath(); // create Path from File
            Resource resource = new UrlResource(path.toUri());

            // Determine content type dynamically
            String contentType = determineContentType(file.getName());
            
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"")
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    };
        

    // Helper method to determine content type based on file extension
    private String determineContentType(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        switch (extension) {
            case "pdf":
                return "application/pdf";
            case "png":
                return "image/png";
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "txt":
                return "text/plain";
            case "html":
                return "text/html";
            default:
                return "application/octet-stream"; // Fallback for unknown types
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
    @GetMapping("/getPrincipalApproved")
    public ResponseEntity<ApiResponse> getPrincipalApprovedBonafides() {
        try {
            List<BonafideResponseDto> bonafideResponseDtos = bonafideService.getPrincipalApprovedBonafides();
            if (bonafideResponseDtos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("No bonafides found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Bonafides found", bonafideResponseDtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("An error occurred, failed to retrieve bonafides", null));
        }
    }
    @GetMapping("getNotifiedBonafides")
    public ResponseEntity<ApiResponse> getNotifiedBonafides(){
        try {
            List<BonafideResponseDto> bonafideResponseDtos = bonafideService.getNotifiedBonafides();
            if (bonafideResponseDtos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("No bonafides found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Bonafides found", bonafideResponseDtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("An error occurred, failed to retrieve bonafides", null));
        }
    }

    @GetMapping("/getApplicableBonafide/{registerNo}")
    public ResponseEntity<ApiResponse> getApplicableBonafide(@PathVariable String registerNo) {
        try {
            ApplicableBonafideResponseDto applicableBonafideResponseDto = bonafideService.getApplicableBonafied(registerNo);
            return ResponseEntity.ok(new ApiResponse("Applicable bonafide fetched successfully", applicableBonafideResponseDto));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error fetching applicable bonafied",null));
        }
    }

    @GetMapping("/faculty/previousBonafide/{facultyId}")
    public ResponseEntity<ApiResponse> getPreviousBonafideByFacultyId(@PathVariable Long facultyId) {
        try {
            List<BonafideResponseDto> previousBonafides = bonafideService.getPreviousBonafidebyFacultyId(facultyId);
            if(previousBonafides.isEmpty()){
                return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("No previous Bonafides" , null));
            }
            return ResponseEntity.ok(new ApiResponse("Previous Bonafides:" , previousBonafides));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error Fetching Previous Bonafides." ,null));
        }
    }

    @GetMapping("/hod/previousBonafide/{hodId}")
    public ResponseEntity<ApiResponse> getPreviousBonafideByHodId(@PathVariable Long hodId) {
        try {
            List<BonafideResponseDto> previousBonafide = bonafideService.getPreviousBonafidebyHodId(hodId);
            if(previousBonafide.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No Previous Bonafides",null));
            }
            return ResponseEntity.ok(new ApiResponse("Previous Bonafides:", previousBonafide));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error Fetching Previous Bonafides." ,null));
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
