package erp.javaguides.erpbackend.service.impl;

import erp.javaguides.erpbackend.dto.AcademicsDto;

import erp.javaguides.erpbackend.entity.Academics;

import erp.javaguides.erpbackend.exception.ResourceNotFoundException;
import erp.javaguides.erpbackend.mapper.AcademicsMapper;
import erp.javaguides.erpbackend.repository.AcademicsRepository;
import erp.javaguides.erpbackend.service.AcademicsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AcademicsServiceImpl implements AcademicsService {
    private AcademicsRepository academicsRepository;
    @Override
    public AcademicsDto createAcademics(AcademicsDto academicsDto) throws Exception{
        Optional<Academics> optionalAcademics = academicsRepository.findById(academicsDto.getRegister_No());
        if(optionalAcademics.isPresent()){
            throw new Exception("Register no already exists");
        }
        Academics academics = AcademicsMapper.mapToAcademics(academicsDto);
        Academics savedAcademics=academicsRepository.save(academics);
        return AcademicsMapper.mapToAcademicsDto(savedAcademics);
    }

    @Override
    public AcademicsDto getAcademicsById(String register_No) {
        Academics academics = academicsRepository.findById(register_No)
                .orElseThrow(() ->new ResourceNotFoundException("Student is not exist with the given id:" + register_No));
        return AcademicsMapper.mapToAcademicsDto(academics);
    }

    @Override
    public List<AcademicsDto> getAllAcademics() {
        List<Academics> academic=academicsRepository.findAll();
        return  academic.stream().map((academics) -> AcademicsMapper.mapToAcademicsDto(academics))
                .collect(Collectors.toList());
    }

    @Override
    public AcademicsDto updateAcademics(String register_No, AcademicsDto updatedAcademics) {
        Academics academics= academicsRepository.findById(register_No)
                .orElseThrow(() ->new ResourceNotFoundException("Student is not exist with the given id:" + register_No));

        Academics updatedAcademicsObj=academicsRepository.save(academics);
        return AcademicsMapper.mapToAcademicsDto(updatedAcademicsObj);
    }

    @Override
    public void deleteAcademics(String register_No) {
        Academics academics = academicsRepository.findById(register_No)
                .orElseThrow(() ->new ResourceNotFoundException("Student is not exist with the given id:" + register_No));
        academicsRepository.deleteById(register_No);
    }
}
