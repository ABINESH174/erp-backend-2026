package erp.javaguides.erpbackend.mapper;

import erp.javaguides.erpbackend.dto.FacultyDto;
import erp.javaguides.erpbackend.entity.Faculty;

public class FacultyMapper {
    public static FacultyDto mapToFacultyDto(Faculty faculty){
        return new FacultyDto(
                faculty.getFirstName(),
                faculty.getLastName(),
                faculty.getEmail(),
                faculty.getMobileNumber(),
                faculty.getDiscipline(),
                faculty.getHandlingBatch(),
                faculty.getHandlingClass(),
                null,
                null
        );
    }
    public static Faculty mapToFaculty(FacultyDto facultyDto){
        return new Faculty(
                facultyDto.getFirstName(),
                facultyDto.getLastName(),
                facultyDto.getEmail(),
                facultyDto.getMobileNumber(),
                facultyDto.getDiscipline(),
                facultyDto.getHandlingBatch(),
                facultyDto.getHandlingClass()
        );
    }
}
