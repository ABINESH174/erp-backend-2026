package erp.javaguides.erpbackend.service.impl;

import erp.javaguides.erpbackend.dto.AuthenticationDto;
import erp.javaguides.erpbackend.entity.Authentication;
import erp.javaguides.erpbackend.mapper.AuthenticationMapper;
import erp.javaguides.erpbackend.repository.AuthenticationRepository;
import erp.javaguides.erpbackend.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationRepository authenticationRepository;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public AuthenticationDto createAuthentication(AuthenticationDto authenticationDto) {
        // Create a new Authentication entity
        Authentication authentication = AuthenticationMapper.mapToAuthentication(authenticationDto);
        // Encrypt the password before saving
        authentication.setPassword(passwordEncoder.encode(authentication.getPassword()));
        // Save the entity to the database
        Authentication savedAuthentication = authenticationRepository.save(authentication);
        // Return the saved entity as a DTO
        return AuthenticationMapper.mapToAuthenticationDto(savedAuthentication);
    }

    @Override
    public Authentication authenticate(AuthenticationDto authenticationDto) {
        Authentication empty=new Authentication();
        // Retrieve the Authentication entity by email
        Authentication authentication = authenticationRepository.findByUserId(authenticationDto.getUserId());
        boolean ismatched;
        if (authentication != null) {
            // Compare the provided password with the stored hashed password
            ismatched = passwordEncoder.matches(authenticationDto.getPassword(), authentication.getPassword());
            if(ismatched){
               return authentication;
            }
        }
        return empty;
    }

}
