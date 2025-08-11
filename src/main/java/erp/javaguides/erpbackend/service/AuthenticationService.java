package erp.javaguides.erpbackend.service;

import erp.javaguides.erpbackend.dto.requestDto.AuthRequestDto;
import erp.javaguides.erpbackend.dto.requestDto.AuthenticationDto;
import erp.javaguides.erpbackend.dto.responseDto.AuthResponseDto;
import erp.javaguides.erpbackend.entity.Authentication;

public interface AuthenticationService {
    AuthenticationDto createAuthentication (AuthenticationDto authenticationDto);
    void generateForgotPasswordResetToken(String email);
    void generateResetPassword(String email, String otp, String newPassword);

    void createAuthenticationAndStudent(String facultyEmail, AuthenticationDto authenticationDto);

    void newPasswordAfterFirstTimeLogin(String userId, String newPassword);


}