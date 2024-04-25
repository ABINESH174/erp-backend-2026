package erp.javaguides.erpbackend.controller;

import erp.javaguides.erpbackend.dto.StudentDto;
import erp.javaguides.erpbackend.dto.StudentWithFilesDto;
import erp.javaguides.erpbackend.entity.Student;
import erp.javaguides.erpbackend.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@CrossOrigin(origins = "http://localhost:3000/personal-form")
@AllArgsConstructor
@RestController
@RequestMapping("/api/student")
public class StudentController {
    private StudentService studentService;
    //Build Add Student REST API
    @PostMapping
    public ResponseEntity<?> createStudent(@RequestParam(value = "first_Name",required = false) String firstName,
                                           @RequestParam("last_Name") String lastName,
                                           @RequestParam("date_Of_Birth") String dateOfBirth,
                                           @RequestParam("gender") String gender,
                                           @RequestParam("aadhar_Number") String aadharNumber,
                                           @RequestParam("nationality") String nationality,
                                           @RequestParam("religion") String religion,
                                           @RequestParam("caste") String caste,
                                           @RequestParam("fathers_Name") String fathersName,
                                           @RequestParam("fathers_Occupation") String fathersOccupation,
                                           @RequestParam("fathers_Mobile_Number") String fathersMobileNumber,
                                           @RequestParam("mothers_Name") String mothersName,
                                           @RequestParam("mothers_Occupation") String mothersOccupation,
                                           @RequestParam("mothers_Mobile_Number") String mothersMobileNumber,
                                           @RequestParam("community") String community,
                                           @RequestParam(value = "guardians_Name",required = false) String guardiansName,
                                           @RequestParam(value = "guardians_Occupation",required = false) String guardiansOccupation,
                                           @RequestParam(value = "guardians_Mobile_Number",required = false) String guardiansMobileNumber,
                                           @RequestParam("parents_Status") String parentsStatus,
                                           @RequestParam("income") String income,
                                           @RequestParam("marital_Status") String maritalStatus,
                                           @RequestParam("profile_Photo") MultipartFile profilePhoto,
                                           @RequestParam("mobile_Number") String mobileNumber,
                                           @RequestParam("email_Id") String emailId,
                                           @RequestParam("residential_Address") String residentialAddress,
                                           @RequestParam("communication_Address") String communicationAddress,
                                           @RequestParam("hosteller") String hosteller,
                                           @RequestParam(value = "hostel_Type",required = false) String hostelType,
                                           @RequestParam("bank_Name") String bankName,
                                           @RequestParam("ifsc_Code") String ifscCode,
                                           @RequestParam("branch_Name") String branchName,
                                           @RequestParam("account_Number") String accountNumber,
                                           @RequestParam("sslc") String sslc,
                                           @RequestParam(value = "hsc_1_Year",required = false) String hsc1Year,
                                           @RequestParam(value = "hsc_2_Year",required = false) String hsc2Year,
                                           @RequestParam(value = "diploma",required = false) String diploma,
                                           @RequestParam("sslc_File") MultipartFile sslcFile,
                                           @RequestParam(value = "hsc_1_Year_File",required = false) MultipartFile hsc1YearFile,
                                           @RequestParam(value = "hsc_2_Year_File",required = false) MultipartFile hsc2YearFile,
                                           @RequestParam(value = "diploma_File",required = false) MultipartFile diplomaFile,
                                           @RequestParam("emis_Number") String emisNumber,
                                           @RequestParam("first_Graduate") String firstGraduate,
                                           @RequestParam("special_Category") String specialCategory
    ) throws IOException {
        String savedStudent=studentService.createStudent(firstName, lastName, dateOfBirth,
                 gender, aadharNumber,  nationality,  religion,
                 caste,  fathersName,  fathersOccupation,
                 fathersMobileNumber,  mothersName,  mothersOccupation,
                 mothersMobileNumber,  community,  guardiansName,
                 guardiansOccupation,  guardiansMobileNumber,  parentsStatus,
                 income,  maritalStatus,  profilePhoto,
                 mobileNumber,  emailId,  residentialAddress,
                 communicationAddress,  hosteller,  hostelType,
                 bankName,  ifscCode,  branchName,  accountNumber,
                 sslc,  hsc1Year,  hsc2Year,  diploma,
                 sslcFile,  hsc1YearFile,  hsc2YearFile,
                 diplomaFile,  emisNumber,  firstGraduate,
                 specialCategory);
//        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.OK)
                .body(savedStudent);
    }

    //Build Get Student REST API
    @GetMapping("{Email_Id}")
    public ResponseEntity<StudentWithFilesDto> getStudentById(@PathVariable("Email_Id") String Email_Id) {
        // Retrieve student data
        StudentDto studentDto = studentService.getStudentByEmailId(Email_Id);

        // Retrieve file content
        byte[] profilePhotoContent = null;
        byte[] sslcFileContent = null;
        byte[] hsc1YearFileContent = null;
        byte[] hsc2YearFileContent = null;
        byte[] diplomaFileContent = null;

        // Retrieve file paths from student data
        String profilePhotoPath = studentDto.getProfile_Photo_Path();
        String sslcFilePath = studentDto.getSSLC_File_Path();
        String hsc1YearFilePath = studentDto.getHSC_1_Year_File_Path();
        String hsc2YearFilePath = studentDto.getHSC_2_Year_File_Path();
        String diplomaFilePath = studentDto.getDiploma_File_Path();

        // Read file content if paths exist
        try {
            if (profilePhotoPath != null) {
                profilePhotoContent = studentService.readFile(profilePhotoPath);
            }
            if (sslcFilePath != null) {
                sslcFileContent = studentService.readFile(sslcFilePath);
            }
            if (hsc1YearFilePath != null) {
                hsc1YearFileContent = studentService.readFile(hsc1YearFilePath);
            }
            if (hsc2YearFilePath != null) {
                hsc2YearFileContent = studentService.readFile(hsc2YearFilePath);
            }
            if (diplomaFilePath != null) {
                diplomaFileContent = studentService.readFile(diplomaFilePath);
            }
            if(diplomaFilePath == null){
                diplomaFileContent = studentService.readFile(sslcFilePath);
            }
        } catch (IOException e) {
            // Handle file reading exception
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        // Create a DTO to hold both student data and file content
        StudentWithFilesDto studentWithFilesDto = new StudentWithFilesDto();
        studentWithFilesDto.setStudentDto(studentDto);
        studentWithFilesDto.setProfilePhotoContent(profilePhotoContent);
        studentWithFilesDto.setSslcFileContent(sslcFileContent);
        studentWithFilesDto.setHsc1YearFileContent(hsc1YearFileContent);
        studentWithFilesDto.setHsc2YearFileContent(hsc2YearFileContent);
        studentWithFilesDto.setDiplomaFileContent(diplomaFileContent);

        return ResponseEntity.ok(studentWithFilesDto);
    }



    //Build GetAllEmployees REST API
    @GetMapping
    public ResponseEntity<List<StudentDto>>getAllStudents(){
        List<StudentDto> students=studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    //Build Update Student REST API
    @PutMapping("{Email_Id}")
    public ResponseEntity<StudentDto>updateStudent(@PathVariable("Email_Id") String Email_Id,@RequestBody StudentDto updatedStudent){
       StudentDto studentDto=studentService.updateStudent(Email_Id,updatedStudent);
       return ResponseEntity.ok(studentDto);
    }

    //Build Delete Student REST API
    @DeleteMapping("{Email_Id}")
    public ResponseEntity<String> deleteStudent(@PathVariable("Email_Id") String Email_Id){
        studentService.deleteStudent(Email_Id);
        return ResponseEntity.ok("Student deleted successfully");
    }
}
