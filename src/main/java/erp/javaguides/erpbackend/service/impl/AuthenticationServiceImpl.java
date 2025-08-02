package erp.javaguides.erpbackend.service.impl;

import erp.javaguides.erpbackend.dto.requestDto.AuthenticationDto;
import erp.javaguides.erpbackend.entity.Authentication;
import erp.javaguides.erpbackend.exception.ResourceNotFoundException;
import erp.javaguides.erpbackend.mapper.AuthenticationMapper;
import erp.javaguides.erpbackend.repository.AuthenticationRepository;
import erp.javaguides.erpbackend.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

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

    private final JavaMailSender javaMailSender;
    @Override
    public void generateForgotPasswordResetToken(String email) {
        Authentication authentication = authenticationRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User Not Found with Email"));
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        authentication.setForgotPasswordResetToken(otp);
        authentication.setForgotPasswordResetTokenExpiry(LocalDateTime.now().plusMinutes(10));
        authenticationRepository.save(authentication);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Reset Password OTP");
        message.setText("Your OTP is: " + otp);
        javaMailSender.send(message);
    }

    @Override
    public void generateResetPassword(String email, String otp, String newPassword) {
        Authentication authentication = authenticationRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        if(authentication.getForgotPasswordResetToken() == null || !authentication.getForgotPasswordResetToken().equals(otp)) {
            System.out.println(otp);
            System.out.println(authentication.getForgotPasswordResetToken());
            throw new RuntimeException("Invalid OTP");
        }
        if(LocalDateTime.now().isAfter(authentication.getForgotPasswordResetTokenExpiry())) {
            throw new RuntimeException("OTP Expired");
        }
        authentication.setPassword(passwordEncoder.encode(newPassword));
        authentication.setForgotPasswordResetToken(null);
        authentication.setForgotPasswordResetTokenExpiry(null);
        authenticationRepository.save(authentication);
    }

}
