package erp.javaguides.erpbackend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import erp.javaguides.erpbackend.dto.requestDto.HodRequestDto;
import erp.javaguides.erpbackend.dto.responseDto.HodResponseDto;
import erp.javaguides.erpbackend.entity.Hod;
import erp.javaguides.erpbackend.exception.ResourceNotFoundException;
import erp.javaguides.erpbackend.mapper.HodMapper;
import erp.javaguides.erpbackend.repository.HodRepository;
import erp.javaguides.erpbackend.service.HodService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HodServiceImpl implements HodService {
    
    private final HodRepository hodRepository;
    

    @Override
    public HodResponseDto createHod(HodRequestDto hodRequestDto) {
        Optional<Hod> existingHod = hodRepository.findByEmail(hodRequestDto.getEmail());
        if (existingHod.isPresent()) {
            throw new IllegalArgumentException("HOD with this email already exists.");
        }
        Hod hod = HodMapper.toHod(hodRequestDto);
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
    public void deleteHod(Long id) {
        Hod hod = hodRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("HOD not found with id: " + id));
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
    public List<HodResponseDto> getHodByDiscipline(String discipline) {
        List<Hod> hods = hodRepository.findByDiscipline(discipline);
        if (hods.isEmpty()) {
            throw new ResourceNotFoundException("No HOD found with discipline: " + discipline);
        }
        return hods.stream()
                .map(HodMapper::toHodResponseDto)
                .toList();
    }
    
    
    
}
