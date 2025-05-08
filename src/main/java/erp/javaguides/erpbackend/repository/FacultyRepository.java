package erp.javaguides.erpbackend.repository;

import erp.javaguides.erpbackend.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FacultyRepository extends JpaRepository<Faculty,Long> {
    List<Faculty> findByDiscipline(String discipline);

    Optional<Faculty> findByEmail(String email);

    Optional<Faculty> findByDisciplineAndHandlingBatch(String discipline, String handlingBatch);
}
