package erp.javaguides.erpbackend.controller;

import erp.javaguides.erpbackend.dto.requestDto.AuthRequestDto;
import erp.javaguides.erpbackend.dto.requestDto.AuthenticationDto;
// import erp.javaguides.erpbackend.dto.requestDto.FacultyDto;
import erp.javaguides.erpbackend.dto.requestDto.NewPasswordRequestDto;
import erp.javaguides.erpbackend.dto.requestDto.StudentDto;
import erp.javaguides.erpbackend.dto.responseDto.AuthResponseDto;
import erp.javaguides.erpbackend.dto.responseDto.FacultyResponseDto;
import erp.javaguides.erpbackend.dto.responseDto.HodResponseDto;
import erp.javaguides.erpbackend.entity.Authentication;
import erp.javaguides.erpbackend.entity.OfficeBearer;
import erp.javaguides.erpbackend.exception.ResourceNotFoundException;
import erp.javaguides.erpbackend.jwt.JwtUtil;
import erp.javaguides.erpbackend.mapper.AuthenticationMapper;
import erp.javaguides.erpbackend.repository.AuthenticationRepository;
import erp.javaguides.erpbackend.response.ApiResponse;
import erp.javaguides.erpbackend.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationRepository authenticationRepository;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final StudentService studentService;
    private  final FacultyService facultyService;
    private final HodService hodService;
    private final OfficeBearerService officeBearerService;

    private final ExcelService excelService;

    @PostMapping("/create")
    public ResponseEntity<AuthenticationDto> createAuthentication(@RequestBody AuthenticationDto authenticationDto) {
        try {
            AuthenticationDto savedStudent = authenticationService.createAuthentication(authenticationDto);
            return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDto> authenticate(@RequestBody AuthRequestDto authRequestDto) {
        try {
            if(authenticationRepository.existsByUserId(authRequestDto.getEmail())) {
            authRequestDto.setEmail(
                    authenticationRepository.findByUserId(authRequestDto.getEmail()).getEmail()
            );
            } else {
                throw new ResourceNotFoundException("No user found with userId :" + authRequestDto.getEmail());
            }
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                                authRequestDto.getEmail(),
                                authRequestDto.getPassword()
                    )
            );
            Authentication authentication = (Authentication) userDetailsService.loadUserByUsername(authRequestDto.getEmail());
            String jwtToken = jwtUtil.generateTokenWithRole(authentication.getUsername(),authentication.getRole().name());

            return ResponseEntity.ok(new AuthResponseDto(jwtToken));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
//    public ResponseEntity<String> authenticate(@RequestBody AuthenticationDto authenticationDto) {
//        Authentication authentication = authenticationService.authenticate(authenticationDto);
//
//        if (authentication==null) {
//            return new ResponseEntity<>("Invalid Register Number", HttpStatus.UNAUTHORIZED);
//        }
//        if(authentication.getRole().equalsIgnoreCase("HOD")){
//            try {
//                HodResponseDto hodResponseDto= hodService.getHodByEmail(authenticationDto.getUserId());
////                FacultyResponseDto facultyResponseDto = facultyService.getFacultyByEmail(authenticationDto.getUserId());
////                if (facultyResponseDto==null) {
////                    return new ResponseEntity<>("HOD Registration Not Successful", HttpStatus.OK);
////                }
//            } catch (Exception e) {
//                return new ResponseEntity<>("HOD Registration Not Successful", HttpStatus.OK);
//            }
//            return new ResponseEntity<>("HOD Authentication Successful", HttpStatus.OK);
//        }
//        if (authentication.getRole().equalsIgnoreCase("PRINCIPAL")) {
//            return new ResponseEntity<>("Principal Authentication Successful", HttpStatus.OK);
//        }
//        if(authentication.getRole().equalsIgnoreCase("FA")){
//            try {
//                FacultyResponseDto facultyResponseDto = facultyService.getFacultyByEmail(authenticationDto.getUserId());
//                if (facultyResponseDto==null) {
//                    return new ResponseEntity<>("Faculty Registration Not Successful", HttpStatus.OK);
//                }
//            } catch (Exception e) {
//                return new ResponseEntity<>("Faculty Registration Not Successful", HttpStatus.OK);
//            }
//            return new ResponseEntity<>("Faculty Authentication Successful", HttpStatus.OK);
//        }
//        if(authentication.getRole().equalsIgnoreCase("STUDENT")){
//            try {
//                StudentDto studentDto = studentService.getStudentByRegisterNo(authenticationDto.getUserId());
//                if (studentDto == null) {
//                    return new ResponseEntity<>("Form not filled", HttpStatus.OK);
//                }
//            } catch (Exception e) {
//                return new ResponseEntity<>("Form not filled", HttpStatus.OK);
//            }
//            return new ResponseEntity<>("Student Authentication Successful", HttpStatus.OK);
//        }
//
//        if(authentication.getRole().equalsIgnoreCase("OB")){
//            try{
//                OfficeBearer officeBearer=officeBearerService.getOfficeBearerByEmail(authentication.getUserId());
//                if(officeBearer==null){
//                    return new ResponseEntity<>("Office bearer is null",HttpStatus.OK);
//                }
//            }catch(Exception e) {
//                e.printStackTrace();
//                return new ResponseEntity<>("Office bearer login unsuccessful", HttpStatus.OK);
//            }
//            return new ResponseEntity<>("Office Bearer Authentication Successful", HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>("Invalid Register Number", HttpStatus.OK);
//    }

    @PostMapping("/upload-students")
    public ResponseEntity<ApiResponse> addStudentAuthenticationFromExcel(@RequestParam("file") MultipartFile file) {
        try {
            List<AuthenticationDto> authenticationDtos =  excelService.addStudentAuthenticationFromExcel(file.getInputStream());
            for(AuthenticationDto authenticationDto : authenticationDtos) {
                AuthenticationDto authentication = authenticationService.createAuthentication(authenticationDto);
            }
            return ResponseEntity.ok(new ApiResponse("Authentication of students using excel is created successfully",null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error adding authentication students from excel",null));
        }
    }
    @GetMapping("/get-otp")
    public ResponseEntity<ApiResponse> generateOTP(@RequestParam String userEmail) {
        try {
            authenticationService.generateForgotPasswordResetToken(userEmail);
            return ResponseEntity.ok(new ApiResponse("OTP generated successfully", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error changing password", null));
        }
    }
    @PutMapping("/set-password")
    public ResponseEntity<ApiResponse> generateNewPassword(@RequestBody NewPasswordRequestDto newPasswordRequestDto) {
        try {
            authenticationService.generateResetPassword(newPasswordRequestDto.getEmail(), newPasswordRequestDto.getOtp(), newPasswordRequestDto.getNewPassword());
            return ResponseEntity.ok(new ApiResponse("Password Changed Successfully",null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("Invalid or Expired OTP", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error changing password", null));
        }
    }
}