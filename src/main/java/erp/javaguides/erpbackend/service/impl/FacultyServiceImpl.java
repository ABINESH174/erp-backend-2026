package erp.javaguides.erpbackend.service.impl;

import erp.javaguides.erpbackend.dto.FacultyDto;
import erp.javaguides.erpbackend.dto.StudentDto;
import erp.javaguides.erpbackend.entity.Faculty;
import erp.javaguides.erpbackend.entity.Student;
import erp.javaguides.erpbackend.exception.ResourceNotFoundException;
import erp.javaguides.erpbackend.mapper.FacultyMapper;
import erp.javaguides.erpbackend.mapper.StudentMapper;
import erp.javaguides.erpbackend.repository.FacultyRepository;
import erp.javaguides.erpbackend.repository.StudentRepository;
import erp.javaguides.erpbackend.service.FacultyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    @Override
    public FacultyDto createFaculty(FacultyDto facultyDto) throws Exception {
        Optional<Faculty> optionalFaculty = facultyRepository.findById(facultyDto.getEmail());
        if (optionalFaculty.isPresent()) {
            throw new Exception("Email already exists");
        }
        Faculty faculty = FacultyMapper.mapToFaculty(facultyDto);
        Faculty savedFaculty = facultyRepository.save(faculty);
        return FacultyMapper.mapToFacultyDto(savedFaculty);
    }
    @Override
    public FacultyDto getFacultyByEmail(String email) {
        Faculty faculty = facultyRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with email: " + email));
        return getFacultyWithStudent(faculty, faculty.getDiscipline(), faculty.getHandlingBatch());
    }

    @Override
    public FacultyDto getFacultyByEmail(String email, String className, String batch) {
        Faculty faculty = facultyRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with email: " + email));
        return getFacultyWithStudent(faculty, className, batch);
    }
    @Override
    public FacultyDto getFacultyWithStudent(String email){
        Faculty faculty = facultyRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with email: " + email));
        List<Student> studentsList = studentRepository.findByDiscipline(faculty.getDiscipline());
        List<StudentDto> studentDtos = studentsList.stream()
                .map(StudentMapper::mapToStudentWithFilesDto)
                .collect(Collectors.toList());

        FacultyDto facultyDto = FacultyMapper.mapToFacultyDto(faculty);
        facultyDto.setStudents(studentDtos);
        return facultyDto;
    }
    private FacultyDto getFacultyWithStudent(Faculty faculty, String discipline, String batch) {
        List<Student> studentsList = studentRepository.findByDisciplineAndBatch(discipline, batch);
        List<StudentDto> studentDtos = studentsList.stream()
                .map(StudentMapper::mapToStudentWithFilesDto)
                .collect(Collectors.toList());

        FacultyDto facultyDto = FacultyMapper.mapToFacultyDto(faculty);
        facultyDto.setStudents(studentDtos);
        return facultyDto;
    }
    public FacultyDto getFaculty(String hodEmail){
        Faculty hod = facultyRepository.findById(hodEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with email: " + hodEmail));

        List<Faculty> allFaculties = facultyRepository.findByDiscipline(hod.getDiscipline());

        List<FacultyDto> facultyDtos = allFaculties.stream()
                .filter(faculty -> !faculty.getEmail().equals(hod.getEmail()))
                .map(FacultyMapper::mapToFacultyDto)
                .collect(Collectors.toList());
        FacultyDto facultyDto = FacultyMapper.mapToFacultyDto(hod);
        facultyDto.setFaculties(facultyDtos);
        return facultyDto;
    }
    @Override
    public List<FacultyDto> getAllFaculties() {
        List<Faculty> faculties = facultyRepository.findAll();
        return faculties.stream()
                .map(FacultyMapper::mapToFacultyDto)
                .collect(Collectors.toList());
    }
    @Override
    public FacultyDto addClassFaculty(String email, FacultyDto facultyDto) {
        Faculty existingFaculty = facultyRepository.findById(email)
                .orElseThrow(() -> new RuntimeException("Faculty not found with email: " + email));

        // Update other fields directly if needed

//        existingFaculty.setFirstName(facultyDto.getFirstName());
//        existingFaculty.setLastName(facultyDto.getLastName());
//        existingFaculty.setMobileNumber(facultyDto.getMobileNumber());
//        existingFaculty.setDiscipline(facultyDto.getDiscipline());
//        existingFaculty.setHandlingBatch(facultyDto.getHandlingBatch());


        if (facultyDto.getSubject() != null) {
            if (existingFaculty.getSubject()!= null){
                existingFaculty.setSubject(existingFaculty.getSubject() + "#" + facultyDto.getSubject());
            }
            else {
                existingFaculty.setSubject(facultyDto.getSubject());
            }
        }
        if (facultyDto.getHandlingSemester() != null) {
            if (existingFaculty.getHandlingSemester()!=null){
                existingFaculty.setHandlingSemester(existingFaculty.getHandlingSemester() + "#" + facultyDto.getHandlingSemester());
            }
            else{
                existingFaculty.setHandlingSemester(facultyDto.getHandlingSemester());
            }
        }
        if (facultyDto.getHandlingDept() != null) {
            if (existingFaculty.getHandlingDept()!=null){
                existingFaculty.setHandlingDept(existingFaculty.getHandlingDept() + "#" + facultyDto.getHandlingDept());
            }
            else{
                existingFaculty.setHandlingDept(facultyDto.getHandlingDept());

            }
        }
        if (facultyDto.getBatch() != null) {
            if (existingFaculty.getBatch()!= null){
                existingFaculty.setBatch(existingFaculty.getBatch() + "#" + facultyDto.getBatch());
            }
            else {
                existingFaculty.setBatch(facultyDto.getBatch());
            }
        }

        // Save the updated faculty entity
        Faculty updatedFaculty = facultyRepository.save(existingFaculty);

        // Convert the updated entity to DTO and return
        return FacultyMapper.mapToFacultyDto(updatedFaculty);  // Assuming you have a mapper for entity-DTO conversion
    }
    public FacultyDto removeClassFaculty(String email, String index) {
        Faculty existingFaculty = facultyRepository.findById(email)
                .orElseThrow(() -> new RuntimeException("Faculty not found with email: " + email));

        // Convert index to integer
        int idx;
        try {
            idx = Integer.parseInt(index);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid index format: " + index);
        }

        // Check if batchYear is present in the batch string
        if (existingFaculty.getBatch() != null) {
            String[] batches = existingFaculty.getBatch().split("#");
            List<String> batchList = new ArrayList<>(Arrays.asList(batches));

            // Remove the batchYear at the specified index if it exists
            if (idx >= 0 && idx < batchList.size()) {
                batchList.remove(idx);
                existingFaculty.setBatch(batchList.isEmpty() ? null : String.join("#", batchList));
            }
        }

        // Now remove the corresponding subject if it exists
        if (existingFaculty.getSubject() != null) {
            String[] subjects = existingFaculty.getSubject().split("#");
            List<String> subjectList = new ArrayList<>(Arrays.asList(subjects));

            // Remove the subject at the specified index if it exists
            if (idx >= 0 && idx < subjectList.size()) {
                subjectList.remove(idx);
                existingFaculty.setSubject(subjectList.isEmpty() ? null : String.join("#", subjectList));
            }
        }

        // Similarly handle handlingDept
        if (existingFaculty.getHandlingDept() != null) {
            String[] departments = existingFaculty.getHandlingDept().split("#");
            List<String> departmentList = new ArrayList<>(Arrays.asList(departments));

            // Remove the department at the specified index if it exists
            if (idx >= 0 && idx < departmentList.size()) {
                departmentList.remove(idx);
                existingFaculty.setHandlingDept(departmentList.isEmpty() ? null : String.join("#", departmentList));
            }
        }

        // Similarly handle handlingSemester
        if (existingFaculty.getHandlingSemester() != null) {
            String[] semesters = existingFaculty.getHandlingSemester().split("#");
            List<String> semesterList = new ArrayList<>(Arrays.asList(semesters));

            // Remove the semester at the specified index if it exists
            if (idx >= 0 && idx < semesterList.size()) {
                semesterList.remove(idx);
                existingFaculty.setHandlingSemester(semesterList.isEmpty() ? null : String.join("#", semesterList));
            }
        }

        // Save the updated faculty entity
        facultyRepository.save(existingFaculty);

        // Convert to DTO and return
        return FacultyMapper.mapToFacultyDto(existingFaculty);  // Assuming you have a mapper for entity-DTO conversion
    }
}
