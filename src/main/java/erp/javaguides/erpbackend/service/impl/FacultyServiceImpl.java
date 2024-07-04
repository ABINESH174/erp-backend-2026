package erp.javaguides.erpbackend.service.impl;

import erp.javaguides.erpbackend.dto.AcademicsDto;
import erp.javaguides.erpbackend.dto.CombinedDto;
import erp.javaguides.erpbackend.dto.FacultyDto;
import erp.javaguides.erpbackend.dto.StudentWithFilesDto;
import erp.javaguides.erpbackend.entity.Academics;
import erp.javaguides.erpbackend.entity.Faculty;
import erp.javaguides.erpbackend.entity.Student;
import erp.javaguides.erpbackend.exception.ResourceNotFoundException;
import erp.javaguides.erpbackend.mapper.AcademicsMapper;
import erp.javaguides.erpbackend.mapper.FacultyMapper;
import erp.javaguides.erpbackend.mapper.StudentMapper;
import erp.javaguides.erpbackend.repository.AcademicsRepository;
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
    private final AcademicsRepository academicsRepository;

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
        List<Academics> academicsList = academicsRepository.findByDisciplineAndAcademicYear(
                faculty.getDiscipline(), faculty.getAcademicYear()
        );

        // Combine student and academic details into CombinedDto
        List<CombinedDto> combinedDtos = academicsList.stream().map(academic -> {
            Student student = studentRepository.findById(academic.getRegisterNo())
                    .orElseThrow(() -> new ResourceNotFoundException("Student not found with register number: " + academic.getRegisterNo()));

            StudentWithFilesDto studentWithFilesDto = StudentMapper.mapToStudentWithFilesDto(student);
            AcademicsDto academicDto = AcademicsMapper.mapToAcademicsDto(academic);
            return new CombinedDto(studentWithFilesDto, academicDto);
        }).collect(Collectors.toList());

        // Convert faculty entity to DTO
        FacultyDto facultyDto = FacultyMapper.mapToFacultyDto(faculty);
        facultyDto.setStudents(combinedDtos);

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
