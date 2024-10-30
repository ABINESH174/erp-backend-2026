package erp.javaguides.erpbackend.dto;

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
    private String handlingBatch;
    private String subject;
    private String handlingSemester;
    private String handlingDept;
    private String batch;
    private List<StudentDto> students;
    private List<FacultyDto> faculties;
}
