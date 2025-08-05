package erp.javaguides.erpbackend.repository;

import erp.javaguides.erpbackend.entity.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthenticationRepository extends JpaRepository<Authentication,Long> {

    Authentication findByUserId(String userId);
    Optional<Authentication> findByEmail(String email);

    boolean existsByUserId(String email);
}