package erp.javaguides.erpbackend.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacultyDto {
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String discipline;

    // Dynamic field, it must be assigned by the hod
    private String handlingBatch;

    private List<String> subjects;
    private List<String> handlingSemesters;
    private List<String> handlingDepartments;
    private List<String> batches;
    private List<StudentDto> students;
    // private List<FacultyDto> faculties;
}
