package erp.javaguides.erpbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacultyDto {
    private String name;
    private String email;
    private String department;
    private String batch;
}
