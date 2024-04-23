package erp.javaguides.erpbackend.service;

import erp.javaguides.erpbackend.dto.AcademicsDto;

import java.util.List;


public interface AcademicsService {
    AcademicsDto createAcademics(AcademicsDto academicsDto);
    AcademicsDto getAcademicsById(String Email_Id);
    List<AcademicsDto> getAllAcademics();
    AcademicsDto updateAcademics(String Email_Id, AcademicsDto updatedAcademics);
    void deleteAcademics(String Email_Id);
}
