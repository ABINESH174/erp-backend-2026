package erp.javaguides.erpbackend.dto.requestDto;

import lombok.Data;

@Data
public class NewPasswordAfterLoginFirstTimeRequestDto {
    private String userId;
    private String newPassword;
}
