package erp.javaguides.erpbackend.repository;

import erp.javaguides.erpbackend.entity.Bonafide;
import erp.javaguides.erpbackend.enums.BonafideStatus;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;

public interface BonafideRepository extends JpaRepository<Bonafide, Long> {
    Optional<Bonafide> findByBonafideIdAndStudentRegisterNo(Long bonafideId, String registerNo);
    List<Bonafide> findAllByStudentRegisterNo(String registerNo);


    // custom query for retrieving bonafides by bonafide status and faculty id from student in bonafide
    // this will be used in the ui of the entities to view specific bonafides depending on the status
    // @Query("SELECT b FROM Bonafide b WHERE b.bonafideStatus = :bonafideStatus AND b.student.facultyId = :facultyId")
    // List<BonafideResponseDto> findBonafidesByStatusAndFacultyId(@Param("bonafideStatus") BonafideStatus bonafideStatus, @Param("facultyId") Long facultyId);

    // This is an auto generated query by spring data jpa for the above query
    List<Bonafide> findByBonafideStatusAndStudentFacultyFacultyId(BonafideStatus bonafideStatus, Long facultyId);
    List<Bonafide> findByBonafideStatusAndStudentDiscipline(BonafideStatus bonafideStatus, String discipline);

    List<Bonafide> findByBonafideStatus(BonafideStatus bonafideStatus);
}

