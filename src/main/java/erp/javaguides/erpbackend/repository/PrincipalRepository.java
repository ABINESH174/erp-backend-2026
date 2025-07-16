package erp.javaguides.erpbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import erp.javaguides.erpbackend.entity.Principal;


public interface PrincipalRepository extends JpaRepository<Principal, Long> {
    Optional<Principal> findByEmail(String email);

    boolean existsByEmail(String principalEmail);
}
