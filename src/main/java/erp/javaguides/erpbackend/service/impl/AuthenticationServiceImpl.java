package erp.javaguides.erpbackend.service.impl;

import erp.javaguides.erpbackend.dto.requestDto.AuthenticationDto;
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
        Authentication authentication = AuthenticationMapper.mapToAuthentication(authenticationDto);
        authentication.setPassword(passwordEncoder.encode(authentication.getPassword()));
        Authentication savedAuthentication = authenticationRepository.save(authentication);
        return AuthenticationMapper.mapToAuthenticationDto(savedAuthentication);
    }

    @Override
    public Authentication authenticate(AuthenticationDto authenticationDto) {
        Authentication empty=new Authentication();
        Authentication authentication = authenticationRepository.findByUserId(authenticationDto.getUserId());
        boolean isMatched;
        if (authentication != null) {
            isMatched = passwordEncoder.matches(authenticationDto.getPassword(), authentication.getPassword());
            if(isMatched){
               return authentication;
            }
        }
        return empty;
    }

}
