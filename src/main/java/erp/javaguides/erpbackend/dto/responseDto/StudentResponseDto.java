package erp.javaguides.erpbackend.dto.responseDto;

import erp.javaguides.erpbackend.entity.Faculty;
import erp.javaguides.erpbackend.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseDto {
    private String registerNo;
    private String firstName;
    private String lastName;

    private Gender gender;

    private String emailId;

    private String mobileNumber;

    private String dateOfBirth;

    private String programme;

    private String discipline;

    private String department;

    private String classSection;

    private String semester;

    private String batch;

    private String cgpa;

    private Long facultyId;

}
