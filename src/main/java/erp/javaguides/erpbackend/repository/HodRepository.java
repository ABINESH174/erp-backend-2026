package erp.javaguides.erpbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import erp.javaguides.erpbackend.entity.Hod;

public interface HodRepository extends JpaRepository<Hod, Long> {

    Optional<Hod> findByEmail(String email);

    List<Hod> findByDiscipline(String discipline);

}
