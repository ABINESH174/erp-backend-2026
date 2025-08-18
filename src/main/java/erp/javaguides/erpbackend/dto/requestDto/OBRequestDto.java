package erp.javaguides.erpbackend.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OBRequestDto {
    private String name;
    private String email;
    private String phone;
    private String handlingPurpose;

}
