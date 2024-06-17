package erp.javaguides.erpbackend.service;

import erp.javaguides.erpbackend.dto.FacultyDto;

public interface FacultyService {
    FacultyDto createFaculty(FacultyDto facultyDto)throws Exception;
}
