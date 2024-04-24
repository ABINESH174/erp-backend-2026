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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private static final String FOLDER_PATH = "C:" + File.separator + "Users" + File.separator + "m.uvasri" + File.separator + "Desktop" + File.separator + "FileSystem";

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

        String userFolderPath = Paths.get(FOLDER_PATH, emailId).toString();
        createFolderIfNotExist(userFolderPath);

        String profilePhotoPath = saveFile(firstName, userFolderPath, "profilephoto", profilePhoto);
        String sslcFilePath = saveFile(firstName, userFolderPath, "sslcfile", sslcFile);
        String hsc1YearFilePath = saveFile(firstName, userFolderPath, "hsc1file", hsc1YearFile);
        String hsc2YearFilePath = saveFile(firstName, userFolderPath, "hsc2file", hsc2YearFile);
        String diplomaFilePath = saveFile(firstName, userFolderPath, "diplomafile", diplomaFile);

        Student student = studentRepository.save(Student.builder()
                .First_Name(firstName)
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

        return student != null ? "Student created successfully with EmailID: " + student.getEmail_Id() : null;
    }
    @Override
    public void createFolderIfNotExist(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }
    @Override
    public String saveFile(String firstName, String userFolderPath, String fileType, MultipartFile file) throws IOException {
        if(file!=null){
            String fileName = firstName + "_" + fileType + "." + getFileExtension(file.getOriginalFilename());
            String filePath = Paths.get(userFolderPath, fileName).toString();
            file.transferTo(new File(filePath));
            return filePath;
        }
        return null;
    }
    @Override
    public String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    @Override
    public StudentDto getStudentByEmailId(String Email_Id) {
        Student student = studentRepository.findById(Email_Id)
                .orElseThrow(() -> new ResourceNotFoundException("Student is not exist with the given EmailId:" + Email_Id));
        return StudentMapper.mapToStudentDto(student);
    }
    public byte[] readFile(String filePath) throws IOException {
        return Files.readAllBytes(Paths.get(filePath));
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(StudentMapper::mapToStudentDto).collect(Collectors.toList());
    }

    @Override
    public StudentDto updateStudent(String Email_Id, StudentDto updatedStudent) {
        Student student = studentRepository.findById(Email_Id)
                .orElseThrow(() -> new ResourceNotFoundException("Student is not exist with the given id:" + Email_Id));

        // Update student fields with the data from updatedStudent
        student.setFirst_Name(updatedStudent.getFirst_Name());
        // Repeat this process for other fields...

        // Save the updated student
        Student updatedStudentObj = studentRepository.save(student);
        return StudentMapper.mapToStudentDto(updatedStudentObj);
    }

    @Override
    public void deleteStudent(String Email_Id) {
        Student student = studentRepository.findById(Email_Id)
                .orElseThrow(() -> new ResourceNotFoundException("Student is not exist with the given id:" + Email_Id));
        studentRepository.deleteById(Email_Id);
    }
}
