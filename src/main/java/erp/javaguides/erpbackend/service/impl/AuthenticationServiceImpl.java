package erp.javaguides.erpbackend.service.impl;

import erp.javaguides.erpbackend.dto.requestDto.AuthenticationDto;
import erp.javaguides.erpbackend.entity.Authentication;
import erp.javaguides.erpbackend.entity.Faculty;
import erp.javaguides.erpbackend.entity.Student;
import erp.javaguides.erpbackend.enums.AuthenticationStatus;
import erp.javaguides.erpbackend.exception.ResourceNotFoundException;
import erp.javaguides.erpbackend.exception.UserAlreadyExistsException;
import erp.javaguides.erpbackend.jwt.JwtUtil;
import erp.javaguides.erpbackend.mapper.AuthenticationMapper;
import erp.javaguides.erpbackend.repository.AuthenticationRepository;
import erp.javaguides.erpbackend.repository.FacultyRepository;
import erp.javaguides.erpbackend.repository.StudentRepository;
import erp.javaguides.erpbackend.service.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService, UserDetailsService {

    @Value("${admin.email}")
    private String adminMail;

    private static final Logger log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private final AuthenticationRepository authenticationRepository;
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public AuthenticationDto createAuthentication(AuthenticationDto authenticationDto) {
        Authentication authentication = AuthenticationMapper.mapToAuthentication(authenticationDto);
        if(authenticationDto.getRole().name().equals("ADMIN")) {
            authentication.setEmail(adminMail);
            authentication.setFirstTimePasswordResetFlag(false);
        }
        authentication.setPassword(passwordEncoder.encode(authentication.getPassword()));
        authentication.setFirstTimePasswordResetFlag(true);
        authentication.setAuthenticationStatus(AuthenticationStatus.ACTIVE);
        Authentication savedAuthentication = authenticationRepository.save(authentication);

        return AuthenticationMapper.mapToAuthenticationDto(savedAuthentication);
    }

//    public Authentication authenticate(AuthenticationDto authenticationDto) {
//        Authentication empty=new Authentication();
//        Authentication authentication = authenticationRepository.findByUserId(authenticationDto.getUserId());
//        boolean isMatched;
//        if (authentication != null) {
//            isMatched = passwordEncoder.matches(authenticationDto.getPassword(), authentication.getPassword());
//            if(isMatched){
//               return authentication;
//            }
//        }
//        return empty;
//    }

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

    @Override
    public void createAuthenticationAndStudent(String facultyEmail, AuthenticationDto authenticationDto) {
        if(authenticationRepository.existsByUserId(authenticationDto.getUserId())) {
            throw new UserAlreadyExistsException("User already exists with email"+authenticationDto.getUserId());
        }
        // create authentication
        Authentication authentication = AuthenticationMapper.mapToAuthentication(authenticationDto);

        authentication.setPassword(passwordEncoder.encode(authentication.getPassword()));
        authentication.setFirstTimePasswordResetFlag(true);
        authentication.setAuthenticationStatus(AuthenticationStatus.ACTIVE);
        Authentication savedAuthentication = authenticationRepository.save(authentication);

        // create student and assign faculty
        Student student = new Student(savedAuthentication.getUserId());
        Faculty faculty = facultyRepository.findByEmail(facultyEmail)
                            .orElseThrow(()->new ResourceNotFoundException("Faculty not found with email :"+ facultyEmail));
        faculty.addStudent(student);

        studentRepository.save(student);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if(authenticationRepository.findByEmail(username).isEmpty()) {
            UserDetails userDetailsForStudentByRegisterNo = authenticationRepository.findByUserId(username);
            if(userDetailsForStudentByRegisterNo==null) {
                throw new UsernameNotFoundException("No user found with the given userId" + username);
            } else {
                log.info("The userDetails object : \n User id: "+userDetailsForStudentByRegisterNo.getUsername()+"Password"+ userDetailsForStudentByRegisterNo.getPassword()+"role"+ userDetailsForStudentByRegisterNo.getAuthorities());
                return userDetailsForStudentByRegisterNo;
            }
        }

        // Since Authentication class implements UserDetails, you can return it directly.
        // The `findByEmail` method returns Optional<Authentication>, and Authentication is a UserDetails.
        log.info("The username of the user: "+ username);
        UserDetails userDetailsForOtherEntitiesByEmail = authenticationRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + username));
        log.info("The userDetails object : \n User id: "+userDetailsForOtherEntitiesByEmail.getUsername()+"Password"+ userDetailsForOtherEntitiesByEmail.getPassword()+"role"+ userDetailsForOtherEntitiesByEmail.getAuthorities());
        return userDetailsForOtherEntitiesByEmail;
    }

    @Override
    public void newPasswordAfterFirstTimeLogin(String userId, String newPassword) {

        try {
            Authentication authentication = authenticationRepository.findByUserId(userId);
            if(authentication.isFirstTimePasswordResetFlag()) {
                authentication.setPassword(passwordEncoder.encode(newPassword));
                authentication.setFirstTimePasswordResetFlag(false);
                authenticationRepository.save(authentication);
            } else {
                throw new RuntimeException("Password is already updated");
            }
        } catch (Exception e) {
            throw new ResourceNotFoundException("User not found with userId: " + userId);
        }
    }
}
