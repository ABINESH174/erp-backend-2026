package erp.javaguides.erpbackend.dto.responseDto;
// import erp.javaguides.erpbackend.dto.responseDto.StudentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacultyResponseDto {
    private Long facultyId;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String discipline;
    private String handlingBatch;
    private List<StudentResponseDto> students;

    // private List<String> subjects;
    // private List<String> handlingSemesters;
    // private List<String> handlingDepartments;
    // private List<String> batches;
    // private List<FacultyDto> faculties;
}
