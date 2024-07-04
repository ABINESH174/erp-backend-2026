package erp.javaguides.erpbackend.service.impl;

import erp.javaguides.erpbackend.dto.StudentWithFilesDto;
import erp.javaguides.erpbackend.entity.Student;
import erp.javaguides.erpbackend.exception.ResourceNotFoundException;
import erp.javaguides.erpbackend.mapper.StudentMapper;
import erp.javaguides.erpbackend.repository.StudentRepository;
import erp.javaguides.erpbackend.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
//    private final AcademicsService academicsService;
    private static final String FOLDERPATH = "C:\\Users\\New\\Desktop\\FileSystem";

    @Override
    public String createStudent(StudentWithFilesDto studentWithFilesDto) throws Exception {
        if (studentWithFilesDto == null || studentWithFilesDto.getRegisterNo() == null) {
            throw new IllegalArgumentException("StudentWithFilesDto or Register Number cannot be null");
        }
        Optional<Student> optionalStudent = studentRepository.findById(studentWithFilesDto.getRegisterNo());
        if(optionalStudent.isPresent()){
            throw new Exception("Register Number already exists");
        }
        String firstname = studentWithFilesDto.getFirstName();
        String registerNo = studentWithFilesDto.getRegisterNo();
        String userFolderPath = Paths.get(FOLDERPATH, registerNo).toString();
        createFolderIfNotExist(userFolderPath);

        // Map StudentWithFilesDto to Student object
        Student student = StudentMapper.mapToStudentWithFilesDto(studentWithFilesDto);

        // Save files and update file paths in the Student object
        student.setProfilePhotoPath(saveFile(firstname, userFolderPath, "profilephoto", studentWithFilesDto.getProfilePhoto()));
        student.setPassbookPath(saveFile(firstname, userFolderPath, "passbook", studentWithFilesDto.getPassbook()));
        student.setSslcFilePath(saveFile(firstname, userFolderPath, "sslcfile", studentWithFilesDto.getSslcFile()));
        student.setHsc1YearFilePath(saveFile(firstname, userFolderPath, "hsc1file", studentWithFilesDto.getHsc1YearFile()));
        student.setHsc2YearFilePath(saveFile(firstname, userFolderPath, "hsc2file", studentWithFilesDto.getHsc2YearFile()));
        student.setDiplomaFilePath(saveFile(firstname, userFolderPath, "diplomafile", studentWithFilesDto.getDiplomaFile()));

        // Save the Student object
        student = studentRepository.save(student);

        return "Student created successfully with RegisterNo: " + student.getRegisterNo();
    }

    @Override
    public void createFolderIfNotExist(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }
    public String saveFile(String firstName, String userFolderPath, String fileType, MultipartFile file) throws IOException {
        if(file==null || file.isEmpty()){
            return "File not found";
        }
        String fileName = firstName + "" + fileType + "." + getFileExtension(file.getOriginalFilename());
        String filePath = Paths.get(userFolderPath, fileName).toString();
        file.transferTo(new File(filePath));
        return filePath;
    }

    public String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
    @Override
    public StudentWithFilesDto getStudentByRegisterNo(String registerNo) {
        Student student = studentRepository.findById(registerNo)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with Register Number: " + registerNo));

        // Read files from their stored paths
        byte[] profilePhotoContent = readFile(student.getProfilePhotoPath());
        byte[] passbookcontent = readFile(student.getPassbookPath());
        byte[] sslcFileContent = readFile(student.getSslcFilePath());
        byte[] hsc1YearFileContent = readFile(student.getHsc1YearFilePath());
        byte[] hsc2YearFileContent = readFile(student.getHsc2YearFilePath());
        byte[] diplomaFileContent = readFile(student.getDiplomaFilePath());

        StudentWithFilesDto studentWithFilesDto = StudentMapper.mapToStudentWithFilesDto(student); // Map StudentDto to StudentWithFilesDto

        studentWithFilesDto.setProfilePhotoContent(profilePhotoContent);
        studentWithFilesDto.setPassbookcontent(passbookcontent);
        studentWithFilesDto.setSslcFileContent(sslcFileContent);
        studentWithFilesDto.setHsc1YearFileContent(hsc1YearFileContent);
        studentWithFilesDto.setHsc2YearFileContent(hsc2YearFileContent);
        studentWithFilesDto.setDiplomaFileContent(diplomaFileContent);

        return studentWithFilesDto;
    }

    public byte[] readFile(String filePath) {
        if (filePath != null) {
            try {
                Path path = Paths.get(filePath);
                return Files.readAllBytes(path);
            } catch (IOException e) {
                // Handle exception
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<StudentWithFilesDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(student -> {
            StudentWithFilesDto studentWithFilesDto = StudentMapper.mapToStudentWithFilesDto(student);
            return studentWithFilesDto;
        }).collect(Collectors.toList());
    }

    /*@Override
    public StudentDto updateStudent(String registerNo, StudentDto updatedStudent) {
        Student student = studentRepository.findById(registerNo)
                .orElseThrow(() -> new ResourceNotFoundException("Student is not exist with the given Register number:" + registerNo));

        // Update student fields with the data from updatedStudent
        student.setFirstName(updatedStudent.getFirstName());
        // Repeat this process for other fields...

        // Save the updated student
        Student updatedStudentObj = studentRepository.save(student);
        return StudentMapper.mapToStudentDto(updatedStudentObj);
    }

    @Override
    public void deleteStudent(String registerNo) {
        Student student = studentRepository.findById(registerNo)
                .orElseThrow(() -> new ResourceNotFoundException("Student does not exist with the given Register number:" + registerNo));
        studentRepository.deleteById(registerNo);
    }*/
}