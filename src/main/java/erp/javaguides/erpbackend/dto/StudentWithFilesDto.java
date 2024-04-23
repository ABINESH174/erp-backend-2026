package erp.javaguides.erpbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentWithFilesDto {
    private StudentDto studentDto;
    private byte[] profilePhotoContent;
    private byte[] sslcFileContent;
    private byte[] hsc1YearFileContent;
    private byte[] hsc2YearFileContent;
    private byte[] diplomaFileContent;

    // Getters and setters
}