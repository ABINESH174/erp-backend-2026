package erp.javaguides.erpbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import erp.javaguides.erpbackend.entity.OfficeBearer;

public interface OfficeBearerRepository extends JpaRepository<OfficeBearer, Long> {
    
}
