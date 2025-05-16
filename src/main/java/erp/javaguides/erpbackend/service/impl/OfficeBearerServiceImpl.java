package erp.javaguides.erpbackend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import erp.javaguides.erpbackend.dto.responseDto.BonafideResponseDto;
import erp.javaguides.erpbackend.enums.BonafideStatus;
import erp.javaguides.erpbackend.mapper.BonafideMapper;
import erp.javaguides.erpbackend.repository.BonafideRepository;
import erp.javaguides.erpbackend.service.OfficeBearerService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OfficeBearerServiceImpl implements OfficeBearerService{

    private final BonafideRepository bonafideRepository;

    @Override
    public List<BonafideResponseDto> getHodApprovedBonafides() {
        return bonafideRepository.findByBonafideStatus(BonafideStatus.HOD_APPROVED)
                .stream()
                .map(BonafideMapper::mapToBonafideResponseDto)
                .toList();
    }
    
}
