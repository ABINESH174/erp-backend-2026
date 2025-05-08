package erp.javaguides.erpbackend.service;

import java.util.List;

import erp.javaguides.erpbackend.dto.requestDto.HodRequestDto;
import erp.javaguides.erpbackend.dto.responseDto.HodResponseDto;

public interface HodService {
    HodResponseDto createHod(HodRequestDto hodRequestDto);
    HodResponseDto getHodById(Long id);
    void deleteHod(Long id);
    List<HodResponseDto> getAllHods();
    HodResponseDto getHodByEmail(String email);
    List<HodResponseDto> getHodByDiscipline(String discipline);
}
