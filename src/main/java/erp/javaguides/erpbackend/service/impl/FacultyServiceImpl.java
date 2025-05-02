package erp.javaguides.erpbackend.service.impl;

import erp.javaguides.erpbackend.dto.requestDto.FacultyDto;
import erp.javaguides.erpbackend.dto.requestDto.StudentDto;
// import erp.javaguides.erpbackend.dto.responseDto.FacultyResponseDto;
import erp.javaguides.erpbackend.entity.Faculty;
import erp.javaguides.erpbackend.entity.Student;
import erp.javaguides.erpbackend.exception.ResourceNotFoundException;
import erp.javaguides.erpbackend.mapper.FacultyMapper;
import erp.javaguides.erpbackend.mapper.StudentMapper;
import erp.javaguides.erpbackend.repository.FacultyRepository;
import erp.javaguides.erpbackend.repository.StudentRepository;
import erp.javaguides.erpbackend.service.FacultyService;
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

    @Override
    public FacultyDto createFaculty(FacultyDto facultyDto) throws Exception {
        Optional<Faculty> optionalFaculty = facultyRepository.findByEmail(facultyDto.getEmail());
        if (optionalFaculty.isPresent()) {
            throw new Exception("Email already exists");
        }
        Faculty faculty = FacultyMapper.mapToFaculty(facultyDto);
        System.out.println(faculty.getEmail());
        Faculty savedFaculty = facultyRepository.save(faculty);
        System.out.println(savedFaculty.getFirstName());
//        return FacultyMapper.mapToFacultyResponseDto(savedFaculty);
        return FacultyMapper.mapToFacultyDto(savedFaculty);
    }
    @Override
    public FacultyDto getFacultyByEmail(String email) {
        Faculty faculty = facultyRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with email: " + email));
        return getFacultyWithStudent(faculty, faculty.getDiscipline(), faculty.getHandlingBatch());
    }

    @Override
    public FacultyDto getFacultyByEmail(String email, String className, String batch) {
        Faculty faculty = facultyRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with email: " + email));
        return getFacultyWithStudent(faculty, className, batch);
    }
    @Override
    public FacultyDto getFacultyWithStudent(String email){
        Faculty faculty = facultyRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with email: " + email));
        List<Student> studentsList = studentRepository.findByDiscipline(faculty.getDiscipline());
        List<StudentDto> studentDtos = studentsList.stream()
                .map(StudentMapper::mapToStudentWithFilesDto)
                .collect(Collectors.toList());

        FacultyDto facultyDto = FacultyMapper.mapToFacultyDto(faculty);
        facultyDto.setStudents(studentDtos);
        return facultyDto;
    }
    private FacultyDto getFacultyWithStudent(Faculty faculty, String discipline, String batch) {
        List<Student> studentsList = studentRepository.findByDisciplineAndBatch(discipline, batch);
        List<StudentDto> studentDtos = studentsList.stream()
                .map(StudentMapper::mapToStudentWithFilesDto)
                .collect(Collectors.toList());

        FacultyDto facultyDto = FacultyMapper.mapToFacultyDto(faculty);
        facultyDto.setStudents(studentDtos);
        return facultyDto;
    }
    public FacultyDto getFaculty(String hodEmail){
        Faculty hod = facultyRepository.findByEmail(hodEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with email: " + hodEmail));

        List<Faculty> allFaculties = facultyRepository.findByDiscipline(hod.getDiscipline());

        List<FacultyDto> facultyDtos = allFaculties.stream()
                .filter(faculty -> !faculty.getEmail().equals(hod.getEmail()))
                .map(FacultyMapper::mapToFacultyDto)
                .collect(Collectors.toList());
        FacultyDto facultyDto = FacultyMapper.mapToFacultyDto(hod);
        facultyDto.setFaculties(facultyDtos);
        return facultyDto;
    }
    @Override
    public List<FacultyDto> getAllFaculties() {
        List<Faculty> faculties = facultyRepository.findAll();
        return faculties.stream()
                .map(FacultyMapper::mapToFacultyDto)
                .collect(Collectors.toList());
    }
    @Override
    public FacultyDto addClassFaculty(String email, FacultyDto facultyDto) {
        // Fetch existing faculty
        Faculty existingFaculty = facultyRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Faculty not found with email: " + email));

        // Update basic fields if needed
//        if (facultyDto.getFirstName() != null) {
//            existingFaculty.setFirstName(facultyDto.getFirstName());
//        }
//        if (facultyDto.getLastName() != null) {
//            existingFaculty.setLastName(facultyDto.getLastName());
//        }
//        if (facultyDto.getMobileNumber() != null) {
//            existingFaculty.setMobileNumber(facultyDto.getMobileNumber());
//        }
        // Add additional fields as necessary...

        // Ensure lists are initialized and add elements
        if (facultyDto.getSubjects() != null) {
            if (existingFaculty.getSubjects() == null) {
                existingFaculty.setSubjects(new ArrayList<>()); // Initialize the list if it's null
            }
            existingFaculty.getSubjects().addAll(facultyDto.getSubjects());
        }

        if (facultyDto.getHandlingSemesters() != null) {
            if (existingFaculty.getHandlingSemesters() == null) {
                existingFaculty.setHandlingSemesters(new ArrayList<>()); // Initialize the list if it's null
            }
            existingFaculty.getHandlingSemesters().addAll(facultyDto.getHandlingSemesters());
        }

        if (facultyDto.getHandlingDepartments() != null) {
            if (existingFaculty.getHandlingDepartments() == null) {
                existingFaculty.setHandlingDepartments(new ArrayList<>()); // Initialize the list if it's null
            }
            existingFaculty.getHandlingDepartments().addAll(facultyDto.getHandlingDepartments());
        }

        if (facultyDto.getBatches() != null) {
            if (existingFaculty.getBatches() == null) {
                existingFaculty.setBatches(new ArrayList<>()); // Initialize the list if it's null
            }
            existingFaculty.getBatches().addAll(facultyDto.getBatches());
        }

        // Save the updated faculty entity
        Faculty updatedFaculty = facultyRepository.save(existingFaculty);

        // Convert the updated entity to DTO and return
        return FacultyMapper.mapToFacultyDto(updatedFaculty);
    }

    @Override
    public FacultyDto removeClassFaculty(String email, String index) {
        Faculty existingFaculty = facultyRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Faculty not found with email: " + email));

        // Convert index to integer
        int idx;
        try {
            idx = Integer.parseInt(index);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid index format: " + index);
        }

        // Remove from batches, subjects, handlingDepts, and handlingSemesters if they exist
        removeFromList(existingFaculty.getBatches(), idx);
        removeFromList(existingFaculty.getSubjects(), idx);
        removeFromList(existingFaculty.getHandlingDepartments(), idx);
        removeFromList(existingFaculty.getHandlingSemesters(), idx);

        // Save the updated faculty entity
        facultyRepository.save(existingFaculty);

        // Convert to DTO and return
        return FacultyMapper.mapToFacultyDto(existingFaculty);
    }

    private void removeFromList(List<String> list, int index) {
        if (index >= 0 && index < list.size()) {
            list.remove(index);
        }
    }
}
