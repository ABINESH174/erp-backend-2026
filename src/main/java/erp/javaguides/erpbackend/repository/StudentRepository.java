package erp.javaguides.erpbackend.repository;

import erp.javaguides.erpbackend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StudentRepository extends JpaRepository<Student,String>{
    List<Student> findByDisciplineAndBatch(String discipline, String batch);
    List<Student> findByDiscipline(String discipline);
}
