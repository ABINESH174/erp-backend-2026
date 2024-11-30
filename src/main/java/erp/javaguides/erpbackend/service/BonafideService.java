package erp.javaguides.erpbackend.service;

import erp.javaguides.erpbackend.dto.BonafideDto;

import java.util.List;

public interface BonafideService {
    BonafideDto createBonafide(BonafideDto bonafideDto);
    BonafideDto getBonafideByRegisterNo(String registerNo);
    List<BonafideDto> getAllBonafides();
    BonafideDto updateBonafide(String registerNo, BonafideDto bonafideDto);
    void deleteBonafide(String registerNo);
}

