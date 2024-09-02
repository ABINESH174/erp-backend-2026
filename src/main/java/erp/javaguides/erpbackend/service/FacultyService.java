package erp.javaguides.erpbackend.service;

import erp.javaguides.erpbackend.dto.FacultyDto;

import java.util.List;

public interface FacultyService {
    FacultyDto createFaculty(FacultyDto facultyDto)throws Exception;
    FacultyDto getFacultyByEmail(String email);
    FacultyDto getFacultyByEmail(String email, String className, String batchYear);
    FacultyDto getFacultyWithStudent(String email);
    FacultyDto getFaculty(String hodEmail);
    List<FacultyDto> getAllFaculties();
}
