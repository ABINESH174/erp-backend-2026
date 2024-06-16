package erp.javaguides.erpbackend.service.impl;

import erp.javaguides.erpbackend.dto.StudentDto;
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
    private static final String FOLDER_PATH = "C:\\Users\\m.uvasri\\Desktop\\FileSystem";

    @Override
    public String createStudentWithFilesDto(StudentWithFilesDto studentWithFilesDto) throws Exception {
        if (studentWithFilesDto == null || studentWithFilesDto.getRegister_No() == null) {
            throw new IllegalArgumentException("StudentWithFilesDto or Register Number cannot be null");
        }
        Optional<Student> optionalStudent = studentRepository.findById(studentWithFilesDto.getRegister_No());
        if(optionalStudent.isPresent()){
            throw new Exception("Register Number already exists");
        }
        String firstname = studentWithFilesDto.getFirst_Name();
        String registerNo = studentWithFilesDto.getRegister_No();
        String userFolderPath = Paths.get(FOLDER_PATH, registerNo).toString();
        createFolderIfNotExist(userFolderPath);

        // Map StudentWithFilesDto to Student object
        Student student = StudentMapper.mapToStudentWithFilesDto(studentWithFilesDto);

        // Save files and update file paths in the Student object
        student.setProfile_Photo_Path(saveFile(firstname, userFolderPath, "profilephoto", studentWithFilesDto.getProfile_Photo()));
        student.setSslc_File_Path(saveFile(firstname, userFolderPath, "sslcfile", studentWithFilesDto.getSslc_File()));
        student.setHsc_1_Year_File_Path(saveFile(firstname, userFolderPath, "hsc1file", studentWithFilesDto.getHsc_1_Year_File()));
        student.setHsc_2_Year_File_Path(saveFile(firstname, userFolderPath, "hsc2file", studentWithFilesDto.getHsc_2_Year_File()));
        student.setDiploma_File_Path(saveFile(firstname, userFolderPath, "diplomafile", studentWithFilesDto.getDiploma_File()));

        // Save the Student object
        student = studentRepository.save(student);

        return "Student created successfully with RegisterNo: " + student.getRegister_No();
    }

    @Override
    public void createFolderIfNotExist(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }
    public String saveFile(String firstName, String userFolderPath, String fileType, MultipartFile file) throws IOException {
        if(file!=null){
            String fileName = firstName + "_" + fileType + "." + getFileExtension(file.getOriginalFilename());
            String filePath = Paths.get(userFolderPath, fileName).toString();
            file.transferTo(new File(filePath));
            return filePath;
        }
        return null;
    }

    public String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
    @Override
    public StudentWithFilesDto getStudentWithFilesDtoByRegisterNo(String register_No) {
        Student student = studentRepository.findById(register_No)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with Register Number: " + register_No));

        // Read files from their stored paths
        byte[] profilePhotoContent = readFile(student.getProfile_Photo_Path());
        byte[] sslcFileContent = readFile(student.getSslc_File_Path());
        byte[] hsc1YearFileContent = readFile(student.getHsc_1_Year_File_Path());
        byte[] hsc2YearFileContent = readFile(student.getHsc_2_Year_File_Path());
        byte[] diplomaFileContent = readFile(student.getDiploma_File_Path());

        StudentWithFilesDto studentWithFilesDto = StudentMapper.mapToStudentWithFilesDto(student); // Map StudentDto to StudentWithFilesDto

        studentWithFilesDto.setProfilePhotoContent(profilePhotoContent);
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
    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(StudentMapper::mapToStudentDto).collect(Collectors.toList());
    }

    @Override
    public StudentDto updateStudent(String register_No, StudentDto updatedStudent) {
        Student student = studentRepository.findById(register_No)
                .orElseThrow(() -> new ResourceNotFoundException("Student is not exist with the given Register number:" + register_No));

        // Update student fields with the data from updatedStudent
        student.setFirst_Name(updatedStudent.getFirst_Name());
        // Repeat this process for other fields...

        // Save the updated student
        Student updatedStudentObj = studentRepository.save(student);
        return StudentMapper.mapToStudentDto(updatedStudentObj);
    }

    @Override
    public void deleteStudent(String register_No) {
        Student student = studentRepository.findById(register_No)
                .orElseThrow(() -> new ResourceNotFoundException("Student does not exist with the given Register number:" + register_No));
        studentRepository.deleteById(register_No);
    }
}