package erp.javaguides.erpbackend.repository;

import erp.javaguides.erpbackend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,String>{
    List<Student> findByDisciplineAndBatch(String discipline, String batch);
    List<Student> findByDiscipline(String discipline);

    Optional<Student> findByRegisterNo(String registerNo);

    List<Student> findBySemesterAndDiscipline(String semester, String discipline);
    List<Student> findByFacultyFacultyId(Long facultyId);
    List<Student> findByDisciplineAndClassSection(String discipline, String classSection);
    List<Student> findByDisciplineAndClassSectionAndBatch(String discipline, String classSection, String batch);

    List<Student> findByDisciplineAndSemesterIn(String discipline, List<String> semesters);
    List<Student> findByDisciplineAndSemesterInAndClassSection(String discipline, List<String> semesters, String classSection);
    

}
