package erp.javaguides.erpbackend.mapper;

import erp.javaguides.erpbackend.dto.requestDto.FacultyDto;
import erp.javaguides.erpbackend.dto.requestDto.FacultyRequestDto;
import erp.javaguides.erpbackend.dto.responseDto.FacultyResponseDto;
import erp.javaguides.erpbackend.dto.responseDto.StudentResponseDto;
import erp.javaguides.erpbackend.entity.Faculty;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FacultyMapper {
    public static FacultyDto mapToFacultyDto(Faculty faculty){
        return new FacultyDto(
                faculty.getFirstName(),
                faculty.getLastName(),
                faculty.getEmail(),
                faculty.getMobileNumber(),
                faculty.getAadharNumber(),
                faculty.getDiscipline(),
                faculty.getHandlingBatch(),
                faculty.getSubjects(),
                faculty.getHandlingSemesters(),
                faculty.getHandlingDepartments(),
                faculty.getBatches(),
                null
        );
    }
    public static Faculty mapToFaculty(FacultyDto facultyDto){
        return new Faculty(
                facultyDto.getFirstName(),
                facultyDto.getLastName(),
                facultyDto.getEmail(),
                facultyDto.getMobileNumber(),
                facultyDto.getAadharNumber(),
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

        // to add the student dto list to the faculty response send
        // in case the students list is null, thats why the optional class came into action...!!!
        List<StudentResponseDto> studentDtos = Optional.ofNullable(faculty.getStudents())
                .orElse(Collections.emptyList())
                .stream()
                .map(StudentMapper::mapToStudentResponseDto)
                .toList();

        return new FacultyResponseDto(
                faculty.getFacultyId(),
                faculty.getFirstName(),
                faculty.getLastName(),
                faculty.getEmail(),
                faculty.getMobileNumber(),
                faculty.getAadharNumber(),
                faculty.getDiscipline(),
                faculty.getDepartment(),
                faculty.getHandlingBatch(),
                studentDtos
        );
    }
    public static Faculty mapToFaculty(FacultyRequestDto facultyRequestDto){
        return new Faculty(
            facultyRequestDto.getFirstName(),
            facultyRequestDto.getLastName(),
            facultyRequestDto.getEmail(),
            facultyRequestDto.getMobileNumber(),
                facultyRequestDto.getAadharNumber(),
            facultyRequestDto.getDiscipline(),
            facultyRequestDto.getDepartment()
        );
    }
}
