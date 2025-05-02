package erp.javaguides.erpbackend.repository;

import erp.javaguides.erpbackend.entity.Bonafide;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BonafideRepository extends JpaRepository<Bonafide, Long> {
    Optional<Bonafide> findByBonafideIdAndStudentRegisterNo(Long bonafideId, String registerNo);
    List<Bonafide> findAllByStudentRegisterNo(String registerNo);
}

