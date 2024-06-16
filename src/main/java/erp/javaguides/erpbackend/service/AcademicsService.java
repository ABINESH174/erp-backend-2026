package erp.javaguides.erpbackend.service;

import erp.javaguides.erpbackend.dto.AcademicsDto;

import java.util.List;


public interface AcademicsService {
    AcademicsDto createAcademics(AcademicsDto academicsDto)throws Exception;
    AcademicsDto getAcademicsById(String register_No);
    List<AcademicsDto> getAllAcademics();
    AcademicsDto updateAcademics(String register_No, AcademicsDto updatedAcademics);
    void deleteAcademics(String register_No);
}
