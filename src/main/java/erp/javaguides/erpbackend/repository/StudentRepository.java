package erp.javaguides.erpbackend.repository;

import erp.javaguides.erpbackend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,String>{
    List<Student> findByDisciplineAndBatch(String discipline, String batch);
    List<Student> findByDiscipline(String discipline);

}
