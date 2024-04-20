package erp.javaguides.erpbackend.service;

import erp.javaguides.erpbackend.dto.AuthenticationDto;

public interface AuthenticationService {
    AuthenticationDto createAuthentication (AuthenticationDto authenticationDto);
}