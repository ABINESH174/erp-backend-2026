package erp.javaguides.erpbackend.service.impl;

import erp.javaguides.erpbackend.dto.BonafideDto;
import erp.javaguides.erpbackend.entity.Bonafide;
import erp.javaguides.erpbackend.exception.ResourceNotFoundException;
import erp.javaguides.erpbackend.mapper.BonafideMapper;
import erp.javaguides.erpbackend.repository.BonafideRepository;
import erp.javaguides.erpbackend.service.BonafideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BonafideServiceImpl implements BonafideService {

    @Autowired
    private BonafideRepository bonafideRepository;

    @Override
    public BonafideDto createBonafide(BonafideDto bonafideDto) {
        Bonafide bonafide = BonafideMapper.mapToBonafide(bonafideDto);
        Bonafide savedBonafide = bonafideRepository.save(bonafide);
        return BonafideMapper.mapToBonafideDto(savedBonafide);
    }

    @Override
    public BonafideDto getBonafideByRegisterNo(String registerNo) {
        Bonafide bonafide = bonafideRepository.findById(registerNo)
                .orElseThrow(() -> new ResourceNotFoundException("Bonafide not found with Register No: " + registerNo));
        return BonafideMapper. mapToBonafideDto(bonafide);
    }

    @Override
    public List<BonafideDto> getAllBonafides() {
        List<Bonafide> bonafides = bonafideRepository.findAll();
        return bonafides.stream()
                .map(BonafideMapper::mapToBonafideDto)
                .collect(Collectors.toList());
    }

    @Override
    public BonafideDto updateBonafide(String registerNo, BonafideDto bonafideDto) {
        Bonafide existingBonafide = bonafideRepository.findById(registerNo)
                .orElseThrow(() -> new ResourceNotFoundException("Bonafide not found with Register No: " + registerNo));

        existingBonafide.setPurpose(bonafideDto.getPurpose());
        existingBonafide.setStatus(bonafideDto.getStatus());

        Bonafide updatedBonafide = bonafideRepository.save(existingBonafide);
        return BonafideMapper.mapToBonafideDto(updatedBonafide);
    }

    @Override
    public void deleteBonafide(String registerNo) {
        Bonafide bonafide = bonafideRepository.findById(registerNo)
                .orElseThrow(() -> new ResourceNotFoundException("Bonafide not found with Register No: " + registerNo));
        bonafideRepository.delete(bonafide);
    }
}

