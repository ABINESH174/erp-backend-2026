package erp.javaguides.erpbackend.service;

import erp.javaguides.erpbackend.dto.requestDto.AuthenticationDto;
import erp.javaguides.erpbackend.entity.Authentication;

public interface AuthenticationService {
    AuthenticationDto createAuthentication (AuthenticationDto authenticationDto);
    Authentication authenticate(AuthenticationDto authenticationDto);
}