package erp.javaguides.erpbackend.service.impl;

import erp.javaguides.erpbackend.dto.FacultyDto;
import erp.javaguides.erpbackend.dto.StudentWithFilesDto;
import erp.javaguides.erpbackend.entity.Faculty;
import erp.javaguides.erpbackend.entity.Student;
import erp.javaguides.erpbackend.exception.ResourceNotFoundException;
import erp.javaguides.erpbackend.mapper.FacultyMapper;
import erp.javaguides.erpbackend.mapper.StudentMapper;
import erp.javaguides.erpbackend.repository.FacultyRepository;
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
    public Faculty getFacultyByEmail(String email) {
        Faculty faculty = facultyRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with email: " + email));

        return faculty;

    }
    @Override
    public List<FacultyDto> getAllFaculties() {
        List<Faculty>  faculties= facultyRepository.findAll();
        return faculties.stream().map(faculty -> {
            FacultyDto facultyDto = FacultyMapper.mapToFacultyDto(faculty);
            return facultyDto;
        }).collect(Collectors.toList());
    }
}
