package erp.javaguides.erpbackend.service.impl;

import erp.javaguides.erpbackend.dto.AuthenticationDto;
import erp.javaguides.erpbackend.entity.Authentication;
import erp.javaguides.erpbackend.mapper.AuthenticationMapper;
import erp.javaguides.erpbackend.repository.AuthenticationRepository;
import erp.javaguides.erpbackend.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private AuthenticationRepository authenticationRepository;
    @Override
    public AuthenticationDto createAuthentication(AuthenticationDto authenticationDto) {
        Authentication authentication = AuthenticationMapper.mapToAuthentication(authenticationDto);
        Authentication savedAuthentication=authenticationRepository.save(authentication);
        return AuthenticationMapper.mapToAuthenticationDto(savedAuthentication);
    }
    @Override
    public boolean authenticate(AuthenticationDto authenticationDto) {
        Authentication authentication = authenticationRepository.findByEmailidAndPassword(
                authenticationDto.getEmailid(),
                authenticationDto.getPassword()
        );
        return authentication != null && authentication.getPassword().equals(authenticationDto.getPassword());
    }

}