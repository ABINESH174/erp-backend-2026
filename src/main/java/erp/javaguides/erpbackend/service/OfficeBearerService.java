package erp.javaguides.erpbackend.service;

import java.util.List;

import erp.javaguides.erpbackend.dto.responseDto.BonafideResponseDto;

public interface OfficeBearerService {
    List<BonafideResponseDto> getHodApprovedBonafides();
}
