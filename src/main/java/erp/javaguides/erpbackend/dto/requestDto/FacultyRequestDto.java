package erp.javaguides.erpbackend.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacultyRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String discipline;
    private String department;
    // private String handlingBatch;
}
