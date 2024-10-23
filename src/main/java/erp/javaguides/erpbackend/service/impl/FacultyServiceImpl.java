package erp.javaguides.erpbackend.service.impl;

import erp.javaguides.erpbackend.dto.FacultyDto;
import erp.javaguides.erpbackend.dto.StudentDto;
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
        Optional<Faculty> optionalFaculty = facultyRepository.findById(facultyDto.getEmail());
        if (optionalFaculty.isPresent()) {
            throw new Exception("Email already exists");
        }
        Faculty faculty = FacultyMapper.mapToFaculty(facultyDto);
        Faculty savedFaculty = facultyRepository.save(faculty);
        return FacultyMapper.mapToFacultyDto(savedFaculty);
    }
    @Override
    public FacultyDto getFacultyByEmail(String email) {
        Faculty faculty = facultyRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with email: " + email));
        return getFacultyWithStudent(faculty, faculty.getDiscipline(), faculty.getHandlingBatch());
    }

    @Override
    public FacultyDto getFacultyByEmail(String email, String className, String batch) {
        Faculty faculty = facultyRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with email: " + email));
        return getFacultyWithStudent(faculty, className, batch);
    }
    @Override
    public FacultyDto getFacultyWithStudent(String email){
        Faculty faculty = facultyRepository.findById(email)
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
        Faculty hod = facultyRepository.findById(hodEmail)
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
    public FacultyDto updateFaculty(String email, FacultyDto facultyDto) {
        Faculty existingFaculty = facultyRepository.findById(email)
                .orElseThrow(() -> new RuntimeException("Faculty not found with email: " + email));

        // Update the fields with the new data from facultyDto
//        existingFaculty.setFirstName(facultyDto.getFirstName());
//        existingFaculty.setLastName(facultyDto.getLastName());
//        existingFaculty.setMobileNumber(facultyDto.getMobileNumber());
//        existingFaculty.setDiscipline(facultyDto.getDiscipline());
//        existingFaculty.setHandlingBatch(facultyDto.getHandlingBatch());
        existingFaculty.setSubject(facultyDto.getSubject());
        existingFaculty.setHandlingSemester(facultyDto.getHandlingSemester());
        existingFaculty.setHandlingDept(facultyDto.getHandlingDept());
        existingFaculty.setBatch(facultyDto.getBatch());

        // Save the updated faculty entity
        Faculty updatedFaculty = facultyRepository.save(existingFaculty);

        // Convert the updated entity to DTO and return
        return FacultyMapper.mapToFacultyDto(updatedFaculty);  // Assuming you have a mapper for entity-DTO conversion
    }

}
