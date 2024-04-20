package erp.javaguides.erpbackend.controller;

import erp.javaguides.erpbackend.dto.StudentDto;
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
                                           @RequestParam("guardians_Name") String guardiansName,
                                           @RequestParam("guardians_Occupation") String guardiansOccupation,
                                           @RequestParam("guardians_Mobile_Number") String guardiansMobileNumber,
                                           @RequestParam("parents_Status") String parentsStatus,
                                           @RequestParam("income") String income,
                                           @RequestParam("marital_Status") String maritalStatus,
                                           @RequestParam("profile_Photo") MultipartFile profilePhoto,
                                           @RequestParam("mobile_Number") String mobileNumber,
                                           @RequestParam("email_Id") String emailId,
                                           @RequestParam("residential_Address") String residentialAddress,
                                           @RequestParam("communication_Address") String communicationAddress,
                                           @RequestParam("hosteller") String hosteller,
                                           @RequestParam("hostel_Type") String hostelType,
                                           @RequestParam("bank_Name") String bankName,
                                           @RequestParam("ifsc_Code") String ifscCode,
                                           @RequestParam("branch_Name") String branchName,
                                           @RequestParam("account_Number") String accountNumber,
                                           @RequestParam("sslc") String sslc,
                                           @RequestParam("hsc_1_Year") String hsc1Year,
                                           @RequestParam("hsc_2_Year") String hsc2Year,
                                           @RequestParam("diploma") String diploma,
                                           @RequestParam("sslc_File") MultipartFile sslcFile,
                                           @RequestParam("hsc_1_Year_File") MultipartFile hsc1YearFile,
                                           @RequestParam("hsc_2_Year_File") MultipartFile hsc2YearFile,
                                           @RequestParam("diploma_File") MultipartFile diplomaFile,
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

    //Build Get  Employee REST API
    @GetMapping("{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable("id") Long Id){
        StudentDto studentDto=studentService.getStudentById(Id);
        return ResponseEntity.ok(studentDto);
    }

    //Build GetAllEmployees REST API
    @GetMapping
    public ResponseEntity<List<StudentDto>>getAllStudents(){
        List<StudentDto> students=studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    //Build Update Student REST API
    @PutMapping("{id}")
    public ResponseEntity<StudentDto>updateStudent(@PathVariable("id") Long id,@RequestBody StudentDto updatedStudent){
       StudentDto studentDto=studentService.updateStudent(id,updatedStudent);
       return ResponseEntity.ok(studentDto);
    }

    //Build Delete Student REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable("id") Long id){
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted successfully");
    }
}
