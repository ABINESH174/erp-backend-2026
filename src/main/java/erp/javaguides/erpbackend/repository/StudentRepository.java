package erp.javaguides.erpbackend.repository;

import erp.javaguides.erpbackend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,String>{
    List<Student> findByDisciplineAndAcademicYear(String discipline, String academicYear);
}
