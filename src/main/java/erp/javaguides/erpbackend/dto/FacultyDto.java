package erp.javaguides.erpbackend.dto;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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

    private List<String> subjects;
    private List<String> handlingSemesters;
    private List<String> handlingDepartments;
    private List<String> batches;
    private List<StudentDto> students;
    private List<FacultyDto> faculties;
}
