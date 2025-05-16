package erp.javaguides.erpbackend.service;

import java.util.List;

import erp.javaguides.erpbackend.dto.requestDto.HodRequestDto;
import erp.javaguides.erpbackend.dto.responseDto.BonafideResponseDto;
import erp.javaguides.erpbackend.dto.responseDto.HodResponseDto;

public interface HodService {
    HodResponseDto createHod(HodRequestDto hodRequestDto);
    HodResponseDto getHodById(Long id);
    void deleteHod(Long hodId);
    List<HodResponseDto> getAllHods();
    HodResponseDto getHodByEmail(String email);
    HodResponseDto getHodByDiscipline(String discipline);

    List<BonafideResponseDto> getFacultyApprovedBonafidesByHodId(Long hodId);
}
