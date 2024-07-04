package erp.javaguides.erpbackend.service;

import erp.javaguides.erpbackend.dto.FacultyDto;

import java.util.List;

public interface FacultyService {
    FacultyDto createFaculty(FacultyDto facultyDto)throws Exception;
    FacultyDto getFacultyByEmail(String email);
    List<FacultyDto> getAllFaculties();
}
