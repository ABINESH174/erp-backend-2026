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
    private String name;
    private String email;
    private String discipline;
    private String academicYear;
    private List<StudentWithFilesDto> students;
}
