package erp.javaguides.erpbackend.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import erp.javaguides.erpbackend.utility.UtilityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import erp.javaguides.erpbackend.dto.requestDto.HodRequestDto;
import erp.javaguides.erpbackend.dto.responseDto.BonafideResponseDto;
import erp.javaguides.erpbackend.dto.responseDto.HodResponseDto;
import erp.javaguides.erpbackend.entity.Hod;
import erp.javaguides.erpbackend.entity.Principal;
import erp.javaguides.erpbackend.enums.BonafideStatus;
import erp.javaguides.erpbackend.exception.ResourceNotFoundException;
import erp.javaguides.erpbackend.mapper.BonafideMapper;
import erp.javaguides.erpbackend.mapper.HodMapper;
import erp.javaguides.erpbackend.repository.BonafideRepository;
import erp.javaguides.erpbackend.repository.HodRepository;
import erp.javaguides.erpbackend.repository.OfficeBearerRepository;
import erp.javaguides.erpbackend.service.HodService;
import erp.javaguides.erpbackend.service.PrincipalService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HodServiceImpl implements HodService {
    
    private final HodRepository hodRepository;

    private final BonafideRepository bonafideRepository;

    private final PrincipalService principalService;

    private final OfficeBearerRepository officeBearerRepository;

    private final UtilityService utilityService;
    

    @Transactional
    @Override
    public HodResponseDto createHod(HodRequestDto hodRequestDto) {
        Optional<Hod> existingHod = hodRepository.findByEmail(hodRequestDto.getEmail());
        if (existingHod.isPresent()) {
            throw new IllegalArgumentException("HOD with this email already exists.");
        }

        Hod hod = HodMapper.toHod(hodRequestDto);

        Principal principal = principalService.getPrincipalByEmail(hodRequestDto.getPrincipalEmail());
        principal.addHod(hod);

        hod.addAllOfficeBearers(officeBearerRepository.findAll());

        utilityService.addEmailToAuthentication(hod.getEmail(),hod.getEmail());
        
        Hod savedHod = hodRepository.save(hod);
        return HodMapper.toHodResponseDto(savedHod);
    }

    @Override
    public HodResponseDto getHodById(Long id) {
        Hod hod = hodRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("HOD not found with id: " + id));
        return HodMapper.toHodResponseDto(hod);
    }

    @Override
    public void deleteHod(Long hodId) {
        Hod hod = hodRepository.findById(hodId)
                            .orElseThrow(() -> new ResourceNotFoundException("HOD not found with id: " + hodId));
        hodRepository.delete(hod);
    }

    @Override
    public List<HodResponseDto> getAllHods() {
        List<Hod> hods = hodRepository.findAll();
        return hods.stream()
                .map(HodMapper::toHodResponseDto)
                .toList();
    }

    @Override
    public HodResponseDto getHodByEmail(String email) {
        Hod hod = hodRepository.findByEmail(email)
                            .orElseThrow(() -> new ResourceNotFoundException("HOD not found with email: " + email));
        return HodMapper.toHodResponseDto(hod);
    }

    @Override
    public HodResponseDto getHodByDiscipline(String discipline) {
        Hod hod = hodRepository.findByDiscipline(discipline)
                .orElseThrow(()->new ResourceNotFoundException("Hod not found with discipline:"+discipline));
        return HodMapper.toHodResponseDto(hod);
    }
    @Override
    public List<BonafideResponseDto> getFacultyApprovedBonafidesByHodId(Long hodId) {

        List<BonafideResponseDto> bonafideResponseDtos = new ArrayList<>();

        Hod hod = hodRepository.findById(hodId)
                .orElseThrow(() -> new ResourceNotFoundException("HOD not found with id: " + hodId));

        if( hod.getDiscipline().equalsIgnoreCase("Science and Humanities")){
            bonafideResponseDtos = bonafideRepository.findByBonafideStatusAndStudentDepartment(BonafideStatus.FACULTY_APPROVED, "Science and Humanities")
                                        .stream()
                                        .map(BonafideMapper::mapToBonafideResponseDto)
                                        .collect((Collectors.toList()));
        } 
        else {      
            bonafideResponseDtos = bonafideRepository.findByBonafideStatusAndStudentDiscipline(
                BonafideStatus.FACULTY_APPROVED, hod.getDiscipline())
                .stream()
                .map(BonafideMapper::mapToBonafideResponseDto)
                .collect((Collectors.toList()));
        }
        return bonafideResponseDtos;
    }
        
    
}
