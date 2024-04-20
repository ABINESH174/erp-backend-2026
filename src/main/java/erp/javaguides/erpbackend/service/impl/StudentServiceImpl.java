package erp.javaguides.erpbackend.service.impl;

import erp.javaguides.erpbackend.dto.StudentDto;
import erp.javaguides.erpbackend.entity.Student;
import erp.javaguides.erpbackend.exception.ResourceNotFoundException;
import erp.javaguides.erpbackend.mapper.StudentMapper;
import erp.javaguides.erpbackend.repository.StudentRepository;
import erp.javaguides.erpbackend.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;
    private static final String FOLDER_PATH = "C:/Users/m.uvasri/Desktop/FileSystem/";
    @Override
    public String createStudent(String firstName, String lastName, String dateOfBirth,
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
                                    String specialCategory) throws IOException {

        String profilePhotoPath = Paths.get(FOLDER_PATH, profilePhoto.getOriginalFilename()).toString();
        String sslcFilePath = Paths.get(FOLDER_PATH, sslcFile.getOriginalFilename()).toString();
        String hsc1YearFilePath = Paths.get(FOLDER_PATH, hsc1YearFile.getOriginalFilename()).toString();
        String hsc2YearFilePath = Paths.get(FOLDER_PATH, hsc2YearFile.getOriginalFilename()).toString();
        String diplomaFilePath = Paths.get(FOLDER_PATH, diplomaFile.getOriginalFilename()).toString();

        Student student = studentRepository.save(Student.builder()
                .First_Name(firstName) // Assuming 'name' corresponds to the student's first name
                .Last_Name(lastName)
                .Date_Of_Birth(dateOfBirth)
                .Gender(gender)
                .Aadhar_Number(aadharNumber)
                .Nationality(nationality)
                .Religion(religion)
                .Caste(caste)
                .Fathers_Name(fathersName)
                .Fathers_Occupation(fathersOccupation)
                .Fathers_Mobile_Number(fathersMobileNumber)
                .Mothers_Name(mothersName)
                .Mothers_Occupation(mothersOccupation)
                .Mothers_Mobile_Number(mothersMobileNumber)
                .Community(community)
                .Guardians_Name(guardiansName)
                .Guardians_Occupation(guardiansOccupation)
                .Guardians_Mobile_Number(guardiansMobileNumber)
                .Parents_Status(parentsStatus)
                .Income(income)
                .Marital_Status(maritalStatus)
                .Profile_Photo_Path(profilePhotoPath)
                .Mobile_Number(mobileNumber)
                .Email_Id(emailId)
                .Residential_Address(residentialAddress)
                .Communication_Address(communicationAddress)
                .Hosteller(hosteller)
                .Hostel_Type(hostelType)
                .Bank_Name(bankName)
                .IFSC_Code(ifscCode)
                .Branch_Name(branchName)
                .Account_Number(accountNumber)
                .SSLC(sslc)
                .HSC_1_Year(hsc1Year)
                .HSC_2_Year(hsc2Year)
                .Diploma(diploma)
                .SSLC_File_Path(sslcFilePath)
                .HSC_1_Year_File_Path(hsc1YearFilePath)
                .HSC_2_Year_File_Path(hsc2YearFilePath)
                .Diploma_File_Path(diplomaFilePath)
                .Emis_Number(emisNumber)
                .First_Graduate(firstGraduate)
                .Special_Category(specialCategory)
                .build());
        profilePhoto.transferTo(new File(profilePhotoPath));
        sslcFile.transferTo(new File(sslcFilePath));
        hsc1YearFile.transferTo(new File(hsc1YearFilePath));
        hsc2YearFile.transferTo(new File(hsc2YearFilePath));
        diplomaFile.transferTo(new File(diplomaFilePath));
        if (student != null) {
            return "Student created successfully with ID: " + student.getId();
        }
        return null;
    }

    @Override
    public StudentDto getStudentById(Long Id) {
        Student student = studentRepository.findById(Id)
                .orElseThrow(() ->new ResourceNotFoundException("Student is not exist with the given id:" + Id));
        return StudentMapper.mapToStudentDto(student);
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> student=studentRepository.findAll();
        return  student.stream().map((students) -> StudentMapper.mapToStudentDto(students))
                .collect(Collectors.toList());
    }

    /*@Override
    public StudentDto updateStudent(Long id, StudentDto updatedStudent) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("Student is not exist with the given id:" + id));

        Student updatedStudentObj=studentRepository.save(student);
        return StudentMapper.mapToStudentDto(updatedStudentObj);
    }
*/
    @Override
    public StudentDto updateStudent(Long id, StudentDto updatedStudent) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student is not exist with the given id:" + id));

        // Update student fields with the data from updatedStudent
        student.setFirst_Name(updatedStudent.getFirst_Name());
        // Repeat this process for other fields...

        // Save the updated student
        Student updatedStudentObj = studentRepository.save(student);
        return StudentMapper.mapToStudentDto(updatedStudentObj);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("Student is not exist with the given id:" + id));
        studentRepository.deleteById(id);
    }
}
