package erp.javaguides.erpbackend.controller;

import erp.javaguides.erpbackend.dto.requestDto.AuthRequestDto;
import erp.javaguides.erpbackend.dto.requestDto.AuthenticationDto;
// import erp.javaguides.erpbackend.dto.requestDto.FacultyDto;
import erp.javaguides.erpbackend.dto.requestDto.NewPasswordAfterLoginFirstTimeRequestDto;
import erp.javaguides.erpbackend.dto.requestDto.NewPasswordRequestDto;
import erp.javaguides.erpbackend.dto.responseDto.AuthResponseDto;
import erp.javaguides.erpbackend.exception.OtpExpiredException;
import erp.javaguides.erpbackend.exception.OtpInvalidException;
import erp.javaguides.erpbackend.exception.ResourceNotFoundException;
import erp.javaguides.erpbackend.jwt.JwtUtil;
import erp.javaguides.erpbackend.repository.AuthenticationRepository;
import erp.javaguides.erpbackend.dto.ApiResponse;
import erp.javaguides.erpbackend.service.*;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationRepository authenticationRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
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
            erp.javaguides.erpbackend.entity.Authentication authVerification = authenticationRepository.findByUserId(authRequestDto.getEmail());

            if(authVerification==null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No User Found with UserId "+authRequestDto.getEmail(),null));
            } else if (!authVerification.isUserActive()){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("User Expired",null));
            } else if (authVerification.getEmail()==null) {
                // For a student and faculty who havent registered yet will have their email empty, they'll have to be registered first.
                if(!passwordEncoder.matches(authRequestDto.getPassword(),authVerification.getPassword())) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("Bad credentials; Password Incorrect",null));
                }

                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse(
                        "The User hasn't registered yet.",
                        Map.of(
                                "userId",
                                authVerification.getUserId(),
                                "role",
                                authVerification.getRole().name())
                        )
                );
            } else {

//                Cookie oldCookie = new Cookie("jwt",null);
//                oldCookie.setPath("/");
//                oldCookie.setMaxAge(0);
//                response.addCookie(oldCookie);

                org.springframework.security.core.Authentication authentication =
                        authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword())
                        );
//                Authentication auth = (Authentication) userDetailsService.loadUserByUsername(authRequestDto.getEmail());

                erp.javaguides.erpbackend.entity.Authentication auth =
                        (erp.javaguides.erpbackend.entity.Authentication) authentication.getPrincipal();

//                SecurityContextHolder.getContext().setAuthentication((org.springframework.security.core.Authentication) authentication);

                String jwtToken = jwtUtil.generateTokenWithRole(auth.getUsername(),auth.getRole().name());
                boolean flag = auth.isFirstTimePasswordResetFlag();


                // Set new cookie with SameSite manually
//                String cookieHeader = String.format(
//                        "jwt=%s; Max-Age=%d; Path=/; HttpOnly; SameSite=Lax",
//                        jwtToken,
//                        3 * 24 * 60 * 60
//                );
//                response.setHeader("Set-Cookie", cookieHeader);




//                Cookie newCookie = new Cookie("jwt", jwtToken);
//                newCookie.setHttpOnly(true);
//                newCookie.setSecure(true); // Set to false for local HTTP development
//                newCookie.setPath("/");
//                newCookie.setMaxAge(3 * 24 * 60 * 60);



                // Manually add the SameSite attribute to the Set-Cookie header
//                response.addHeader("Set-Cookie", String.format("%s; SameSite=Lax", newCookie.toString()));
//
//                response.addCookie(newCookie);

                return ResponseEntity.ok(new AuthResponseDto(jwtToken, flag));
//                if(!(authenticationRepository.findByUserId(authRequestDto.getEmail()).getEmail()==null)) {
////                    authRequestDto.setEmail(
////                            authenticationRepository.findByUserId(authRequestDto.getEmail()).getEmail()
////                    );
//                }
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

        catch (BadCredentialsException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("Bad credentials; Username or Password Icorrect",null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

//    @PostMapping("/logout")
//    public ResponseEntity<ApiResponse> logout(HttpServletResponse httpServletResponse) {
////        Cookie cookie = new Cookie("jwt", null);
////        cookie.setHttpOnly(true);
////        cookie.setSecure(true);
////        cookie.setPath("/");
////        cookie.setMaxAge(0); // Expire
//
//        // You should add SameSite attribute manually if you were doing so during login
////        httpServletResponse.addHeader("Set-Cookie", String.format("%s; SameSite=Lax", cookie.toString()));
////
////        httpServletResponse.addCookie(cookie);
//
//        return ResponseEntity.ok(new ApiResponse("Logged out Successfully", null));
//    }

//    @GetMapping("/current-user")
//    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
//        try {
//            // Extract JWT from cookie
//            String jwt = null;
//            if (request.getCookies() != null) {
//                for (Cookie cookie : request.getCookies()) {
//                    if ("jwt".equals(cookie.getName())) {
//                        jwt = cookie.getValue();
//                        break;
//                    }
//                }
//            }
//
//            if (jwt == null) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No token found");
//            }
//
//            // Extract email and role
//            String userId = jwtUtil.extractUserName(jwt);
//            String role = jwtUtil.extractRole(jwt);
//
//            System.out.println("👍👍👍👍👍👍👍👍👍👍👍Current UserId in authcontroller"+userId);
//            System.out.println("👍👍👍👍👍👍👍👍👍👍👍👍Current role in authcontroller"+role);
////            log.info("Current UserId in authcontroller"+userId);
////            log.info("Current role in authcontroller"+role);
//
//            return ResponseEntity.ok(
//                        new ApiResponse("Current user retrieved successfully", Map.of(
//                                "userId", userId,
//                                "role", role
//                        ))
//                    );
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
//        }
//    }

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
            return ResponseEntity.ok(new ApiResponse("Authentication of students using excel by faculty is created successfully",null));
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
        } catch (OtpExpiredException e) { // Status code - 412
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(new ApiResponse("Expired OTP", null));
        } catch (OtpInvalidException e) { // Status code - 406
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("Invalid OTP", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error changing password", null));
        }
    }

    @PutMapping("/new-password")
    public ResponseEntity<ApiResponse> newPasswordAfterFirstTimeAuthentication(@RequestBody NewPasswordAfterLoginFirstTimeRequestDto loginFirstTimeRequestDto) {
        try {
            authenticationService.newPasswordAfterFirstTimeLogin(loginFirstTimeRequestDto.getUserId(), loginFirstTimeRequestDto.getNewPassword());
            return ResponseEntity.ok(new ApiResponse("Password Updated Successfully",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User not found with userId: " + loginFirstTimeRequestDto.getUserId(), null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("update password failed",null));
        }
    }
}