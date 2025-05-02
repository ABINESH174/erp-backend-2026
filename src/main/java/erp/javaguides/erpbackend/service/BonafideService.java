package erp.javaguides.erpbackend.service;

import java.util.List;

import erp.javaguides.erpbackend.dto.requestDto.CreateBonafideRequestDto;
import erp.javaguides.erpbackend.dto.responseDto.BonafideResponseDto;

public interface BonafideService {
    BonafideResponseDto saveBonafide(CreateBonafideRequestDto bonafideDto) throws  Exception;
    BonafideResponseDto getBonafideById(Long bonafideId);
    BonafideResponseDto getBonafideByIdAndRegisterNo(Long bonafideId, String registerNo);
    List<BonafideResponseDto> getAllBonafidesByRegisterNo(String registerNo);
    List<BonafideResponseDto> getAllBonafidesByRegisterNoAndBonafideStatus(String registerNo, String status);
    List<BonafideResponseDto> getAllBonafides();
    BonafideResponseDto updateBonafideWithBonafideStatus(Long bonafideId, String registerNo, String status);
    void deleteBonafide(Long bonafideId, String registerNo);


//    List<BonafideDto> getAllBonafides();
//    BonafideDto updateBonafide(String registerNo, BonafideDto bonafideDto);
//    void deleteBonafide(String registerNo);
}

