package erp.javaguides.erpbackend.controller;

import erp.javaguides.erpbackend.dto.AuthenticationDto;
import erp.javaguides.erpbackend.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/authentication")
public class    AuthenticationController {
    private AuthenticationService authenticationService;

    //Build Add Student REST API
    @PostMapping("/create")
    public ResponseEntity<AuthenticationDto> createAuthentication(@RequestBody AuthenticationDto authenticationDto) {
        AuthenticationDto savedStudent = authenticationService.createAuthentication(authenticationDto);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }
    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationDto authenticationDto) {
        // Call your authentication service to handle the authentication logic
        boolean isAuthenticated = authenticationService.authenticate(authenticationDto);

        if (isAuthenticated) {
            return new ResponseEntity<>("Authentication successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid register Number", HttpStatus.UNAUTHORIZED);
        }
    }

}