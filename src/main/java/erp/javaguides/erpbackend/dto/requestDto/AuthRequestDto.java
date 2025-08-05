package erp.javaguides.erpbackend.dto.requestDto;

import lombok.Data;

@Data
public class AuthRequestDto {
    private String email;
    private String password;
}
