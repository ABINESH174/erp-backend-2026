package erp.javaguides.erpbackend.service;

import erp.javaguides.erpbackend.dto.StudentDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StudentService {
    String createStudent(String firstName, String lastName, String dateOfBirth,
                             String gender, String aadharNumber, String nationality, String religion,
                             String caste, String fathersName, String fathersOccupation,
                             String fathersMobileNumber, String mothersName, String mothersOccupation,
                             String mothersMobileNumber, String community, String guardiansName,
                             String guardiansOccupation, String guardiansMobileNumber, String parentsStatus,
                             String income, String maritalStatus, MultipartFile profilePhoto,
                             String mobileNumber, String emailId, String residentialAddress,
                             String communicationAddress, String hosteller, String hostelType,
                             String bankName, String ifscCode, String branchName, String accountNumber,
                             String sslc, String hsc1Year, String hsc2Year, String diploma,
                             MultipartFile sslcFile, MultipartFile hsc1YearFile, MultipartFile hsc2YearFile,
                             MultipartFile diplomaFile, String emisNumber, String firstGraduate,
                             String specialCategory) throws IOException;
    void createFolderIfNotExist(String folderPath);
    String saveFile(String firstName, String userFolderPath, String fileType, MultipartFile file) throws IOException;
    String getFileExtension(String filename);
    StudentDto getStudentByEmailId(String Email_Id);
    byte[] readFile(String filePath) throws IOException;
    List<StudentDto> getAllStudents();
    StudentDto updateStudent(String Email_Id, StudentDto updatedStudent);
    void deleteStudent(String Email_Id);
}
