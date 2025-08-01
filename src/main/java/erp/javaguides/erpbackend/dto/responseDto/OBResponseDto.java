package erp.javaguides.erpbackend.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OBResponseDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    
}
