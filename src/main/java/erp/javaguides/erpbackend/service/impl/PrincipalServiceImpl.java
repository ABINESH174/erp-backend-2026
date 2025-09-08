package erp.javaguides.erpbackend.service.impl;

import java.util.List;

import erp.javaguides.erpbackend.utility.UtilityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import erp.javaguides.erpbackend.dto.responseDto.BonafideResponseDto;
import erp.javaguides.erpbackend.entity.Principal;
import erp.javaguides.erpbackend.enums.BonafideStatus;
import erp.javaguides.erpbackend.exception.ResourceNotFoundException;
import erp.javaguides.erpbackend.mapper.BonafideMapper;
import erp.javaguides.erpbackend.repository.BonafideRepository;
import erp.javaguides.erpbackend.repository.PrincipalRepository;
import erp.javaguides.erpbackend.service.PrincipalService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalServiceImpl implements PrincipalService {

    private final PrincipalRepository principalRepository;
    private final BonafideRepository bonafideRepository;
    private final UtilityService utilityService;

    @Override
    public Principal getPrincipalById(Long principalId) {
        return principalRepository.findById(principalId)
                    .orElseThrow(()-> new ResourceNotFoundException("Principal not found with id: " + principalId));
    }

    @Transactional
    @Override
    public Principal savePrincipal(Principal principal) {
        utilityService.addEmailToAuthentication(principal.getEmail(),principal.getEmail());
        // Must check dynamically if another principal in db is active... before saving (future feature)
        return principalRepository.save(principal);
    }

    @Transactional(readOnly = true)
    @Override
    public Principal getPrincipalByEmail(String email) {
        return principalRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Principal not found with email: " + email));
    }

    @Override
    public List<BonafideResponseDto> getOfficeBearersApprovedBonafides(){
        return bonafideRepository.findByBonafideStatus(BonafideStatus.OB_APPROVED)
                .stream()
                .map(BonafideMapper::mapToBonafideResponseDto)
                .toList();
    }

    @Override
    public List<Principal> getAllPrincipals() {
        return principalRepository.findAll();
    }

}
