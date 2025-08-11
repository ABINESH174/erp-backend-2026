package erp.javaguides.erpbackend.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDto {
    private String token;
    private boolean firstTimePasswordResetFlag;
}
