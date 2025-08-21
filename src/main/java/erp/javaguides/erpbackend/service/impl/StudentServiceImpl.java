package erp.javaguides.erpbackend.service.impl;

import erp.javaguides.erpbackend.dto.requestDto.StudentDto;
import erp.javaguides.erpbackend.dto.responseDto.StudentResponseDto;
import erp.javaguides.erpbackend.entity.Faculty;
import erp.javaguides.erpbackend.entity.Student;
import erp.javaguides.erpbackend.enums.PursuingYear;
import erp.javaguides.erpbackend.exception.InternalServerErrorException;
import erp.javaguides.erpbackend.exception.ResourceNotFoundException;
import erp.javaguides.erpbackend.mapper.StudentMapper;
import erp.javaguides.erpbackend.repository.FacultyRepository;
import erp.javaguides.erpbackend.repository.StudentRepository;
import erp.javaguides.erpbackend.service.StudentService;
import erp.javaguides.erpbackend.utility.UtilityService;
import lombok.RequiredArgsConstructor;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
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
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    private final UtilityService utilityService;

    @Value("${student.details.base-path}")
    private String FOLDERPATH;

    @Override
    public String createStudent(StudentDto studentDto) throws Exception {
        try {
            if (studentDto == null || studentDto.getRegisterNo() == null) {
                throw new IllegalArgumentException("StudentDto or Register Number cannot be null");
            }
            Optional<Student> optionalStudent = studentRepository.findByRegisterNo(studentDto.getRegisterNo());

            if (optionalStudent.isPresent() && !(optionalStudent.get().getFirstName() == null)) {
                throw new Exception("Register Number already exists");
//                updateStudent(studentDto.getRegisterNo(),studentDto);
            }

            // Add email to authentication table
            utilityService.addEmailToAuthentication(studentDto.getRegisterNo(),studentDto.getEmailid());

            String firstName = studentDto.getFirstName();
            String registerNo = studentDto.getRegisterNo();

            // Folder Path with structure: "/basepath/registerNo"
            String userFolderPath = Paths.get(FOLDERPATH, registerNo).toString();
            createFolderIfNotExist(userFolderPath);

            // Map StudentDto to Student object
            Student student = StudentMapper.mapToStudentWithFilesDto(studentDto);

            if (!(optionalStudent.get().getFaculty().getFacultyId() == null)) {
                student.setFaculty(facultyRepository.findByFacultyId(optionalStudent.get().getFaculty().getFacultyId()));
            }

            // Convert Base64 strings to MultipartFile and save files
            student.setCommunityCertificatePath(saveFile(firstName, userFolderPath, "communityCertificate",
                    base64ToMultipartFile(studentDto.getCommunityCertificate(), "communityCertificate")));
            student.setProfilePhotoPath(saveFile(firstName, userFolderPath, "profilephoto",
                    base64ToMultipartFile(studentDto.getPassbook(), "profilephoto")));
            student.setPassbookPath(saveFile(firstName, userFolderPath, "passbook",
                    base64ToMultipartFile(studentDto.getPassbook(), "passbook")));
            student.setFirstGraduateFilePath(saveFile(firstName, userFolderPath, "firstGraduateFile",
                    base64ToMultipartFile(studentDto.getFirstGraduateFile(), "firstGraduateFile")));
            student.setSpecialCategoryFilePath(saveFile(firstName, userFolderPath, "specialCategoryFile",
                    base64ToMultipartFile(studentDto.getSpecialCategoryFile(), "specialCategoryFile")));
            student.setSslcFilePath(saveFile(firstName, userFolderPath, "sslcfile",
                    base64ToMultipartFile(studentDto.getSslcFile(), "sslcfile")));
            student.setHscFirstYearFilePath(saveFile(firstName, userFolderPath, "hsc1file",
                    base64ToMultipartFile(studentDto.getHsc1YearFile(), "hsc1file")));
            student.setHscSecondYearFilePath(saveFile(firstName, userFolderPath, "hsc2file",
                    base64ToMultipartFile(studentDto.getHsc2YearFile(), "hsc2file")));
            student.setDiplomaFilePath(saveFile(firstName, userFolderPath, "diplomafile",
                    base64ToMultipartFile(studentDto.getDiplomaFile(), "diplomafile")));
            student.setAadharCardFilePath(saveFile(firstName,userFolderPath,"aadharCardFile",
                    base64ToMultipartFile(studentDto.getAadharCardFile(),"aadharCardFile")));



            // Save the Student object
            student = studentRepository.save(student);

            return "Student created successfully with RegisterNo: " + student.getRegisterNo();
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid input data", ex);
        } catch (IOException ex) {
            throw new BadRequestException("File handling error", ex);
        } catch (Exception ex) {
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
            return "File not found";
        }

        // Sanitize the firstName for filename
        String sanitizedFirstName = sanitizeForFilename(firstName);

        // Determine the file extension
        String originalFileExtension = getFileExtension(file.getOriginalFilename());
        if (originalFileExtension.isEmpty()) {
            originalFileExtension = "dat"; // default extension if missing
        }

        // Generate a unique filename with original extension
        String fileName = sanitizedFirstName + "_" + fileType + "." + originalFileExtension;
        String filePath = Paths.get(userFolderPath, fileName).toString();

        // Save the file
        try {
            Files.write(Paths.get(filePath), file.getBytes());
            return filePath;
        } catch (IOException e) {
            throw e;
        }
    }

    public String sanitizeForFilename(String input) {
        return input.replaceAll("[^a-zA-Z0-9.\\-]", "_"); // Replace illegal characters with underscores
    }

    public String getFileExtension(String originalFileName) {
        if (originalFileName == null || originalFileName.isEmpty()) {
            return "";
        }

        int lastDotIndex = originalFileName.lastIndexOf('.');
        if (lastDotIndex != -1 && lastDotIndex < originalFileName.length() - 1) {
            return originalFileName.substring(lastDotIndex + 1).toLowerCase(); // Ensure the extension is lowercase
        }

        return ""; // Return empty if no extension found
    }

    @Override
    public StudentDto getStudentByRegisterNo(String registerNo) {
        Student student = studentRepository.findByRegisterNo(registerNo)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with Register Number: " + registerNo));
        byte[] communityCertificateContent = readFile(student.getCommunityCertificatePath());
        byte[] prfilePhotoContent = readFile(student.getProfilePhotoPath());
        byte[] passbookContent = readFile(student.getPassbookPath());
        byte[] firstGraduateFileContent = readFile(student.getFirstGraduateFilePath());
        byte[] specialCategoryFileContent=readFile(student.getSpecialCategoryFilePath());
        byte[] sslcFileContent = readFile(student.getSslcFilePath());
        byte[] hsc1YearFileContent = readFile(student.getHscFirstYearFilePath());
        byte[] hsc2YearFileContent = readFile(student.getHscSecondYearFilePath());
        byte[] diplomaFileContent = readFile(student.getDiplomaFilePath());

        StudentDto studentDto = StudentMapper.mapToStudentWithFilesDto(student);
        studentDto.setCommunityCertificateContent(communityCertificateContent);
        studentDto.setProfilePhotoContent(prfilePhotoContent);
        studentDto.setPassbookcontent(passbookContent);
        studentDto.setFirstGraduateFileContent(firstGraduateFileContent);
        studentDto.setSpecialCategoryFileContent(specialCategoryFileContent);
        studentDto.setSslcFileContent(sslcFileContent);
        studentDto.setHsc1YearFileContent(hsc1YearFileContent);
        studentDto.setHsc2YearFileContent(hsc2YearFileContent);
        studentDto.setDiplomaFileContent(diplomaFileContent);

        return studentDto;
    }

    public byte[] readFile(String filePath) {
        if (filePath != null&& !filePath.equals("File not found")) {
            try {
                Path path = Paths.get(filePath);
                return Files.readAllBytes(path);
            } catch (IOException e) {
                throw new InternalServerErrorException("Error reading file: " + filePath, e);
            }
        }
        return null;
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(StudentMapper::mapToStudentWithFilesDto).collect(Collectors.toList());
    }
    @Override
    public List<StudentDto> getAllStudentsByDiscipline(String discipline) {
        List<Student> Students = studentRepository.findByDiscipline(discipline);
        return Students.stream()
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
            }

            byte[] fileBytes = Base64.getDecoder().decode(base64Data);
            return new MockMultipartFile(fileName + "." + fileExtension, fileName + "." + fileExtension, mimeType, fileBytes);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error converting Base64 string to MultipartFile", e);
        }
    }

    private String getFileExtensionFromMimeType(String mimeType) {
        // Example of basic MIME type to file extension mapping
        return switch (mimeType) {
            case "image/jpeg" -> "jpg";
            case "image/png" -> "png";
            case "application/pdf" -> "pdf";
            case "text/plain" -> "txt";
            default -> null;
        };
    }

    public String updateStudent(String registerNo, StudentDto studentDto) throws Exception {
        // Find the existing student by register number
        Student existingStudent = studentRepository.findByRegisterNo(registerNo)
                .orElseThrow(() -> new Exception("Student not found with register number: " + registerNo));
        existingStudent.setIncome(studentDto.getIncome());
        existingStudent.setParentStatus(studentDto.getParentsStatus());
        existingStudent.setSemester(studentDto.getSemester());
        existingStudent.setStudentStatus(studentDto.getStudentStatus());
        // Save updated student back to the database
        studentRepository.save(existingStudent);

        return "Student updated successfully";
    }

    @Override
    public List<StudentResponseDto> getAllStudentsBySemesterAndDiscipline(String semester, String discipline) {
        List<Student> students = studentRepository.findBySemesterAndDiscipline(semester, discipline);
        return students.stream()
                .map(student -> new StudentResponseDto(
                        student.getRegisterNo(),
                        student.getFirstName(),
                        student.getLastName(),
                        student.getGender(),
                        student.getEmailId(),
                        student.getMobileNumber(),
                        student.getDateOfBirth(),
                        student.getProgramme(),
                        student.getDiscipline(),
                        student.getDepartment(),
                        student.getClassSection(),
                        student.getSemester(),
                        student.getBatch(),
                        student.getCgpa(),
                        (student.getFaculty()==null)? null:student.getFaculty().getFacultyId()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentResponseDto> getAllStudentsByBatchAndDiscipline(String batch, String discipline) {
        List<Student> students = studentRepository.findByDisciplineAndBatch(discipline, batch);
        return students.stream()
                .map(student -> new StudentResponseDto(
                        student.getRegisterNo(),
                        student.getFirstName(),
                        student.getLastName(),
                        student.getGender(),
                        student.getEmailId(),
                        student.getMobileNumber(),
                        student.getDateOfBirth(),
                        student.getProgramme(),
                        student.getDiscipline(),
                        student.getDepartment(),
                        student.getClassSection(),
                        student.getSemester(),
                        student.getBatch(),
                        student.getCgpa(),
                        (student.getFaculty()==null)? null:student.getFaculty().getFacultyId()
                ))
                .collect(Collectors.toList());
    }

    // Required for EEE and Mechanical displines as they have two sections...

    @Override
    public List<StudentResponseDto> getAllStudentsByDisciplineAndClassSection(String discipline, String classSection) {
        List<Student> students = studentRepository.findByDisciplineAndClassSection(discipline,classSection);
        return students
                .stream()
                .map(StudentMapper::mapToStudentResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentResponseDto> getAllStudentsByDisciplineAndClassSectionAndBatch(String discipline,
            String classSection, String batch) {
                List<Student> students = studentRepository.findByDisciplineAndClassSectionAndBatch(discipline, classSection, batch);
                return students
                        .stream()
                        .map(StudentMapper::mapToStudentResponseDto)
                        .collect(Collectors.toList());
    }


    // Hod neccessities

    @Override
    public List<StudentDto> getAllStudentsByDisciplineAndYear(String discipline, PursuingYear year) {
        return studentRepository.findByDisciplineAndSemesterIn(
                                        discipline, 
                                        utilityService.getListOfSemesterFromYear(year)
                                    )
                                    .stream()
                                    .map(StudentMapper::mapToStudentWithFilesDto)
                                    .collect(Collectors.toList());
    }

    @Override
    public List<StudentDto> getAllStudentsByDisciplineAndYearAndClassSection(String discipline, PursuingYear year,
            String classSection) {
        
        return studentRepository.findByDisciplineAndSemesterInAndClassSection(
                                        discipline, 
                                        utilityService.getListOfSemesterFromYear(year), 
                                        classSection
                                    )
                                    .stream()
                                    .map(StudentMapper::mapToStudentWithFilesDto)
                                    .collect(Collectors.toList());
    }

    //Faculty neccessities



    //Getting students by batch , required for faculty and hod
}