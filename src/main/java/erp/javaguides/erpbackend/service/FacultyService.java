package erp.javaguides.erpbackend.service;

import erp.javaguides.erpbackend.dto.FacultyDto;
import erp.javaguides.erpbackend.entity.Faculty;

import java.util.List;

public interface FacultyService {
    FacultyDto createFaculty(FacultyDto facultyDto)throws Exception;
    Faculty getFacultyByEmail(String email);
    List<FacultyDto> getAllFaculties();
}
