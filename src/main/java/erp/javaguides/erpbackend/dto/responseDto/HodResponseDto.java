package erp.javaguides.erpbackend.dto.responseDto;

import lombok.Data;

import java.util.List;

@Data
public class HodResponseDto {

    private Long hodId;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String discipline;

    private List<FacultyResponseDto> faculties;
    
}
