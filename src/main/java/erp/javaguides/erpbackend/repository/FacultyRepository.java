package erp.javaguides.erpbackend.repository;

import erp.javaguides.erpbackend.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty,String> {
    List<Faculty> findByDiscipline(String discipline);
}
