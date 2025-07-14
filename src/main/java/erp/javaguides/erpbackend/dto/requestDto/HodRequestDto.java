package erp.javaguides.erpbackend.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HodRequestDto {

    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String discipline;
    private String department;

    // Hard code
    private String principalEmail;
    
}
