package erp.javaguides.erpbackend.service.impl;

import erp.javaguides.erpbackend.dto.FacultyDto;
import erp.javaguides.erpbackend.entity.Faculty;
import erp.javaguides.erpbackend.mapper.FacultyMapper;
import erp.javaguides.erpbackend.repository.FacultyRepository;
import erp.javaguides.erpbackend.service.FacultyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}
