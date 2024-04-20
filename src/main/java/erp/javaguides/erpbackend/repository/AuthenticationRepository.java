package erp.javaguides.erpbackend.repository;

import erp.javaguides.erpbackend.entity.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationRepository extends JpaRepository<Authentication,Long> {
}