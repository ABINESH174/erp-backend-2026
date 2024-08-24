package erp.javaguides.erpbackend.controller;

import erp.javaguides.erpbackend.dto.AuthenticationDto;
import erp.javaguides.erpbackend.dto.StudentWithFilesDto;
import erp.javaguides.erpbackend.entity.Authentication;
import erp.javaguides.erpbackend.service.AuthenticationService;
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

    // Build Add Student REST API
    @PostMapping("/create")
    public ResponseEntity<AuthenticationDto> createAuthentication(@RequestBody AuthenticationDto authenticationDto) {
        AuthenticationDto savedStudent = authenticationService.createAuthentication(authenticationDto);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationDto authenticationDto) {
        Authentication authentication = authenticationService.authenticate(authenticationDto);

        if (authentication==null) {
            return new ResponseEntity<>("Invalid register number", HttpStatus.UNAUTHORIZED);
        }

        if(authentication.getRole().equalsIgnoreCase("FA")){
            return new ResponseEntity<>("Faculty Authentication successful", HttpStatus.OK);
        }
        if(authentication.getRole().equalsIgnoreCase("ST")){
            try {
                StudentWithFilesDto student = studentService.getStudentByRegisterNo(authenticationDto.getUserId());
                if (student == null) {
                    return new ResponseEntity<>("Form not filled", HttpStatus.OK);
                }
            } catch (Exception e) {
                return new ResponseEntity<>("Form not filled", HttpStatus.OK);
            }
            return new ResponseEntity<>("Student Authentication successful", HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid register number", HttpStatus.OK);

    }
}