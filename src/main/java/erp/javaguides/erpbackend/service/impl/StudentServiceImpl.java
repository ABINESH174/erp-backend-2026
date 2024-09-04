package erp.javaguides.erpbackend.service.impl;

import erp.javaguides.erpbackend.dto.StudentWithFilesDto;
import erp.javaguides.erpbackend.entity.Student;
import erp.javaguides.erpbackend.exception.InternalServerErrorException;
import erp.javaguides.erpbackend.exception.ResourceNotFoundException;
import erp.javaguides.erpbackend.mapper.StudentMapper;
import erp.javaguides.erpbackend.repository.StudentRepository;
import erp.javaguides.erpbackend.service.StudentService;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private static final String FOLDERPATH = "J:\\FileSystem";
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Override
    public String createStudent(StudentWithFilesDto studentWithFilesDto) throws Exception {
        try {
            if (studentWithFilesDto == null || studentWithFilesDto.getRegisterNo() == null) {
                throw new IllegalArgumentException("StudentWithFilesDto or Register Number cannot be null");
            }
            Optional<Student> optionalStudent = studentRepository.findById(studentWithFilesDto.getRegisterNo());
            if (optionalStudent.isPresent()) {
                throw new Exception("Register Number already exists");
            }

            String firstName = studentWithFilesDto.getFirstName();
            String registerNo = studentWithFilesDto.getRegisterNo();
            String userFolderPath = Paths.get(FOLDERPATH, registerNo).toString();
            createFolderIfNotExist(userFolderPath);

            // Map StudentWithFilesDto to Student object
            Student student = StudentMapper.mapToStudentWithFilesDto(studentWithFilesDto);

            // Convert Base64 strings to MultipartFile and save files
            student.setProfilePhotoPath(saveFile(firstName, userFolderPath, "profilephoto",
                    base64ToMultipartFile(studentWithFilesDto.getPassbook(), "profilephoto")));
            student.setPassbookPath(saveFile(firstName, userFolderPath, "passbook",
                    base64ToMultipartFile(studentWithFilesDto.getPassbook(), "passbook")));
            student.setSslcFilePath(saveFile(firstName, userFolderPath, "sslcfile",
                    base64ToMultipartFile(studentWithFilesDto.getSslcFile(), "sslcfile")));
            student.setHsc1YearFilePath(saveFile(firstName, userFolderPath, "hsc1file",
                    base64ToMultipartFile(studentWithFilesDto.getHsc1YearFile(), "hsc1file")));
            student.setHsc2YearFilePath(saveFile(firstName, userFolderPath, "hsc2file",
                    base64ToMultipartFile(studentWithFilesDto.getHsc2YearFile(), "hsc2file")));
            student.setDiplomaFilePath(saveFile(firstName, userFolderPath, "diplomafile",
                    base64ToMultipartFile(studentWithFilesDto.getDiplomaFile(), "diplomafile")));

            // Save the Student object
            student = studentRepository.save(student);

            return "Student created successfully with RegisterNo: " + student.getRegisterNo();
        } catch (IllegalArgumentException ex) {
            logger.error("Invalid input data: " + ex.getMessage());
            throw new BadRequestException("Invalid input data", ex);
        } catch (IOException ex) {
            logger.error("File handling error: " + ex.getMessage());
            throw new BadRequestException("File handling error", ex);
        } catch (Exception ex) {
            logger.error("Unexpected error: " + ex.getMessage());
            throw new InternalServerErrorException("An unexpected error occurred", ex);
        }
    }

    @Override
    public void createFolderIfNotExist(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public String saveFile(String firstName, String userFolderPath, String fileType, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            logger.error("File not found or is empty");
            return "File not found";
        }

        // Sanitize the firstName for filename
        String sanitizedFirstName = sanitizeForFilename(firstName);

        // Determine the file extension
        String originalFileExtension = getFileExtension(file.getOriginalFilename());
        if (originalFileExtension.isEmpty()) {
            logger.warn("File extension is missing, defaulting to 'dat'");
            originalFileExtension = "dat"; // default extension if missing
        }

        // Generate a unique filename with original extension
        String fileName = sanitizedFirstName + "" + fileType + "" + System.currentTimeMillis() + "." + originalFileExtension;
        String filePath = Paths.get(userFolderPath, fileName).toString();

        // Save the file
        try {
            Files.write(Paths.get(filePath), file.getBytes());
            logger.info("File saved successfully at: " + filePath);
            return filePath;
        } catch (IOException e) {
            logger.error("Error saving file at: " + filePath, e);
            throw e;
        }
    }

    public String sanitizeForFilename(String input) {
        return input.replaceAll("[^a-zA-Z0-9\\.\\-]", "_"); // Replace illegal characters with underscores
    }

    public String getFileExtension(String originalFileName) {
        if (originalFileName == null || originalFileName.isEmpty()) {
            logger.error("Filename is null or empty");
            return "";
        }

        int lastDotIndex = originalFileName.lastIndexOf('.');
        if (lastDotIndex != -1 && lastDotIndex < originalFileName.length() - 1) {
            return originalFileName.substring(lastDotIndex + 1).toLowerCase(); // Ensure the extension is lowercase
        }

        logger.warn("No file extension found in filename: " + originalFileName);
        return ""; // Return empty if no extension found
    }

    @Override
    public StudentWithFilesDto getStudentByRegisterNo(String registerNo) {
        Student student = studentRepository.findById(registerNo)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with Register Number: " + registerNo));
        byte[] prfilePhotoContent = readFile(student.getProfilePhotoPath());
        byte[] passbookContent = readFile(student.getPassbookPath());
        byte[] sslcFileContent = readFile(student.getSslcFilePath());
        byte[] hsc1YearFileContent = readFile(student.getHsc1YearFilePath());
        byte[] hsc2YearFileContent = readFile(student.getHsc2YearFilePath());
        byte[] diplomaFileContent = readFile(student.getDiplomaFilePath());

        StudentWithFilesDto studentWithFilesDto = StudentMapper.mapToStudentWithFilesDto(student);

        studentWithFilesDto.setProfilePhotoContent(prfilePhotoContent);
        studentWithFilesDto.setPassbookcontent(passbookContent);
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
                logger.error("Error reading file at: " + filePath, e);
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
    @Override
    public List<StudentWithFilesDto> getAllStudentsByDiscipline(String discipline) {
        List<Student> cseStudents = studentRepository.findByDiscipline(discipline);
        return cseStudents.stream()
                .map(StudentMapper::mapToStudentWithFilesDto)
                .collect(Collectors.toList());
    }
    public MultipartFile base64ToMultipartFile(String base64, String fileName) {
        if (base64 == null || base64.isEmpty()) {
            return null;
        }

        try {
            String mimeType = "application/octet-stream"; // Default MIME type
            String base64Data = base64;

            if (base64.contains(",")) {
                String[] parts = base64.split(",");
                base64Data = parts[1];

                String dataPrefix = parts[0];
                Pattern pattern = Pattern.compile("data:(.*?);base64");
                Matcher matcher = pattern.matcher(dataPrefix);
                if (matcher.find()) {
                    mimeType = matcher.group(1);
                }
            }

            // Determine file extension based on MIME type
            String fileExtension = getFileExtensionFromMimeType(mimeType);
            if (fileExtension == null) {
                return null;
//                fileExtension = "dat"; // Fallback if extension cannot be determined
            }

            byte[] fileBytes = Base64.getDecoder().decode(base64Data);
            return new MockMultipartFile(fileName + "." + fileExtension, fileName + "." + fileExtension, mimeType, fileBytes);
        } catch (Exception e) {
            logger.error("Error converting Base64 string to MultipartFile: " + e.getMessage(), e);
            return null;
        }
    }

    private String getFileExtensionFromMimeType(String mimeType) {
        // Example of basic MIME type to file extension mapping
        switch (mimeType) {
            case "image/jpeg":
                return "jpg";
            case "image/png":
                return "png";
            case "application/pdf":
                return "pdf";
            case "text/plain":
                return "txt";
            // Add more mappings as needed
            default:
                return null;
        }
    }
}