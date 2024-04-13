package erp.javaguides.erpbackend.service;

import erp.javaguides.erpbackend.dto.AcademicsDto;

import java.util.List;


public interface AcademicsService {
    AcademicsDto createAcademics(AcademicsDto academicsDto);
    AcademicsDto getAcademicsById(String Register_No);
    List<AcademicsDto> getAllAcademics();
    AcademicsDto updateAcademics(String Register_No, AcademicsDto updatedAcademics);
    void deleteAcademics(String Register_No);
}
