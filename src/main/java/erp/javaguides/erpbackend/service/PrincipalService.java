package erp.javaguides.erpbackend.service;

import java.util.List;

import erp.javaguides.erpbackend.dto.responseDto.BonafideResponseDto;
import erp.javaguides.erpbackend.entity.Principal;

public interface PrincipalService {
    Principal getPrincipalById(Long principalId);
    Principal savePrincipal(Principal principal);
    Principal getPrincipalByEmail(String email);


    // Bonafide neccessities
    List<BonafideResponseDto> getOfficeBearersApprovedBonafides();
}
