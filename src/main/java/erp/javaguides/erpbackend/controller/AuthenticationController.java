package erp.javaguides.erpbackend.controller;

import erp.javaguides.erpbackend.dto.requestDto.AuthenticationDto;
// import erp.javaguides.erpbackend.dto.requestDto.FacultyDto;
import erp.javaguides.erpbackend.dto.requestDto.StudentDto;
import erp.javaguides.erpbackend.dto.responseDto.FacultyResponseDto;
import erp.javaguides.erpbackend.entity.Authentication;
import erp.javaguides.erpbackend.service.AuthenticationService;
import erp.javaguides.erpbackend.service.FacultyService;
import erp.javaguides.erpbackend.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final StudentService studentService;
    private  final FacultyService facultyService;

    @PostMapping("/create")
    public ResponseEntity<AuthenticationDto> createAuthentication(@RequestBody AuthenticationDto authenticationDto) {
        AuthenticationDto savedStudent = authenticationService.createAuthentication(authenticationDto);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationDto authenticationDto) {
        Authentication authentication = authenticationService.authenticate(authenticationDto);

        if (authentication==null) {
            return new ResponseEntity<>("Invalid Register Number", HttpStatus.UNAUTHORIZED);
        }
        if(authentication.getRole().equalsIgnoreCase("HOD")){
            try {
                FacultyResponseDto facultyResponseDto = facultyService.getFacultyByEmail(authenticationDto.getUserId());
                if (facultyResponseDto==null) {
                    return new ResponseEntity<>("HOD Registration Not Successful", HttpStatus.OK);
                }
            } catch (Exception e) {
                return new ResponseEntity<>("HOD Registration Not Successful", HttpStatus.OK);
            }
            return new ResponseEntity<>("HOD Authentication Successful", HttpStatus.OK);
        }
        if (authentication.getRole().equalsIgnoreCase("PRINCIPAL")) {
            return new ResponseEntity<>("Principal Authentication Successful", HttpStatus.OK);
        }
        if(authentication.getRole().equalsIgnoreCase("FA")){
            try {
                FacultyResponseDto facultyResponseDto = facultyService.getFacultyByEmail(authenticationDto.getUserId());
                if (facultyResponseDto==null) {
                    return new ResponseEntity<>("Faculty Registration Not Successful", HttpStatus.OK);
                }
            } catch (Exception e) {
                return new ResponseEntity<>("Faculty Registration Not Successful", HttpStatus.OK);
            }
            return new ResponseEntity<>("Faculty Authentication Successful", HttpStatus.OK);
        }
        if(authentication.getRole().equalsIgnoreCase("ST")){
            try {
                StudentDto studentDto = studentService.getStudentByRegisterNo(authenticationDto.getUserId());
                if (studentDto == null) {
                    return new ResponseEntity<>("Form not filled", HttpStatus.OK);
                }
            } catch (Exception e) {
                return new ResponseEntity<>("Form not filled", HttpStatus.OK);
            }
            return new ResponseEntity<>("Student Authentication Successful", HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid Register Number", HttpStatus.OK);

    }
}