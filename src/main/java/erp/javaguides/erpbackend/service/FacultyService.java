package erp.javaguides.erpbackend.service;

import erp.javaguides.erpbackend.dto.requestDto.FacultyDto;
// import erp.javaguides.erpbackend.dto.responseDto.FacultyResponseDto;

import java.util.List;

public interface FacultyService {
    FacultyDto createFaculty(FacultyDto facultyDto)throws Exception;
    FacultyDto getFacultyByEmail(String email);
    FacultyDto getFacultyByEmail(String email, String className, String batch);
    FacultyDto getFacultyWithStudent(String email);
    FacultyDto addClassFaculty(String email, FacultyDto facultyDto) ;
    FacultyDto removeClassFaculty(String email, String index) ;
    FacultyDto getFaculty(String hodEmail);
    List<FacultyDto> getAllFaculties();
}
