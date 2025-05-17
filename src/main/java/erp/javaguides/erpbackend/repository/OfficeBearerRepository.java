package erp.javaguides.erpbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import erp.javaguides.erpbackend.entity.OfficeBearer;

import java.util.Optional;

public interface OfficeBearerRepository extends JpaRepository<OfficeBearer, Long> {

    Optional<OfficeBearer> findByEmail(String email);
}
