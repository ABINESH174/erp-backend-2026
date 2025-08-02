package erp.javaguides.erpbackend.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationDto {
    private Long id;
    private String userId;
    private String password;
    private String role;
    private String email;
    private String forgotPasswordResetToken;
    private LocalDateTime forgotPasswordResetTokenExpiry;

    public AuthenticationDto(String userId, String password, String role) {
        this.userId=userId;
        this.password=password;
        this.role= role;
    }

    public AuthenticationDto(Long id, String userId, String password, String role) {
        this.id=id;
        this.userId=userId;
        this.password=password;
        this.role= role;
    }
}