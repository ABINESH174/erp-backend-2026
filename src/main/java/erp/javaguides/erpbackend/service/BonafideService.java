package erp.javaguides.erpbackend.service;

import java.util.List;

import erp.javaguides.erpbackend.dto.requestDto.CreateBonafideRequestDto;
import erp.javaguides.erpbackend.dto.responseDto.ApplicableBonafideResponseDto;
import erp.javaguides.erpbackend.dto.responseDto.BonafideResponseDto;
import erp.javaguides.erpbackend.entity.Bonafide;

public interface BonafideService {
    BonafideResponseDto saveBonafide(CreateBonafideRequestDto bonafideDto) throws  Exception;
    BonafideResponseDto getBonafideById(Long bonafideId);
    BonafideResponseDto getBonafideByIdAndRegisterNo(Long bonafideId, String registerNo);
    List<BonafideResponseDto> getAllBonafidesByRegisterNo(String registerNo);
    List<BonafideResponseDto> getAllBonafidesByRegisterNoAndBonafideStatus(String registerNo, String status);
    List<BonafideResponseDto> getAllBonafides();
    BonafideResponseDto updateBonafideWithBonafideStatus(Long bonafideId, String registerNo, String status);
    void deleteBonafide(Long bonafideId, String registerNo);
    ApplicableBonafideResponseDto getApplicableBonafied(String registerNo);


    byte[] generateBonafideCertificate(Long bonafideId, String registerNo) throws Exception;
    void notifyFacultyOnSubmission(String registerNo);
    Bonafide notifyNextApprover(Long bonafideId , String status , String registerNo);
    List<BonafideResponseDto> getHodApprovedBonafides();
    List<BonafideResponseDto> getPrincipalApprovedBonafides();

    BonafideResponseDto updateObRejectedBonafide(Long bonafideId, String registerNo, BonafideStatus status, String rejectionMessage);
    // BonafideResponseDto updateBonafideWithFiles(CreateBonafideRequestDto bonafideDto);

//    List<BonafideDto> getAllBonafides();
//    BonafideDto updateBonafide(String registerNo, BonafideDto bonafideDto);
//    void deleteBonafide(String registerNo);
}

