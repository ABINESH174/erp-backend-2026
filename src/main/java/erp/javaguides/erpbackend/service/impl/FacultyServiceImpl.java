package erp.javaguides.erpbackend.service.impl;

import erp.javaguides.erpbackend.dto.FacultyDto;
import erp.javaguides.erpbackend.dto.StudentWithFilesDto;
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

        // Fetch academics based on discipline and academic year
        List<Student> studentsList = studentRepository.findByDisciplineAndAcademicYear(
                faculty.getDiscipline(), faculty.getHandlingBatch()
        );

        // Combine student and academic details into CombinedDto
        List<StudentWithFilesDto> studentWithFilesDtos = studentsList.stream().map(student -> {
            StudentWithFilesDto studentWithFilesDto = StudentMapper.mapToStudentWithFilesDto(student);
            return studentWithFilesDto;
        }).collect(Collectors.toList());

        // Convert faculty entity to DTO
        FacultyDto facultyDto = FacultyMapper.mapToFacultyDto(faculty);
        facultyDto.setStudents(studentWithFilesDtos);

        return facultyDto;
    }

    @Override
    public List<FacultyDto> getAllFaculties() {
        List<Faculty> faculties = facultyRepository.findAll();
        return faculties.stream()
                .map(FacultyMapper::mapToFacultyDto)
                .collect(Collectors.toList());
    }
}
