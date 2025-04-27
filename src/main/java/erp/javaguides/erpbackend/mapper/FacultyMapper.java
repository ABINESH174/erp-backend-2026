package erp.javaguides.erpbackend.mapper;

import erp.javaguides.erpbackend.dto.requestDto.FacultyDto;
import erp.javaguides.erpbackend.dto.responseDto.FacultyResponseDto;
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
                faculty.getSubjects(),
                faculty.getHandlingSemesters(),
                faculty.getHandlingDepartments(),
                faculty.getBatches(),
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
                facultyDto.getSubjects(),
                facultyDto.getHandlingSemesters(),
                facultyDto.getHandlingDepartments(),
                facultyDto.getBatches()
        );
    }

    // faculty to faculty response dto
    public static FacultyResponseDto mapToFacultyResponseDto(Faculty faculty){
        return new FacultyResponseDto(
                faculty.getFacultyId(),
                faculty.getFirstName(),
                faculty.getLastName(),
                faculty.getEmail(),
                faculty.getMobileNumber(),
                faculty.getDiscipline(),
                faculty.getHandlingBatch(),
                faculty.getSubjects(),
                faculty.getHandlingSemesters(),
                faculty.getHandlingDepartments(),
                faculty.getBatches(),
                null,
                null
        );
    }
}
