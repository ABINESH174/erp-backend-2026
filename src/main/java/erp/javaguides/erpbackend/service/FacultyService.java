package erp.javaguides.erpbackend.service;

import erp.javaguides.erpbackend.dto.requestDto.FacultyRequestDto;
import erp.javaguides.erpbackend.dto.responseDto.BonafideResponseDto;
// import erp.javaguides.erpbackend.dto.responseDto.FacultyResponseDto;
import erp.javaguides.erpbackend.dto.responseDto.FacultyResponseDto;
import erp.javaguides.erpbackend.dto.responseDto.StudentResponseDto;

import java.util.List;

public interface FacultyService {
    FacultyResponseDto createFaculty(FacultyRequestDto facultyRequestDto)throws Exception;
    FacultyResponseDto getFacultyByEmail(String email);
    // FacultyDto getFacultyByEmail(String email, String className, String batch);
    // FacultyDto getFacultyWithStudent(String email);
    // FacultyDto addClassFaculty(String email, FacultyDto facultyDto) ;
    // FacultyDto removeClassFaculty(String email, String index) ;
    // FacultyDto getFaculty(String hodEmail);
    FacultyResponseDto getFacultyByFacultyId(Long facultyId);
    List<FacultyResponseDto> getAllFaculties();
    

    // Implementing faculty neccessities for bonafide

    List<FacultyResponseDto> getAllFacultiesByDiscipline(String discipline);
    FacultyResponseDto getFacultyByDisciplineAndBatch(String discipline, String handlingBatch);

    //Assigning and dismissing students to faculty using batch
    FacultyResponseDto assignFacultyWithStudents(String email, String batch);
    FacultyResponseDto dismissFacultyWithStudents(String email, String batch);

    
    List<StudentResponseDto> getAllStudentsByFacultyId(Long facultyId);
    
    //Bonafide neccessities
    List<BonafideResponseDto> getPendingBonafidesByFacultyId(Long facultyId);

    //hod neccessities
    List<FacultyResponseDto> getAllUnassignedFaculties();
    
}
