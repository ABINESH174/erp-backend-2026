package erp.javaguides.erpbackend.dto.requestDto;

import lombok.Data;

@Data
public class NewPasswordRequestDto {
    private String email;
    private String newPassword;
    private String otp;
}
