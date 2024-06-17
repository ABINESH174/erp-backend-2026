package erp.javaguides.erpbackend.mapper;

import erp.javaguides.erpbackend.dto.FacultyDto;
import erp.javaguides.erpbackend.entity.Faculty;

public class FacultyMapper {
    public static FacultyDto mapToFacultyDto(Faculty faculty){
        return new FacultyDto(
                faculty.getName(),
                faculty.getEmail(),
                faculty.getDepartment(),
                faculty.getBatch()
        );
    }
    public static Faculty mapToFaculty(FacultyDto facultyDto){
        return new Faculty(
                facultyDto.getName(),
                facultyDto.getEmail(),
                facultyDto.getDepartment(),
                facultyDto.getBatch()
        );
    }
}
