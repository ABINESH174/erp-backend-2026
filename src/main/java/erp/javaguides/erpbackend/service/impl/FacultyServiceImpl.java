package erp.javaguides.erpbackend.service.impl;

import erp.javaguides.erpbackend.dto.requestDto.FacultyRequestDto;
import erp.javaguides.erpbackend.dto.requestDto.StudentDto;
import erp.javaguides.erpbackend.dto.responseDto.BonafideResponseDto;
import erp.javaguides.erpbackend.dto.responseDto.FacultyResponseDto;
import erp.javaguides.erpbackend.entity.Faculty;
import erp.javaguides.erpbackend.entity.Hod;
import erp.javaguides.erpbackend.entity.Student;
import erp.javaguides.erpbackend.enums.BonafideStatus;
import erp.javaguides.erpbackend.enums.PursuingYear;
import erp.javaguides.erpbackend.exception.ResourceNotFoundException;
import erp.javaguides.erpbackend.mapper.BonafideMapper;
import erp.javaguides.erpbackend.mapper.FacultyMapper;
import erp.javaguides.erpbackend.mapper.StudentMapper;
import erp.javaguides.erpbackend.repository.BonafideRepository;
import erp.javaguides.erpbackend.repository.FacultyRepository;
import erp.javaguides.erpbackend.repository.HodRepository;
import erp.javaguides.erpbackend.repository.StudentRepository;
import erp.javaguides.erpbackend.service.FacultyService;
import erp.javaguides.erpbackend.utility.UtilityService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;
    private final BonafideRepository bonafideRepository;
    private final HodRepository hodRepository;

    private final UtilityService utilityService;

    @Override
    public FacultyResponseDto createFaculty(FacultyRequestDto facultyRequestDto) throws Exception {

        Optional<Faculty> optionalFaculty = facultyRepository.findByEmail(facultyRequestDto.getEmail());
        if (optionalFaculty.isPresent()) {
            throw new Exception("Email already exists");
        }

        Faculty savedFaculty;

        try {
            
            Hod hod = hodRepository.findByDiscipline(facultyRequestDto.getDiscipline())
                    .orElseThrow(()->new ResourceNotFoundException("Hod not found with discipline:"));

            Faculty faculty = FacultyMapper.mapToFaculty(facultyRequestDto);
            hod.addFaculty(faculty);

            utilityService.addEmailToAuthentication(faculty.getEmail(),faculty.getEmail());

            savedFaculty = facultyRepository.save(faculty);
        } catch (Exception e) {
            throw new Exception("Error creating faculty: " + e.getMessage());
        }

        return FacultyMapper.mapToFacultyResponseDto(savedFaculty);
    }

    @Override
    public FacultyResponseDto getFacultyByEmail(String email) {
        Faculty faculty = facultyRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with email: " + email));
        return FacultyMapper.mapToFacultyResponseDto(faculty);
    }

    
    @Override
    public List<FacultyResponseDto> getAllFaculties() {
        List<Faculty> faculties = facultyRepository.findAll();
        return faculties.stream()
                .map(FacultyMapper::mapToFacultyResponseDto)
                .collect(Collectors.toList());
    }

    // Implementing faculty necessities for bonafide

    @Override
    public List<FacultyResponseDto> getAllFacultiesByDiscipline(String discipline) {
        List<Faculty> faculties = facultyRepository.findByDiscipline(discipline);
        return faculties.stream()
                .map(FacultyMapper::mapToFacultyResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public FacultyResponseDto getFacultyByFacultyId(Long facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with ID: " + facultyId));
        return FacultyMapper.mapToFacultyResponseDto(faculty);
    }

    @Override
    public FacultyResponseDto getFacultyByDisciplineAndBatch(String discipline, String handlingBatch) {
        Optional<Faculty> optionalFaculty = facultyRepository.findByDisciplineAndHandlingBatch(discipline, handlingBatch);
        if (optionalFaculty.isPresent()) {
            return FacultyMapper.mapToFacultyResponseDto(optionalFaculty.get());
        } else {
            throw new ResourceNotFoundException("Faculty not found with discipline: " + discipline + " and handling batch: " + handlingBatch);
        }
    }

    @Override
    public FacultyResponseDto assignFacultyWithStudents(String email, String batch, String discipline) {
        List<Student> studentsList = new ArrayList<>();
        Faculty faculty = facultyRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with email: " + email));
        faculty.setHandlingBatch(batch);
            
        studentsList = studentRepository.findByDisciplineAndBatch(discipline, batch);
        
        if (studentsList.isEmpty()) {
            throw new ResourceNotFoundException("No students found with discipline: " + faculty.getDiscipline() + " and batch: " + batch);
        }
        for (Student student : studentsList) {
            faculty.addStudent(student);
        }
        Faculty updatedFaculty = facultyRepository.save(faculty);
        return FacultyMapper.mapToFacultyResponseDto(updatedFaculty);
    }

    @Override
    public FacultyResponseDto dismissFacultyWithStudents(String email) {
        Faculty faculty = facultyRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with email: " + email));
        List<Student> studentsList = new ArrayList<>(faculty.getStudents());
        for(Student student:studentsList){
            faculty.removeStudent(student);
        }
        faculty.setHandlingBatch(null);
        Faculty updatedFaculty = facultyRepository.save(faculty);
        return FacultyMapper.mapToFacultyResponseDto(updatedFaculty);
    }

    @Override
    public List<StudentDto> getAllStudentsByFacultyId(Long facultyId) {
        List<Student> students = studentRepository.findByFacultyFacultyId(facultyId);
        List<StudentDto> studentResponseDtos = students
                    .stream()
                    .map(StudentMapper::mapToStudentWithFilesDto) 
                    .collect(Collectors.toList());
        return studentResponseDtos;
    }

    // Bonafide neccessities
    @Override
    public List<BonafideResponseDto> getPendingBonafidesByFacultyId(Long facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with ID: " + facultyId));
        List<BonafideResponseDto> bonafideResponseDtos = bonafideRepository.findByBonafideStatusAndStudentFacultyFacultyId(BonafideStatus.PENDING, faculty.getFacultyId())
                .stream()
                .map(BonafideMapper::mapToBonafideResponseDto)
                .collect(Collectors.toList());
        return bonafideResponseDtos;
    }

    @Override
    public List<FacultyResponseDto> getAllUnassignedFaculties(String discipline) {
        List<Faculty> faculties;
        List<String> coreDepartments = List.of("Computer Science and Engineering",
                "Civil Engineering",
                "Electrical and Electronic Engineering",
                "Electronics and Communication Engineering",
                "Mechanical Engineering");

        if(coreDepartments.contains(discipline)){
             faculties = facultyRepository.findByDisciplineAndStudentsIsEmpty(discipline);
        } else {
            faculties = facultyRepository.findByDepartmentNotInAndStudentsIsEmpty(coreDepartments);
        }
        return faculties.stream()
                .map(FacultyMapper::mapToFacultyResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public FacultyResponseDto assignFacultyUsingDisciplineYearAndClass(String facultyEmail, String discipline, PursuingYear year, String classSection) {
        Faculty faculty = facultyRepository.findByEmail(facultyEmail)
                                .orElseThrow(()->new ResourceNotFoundException("Faculty not found with email :"+ facultyEmail));
                
        List<Student> students = studentRepository.findByDisciplineAndSemesterInAndClassSection(discipline, utilityService.getListOfSemesterFromYear(year), classSection);

        if (students.isEmpty()) {
            throw new ResourceNotFoundException("No students found with discipline: " + discipline);
        }

        faculty.setHandlingBatch(students.getFirst().getBatch());

        for(Student student : students) {
                    faculty.addStudent(student);
        }

        return FacultyMapper.mapToFacultyResponseDto(facultyRepository.save(faculty));
    }

}
