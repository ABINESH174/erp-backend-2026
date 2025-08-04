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
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> authenticate(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response) {
        try {
            if(!(authenticationRepository.existsByUserId(authRequestDto.getEmail()))) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No User Found with UserId "+authRequestDto.getEmail(),null));
            } else if (authenticationRepository.findByUserId(authRequestDto.getEmail()).getEmail()==null) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse(
                        "The User hasn't registered yet.",
                        Map.of(
                                "userId",
                                authenticationRepository.findByUserId(authRequestDto.getEmail()).getUserId(),
                                "role",
                                authenticationRepository.findByUserId(authRequestDto.getEmail()).getRole().name())
                        )
                );
            } else {
                authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequestDto.getEmail(),
                            authRequestDto.getPassword()
                    )
                );
                Authentication authentication = (Authentication) userDetailsService.loadUserByUsername(authRequestDto.getEmail());
                String jwtToken = jwtUtil.generateTokenWithRole(authentication.getUsername(),authentication.getRole().name());

                Cookie cookie = new Cookie("jwt", jwtToken);
                cookie.setHttpOnly(true);
                cookie.setSecure(true);
                cookie.setPath("/");
                cookie.setMaxAge(3 * 24 * 60 * 60);
                response.addCookie(cookie);

                return ResponseEntity.ok(new ApiResponse("Login Successfull",null));
//                if(!(authenticationRepository.findByUserId(authRequestDto.getEmail()).getEmail()==null)) {
////                    authRequestDto.setEmail(
////                            authenticationRepository.findByUserId(authRequestDto.getEmail()).getEmail()
////                    );
//                }
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Expire

        httpServletResponse.addCookie(cookie);

        return ResponseEntity.ok(new ApiResponse("Logged out Successfully", null));
    }

    @GetMapping("/current-user")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        try {
            // Extract JWT from cookie
            String jwt = null;
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if ("jwt".equals(cookie.getName())) {
                        jwt = cookie.getValue();
                        break;
                    }
                }
            }

            if (jwt == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No token found");
            }

            // Extract email and role
            String userId = jwtUtil.extractUserName(jwt);
            String role = jwtUtil.extractRole(jwt);

            return ResponseEntity.ok(
                        new ApiResponse("Current user retrieved successfully", Map.of(
                                "userId", userId,
                                "role", role
                        ))
                    );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
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

    @PostMapping("/upload-students/{facultyEmail}")
    public ResponseEntity<ApiResponse> addStudentAuthenticationFromExcel(@PathVariable String facultyEmail, @RequestParam("file") MultipartFile file) {
        try {
            List<AuthenticationDto> authenticationDtos =  excelService.addStudentAuthenticationFromExcel(file.getInputStream());
            for(AuthenticationDto authenticationDto : authenticationDtos) {
                authenticationService.createAuthenticationAndStudent(facultyEmail, authenticationDto);
            }
            return ResponseEntity.ok(new ApiResponse("Authentication of students using excel is created successfully",null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error adding authentication students from excel",null));
        }
    }

    @PostMapping("/create/student/{facultyEmail}")
    public ResponseEntity<ApiResponse> createAuthenticationAndStudent(@PathVariable String facultyEmail, @RequestBody AuthenticationDto authenticationDto) {
        try {
            authenticationService.createAuthenticationAndStudent(facultyEmail, authenticationDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Authentication and student created successfully", null));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error creating authentication and student", null));

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